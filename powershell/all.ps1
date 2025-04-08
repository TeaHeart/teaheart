function Base {
    set-executionpolicy remotesigned # 允许脚本
    powercfg -duplicatescheme e9a42b02-d5df-448d-aa00-03f14749eb61 # 电源计划 卓越性能
}

function Fast-Copy {
    param (
        [Parameter(Mandatory)] [ValidateNotNullOrEmpty()] [string] $src,
        [Parameter(Mandatory)] [ValidateNotNullOrEmpty()] [string] $dest,
        [ValidateNotNullOrEmpty()] [int] $cpus = (Get-WmiObject win32_processor).NumberOfLogicalProcessors / 2,
        [ValidateNotNullOrEmpty()] [switch] $quiet = $false
    )
    $src = Join-Path (Resolve-Path $src) "\"
    $dest = Join-Path (Resolve-Path $dest) "\"
    $parent = ([System.IO.FileInfo]$src).Directory.Parent.FullName
    if (Test-Path -PathType Leaf $src) {
        throw "请输入文件夹"
    }
    Copy-Item $src $dest
    Get-ChildItem -Recurse $src | ForEach-Object -Parallel {
        $destPath = Join-Path $using:dest $_.FullName.Substring($using:parent.Length)
        Copy-Item $_ $destPath
        if (!$using:quiet) {
            Write-Host "$_ => $destPath"
        }
    } -ThrottleLimit $cpus
}

function Hash-Helper {
    param (
        [Parameter(Mandatory)] [ValidateNotNullOrEmpty()] [string] $src,
        [ValidateSet('SHA1', 'SHA256', 'SHA384', 'SHA512', 'MD5', 'LENGTH')] [string] $algorithm = "LENGTH",
        [string] $hashFile = "",
        [ValidateNotNullOrEmpty()] [int] $cpus = (Get-WmiObject win32_processor).NumberOfLogicalProcessors / 2,
        [ValidateNotNullOrEmpty()] [switch] $quiet = $false
    )
    $src = Join-Path (Resolve-Path $src) "\"
    if (Test-Path -PathType Leaf $src) {
        throw "请输入文件夹"
    }
    if ([string]::IsNullOrEmpty($hashFile)) {
        $hashFile = "$(([System.IO.FileInfo]$src).Directory.Name).$algorithm"
    }
    if (Test-Path -PathType Leaf $hashFile) {
        Get-Content $hashFile | ForEach-Object -Parallel {
            # 格式: `HASH *FILENAME`
            $info = $_.Split(" *")
            $srcPath = Join-Path $using:src $info[1]
            $hash = "LENGTH" -ieq $using:algorithm ? (Get-ChildItem $srcPath).Length : (Get-FileHash -Algorithm $using:algorithm $srcPath).Hash
            if ($hash -ieq $info[0]) {
                if (!$using:quiet) {
                    Write-Host "$hash == $($info[0]) $($info[1])"
                }
            } else {
                throw "$hash != $($info[0]) $($info[1])"
            }
        } -ThrottleLimit $cpus
    } else {
        Get-ChildItem -File -Recurse $src | ForEach-Object -Parallel {
            $destPath = $_.FullName.Substring($using:src.Length)
            $hash = "LENGTH" -ieq $using:algorithm ? $_.Length : (Get-FileHash -Algorithm $using:algorithm $_).Hash
            $info = "$hash *$destPath"
            if (!$using:quiet) {
                Write-Host $info
            }
            return $info
        } -ThrottleLimit $cpus | Out-File $hashFile
    }
}

function Tree-Compare {
    param (
        [Parameter(Mandatory)] [ValidateNotNullOrEmpty()] [string] $src,
        [Parameter(Mandatory)] [ValidateNotNullOrEmpty()] [string] $dest
    )
    $srcTmp = "src.tmp"
    $destTmp = "dest.tmp"
    tree /F $src | Select-Object -Skip 2 | Out-File $srcTmp
    tree /F $dest | Select-Object -Skip 2 | Out-File $destTmp
    git diff $srcTmp $destTmp
    Remove-Item $srcTmp
    Remove-Item $destTmp
}

function Hash-Compare {
    param (
        [Parameter(Mandatory)] [ValidateNotNullOrEmpty()] [string] $src,
        [Parameter(Mandatory)] [ValidateNotNullOrEmpty()] [string] $dest,
        [ValidateSet('SHA1', 'SHA256', 'SHA384', 'SHA512', 'MD5', 'LENGTH')] [string] $algorithm = "LENGTH",
        [string] $hashFile = "",
        [ValidateNotNullOrEmpty()] [int] $cpus = (Get-WmiObject win32_processor).NumberOfLogicalProcessors / 2,
        [ValidateNotNullOrEmpty()] [switch] $quiet = $false
    )
    $src = Join-Path (Resolve-Path $src) "\"
    $dest = Join-Path (Resolve-Path $dest) "\"
    if (Test-Path -PathType Leaf $src) {
        throw "请输入文件夹"
    }
    if (Test-Path -PathType Leaf $dest) {
        throw "请输入文件夹"
    }
    if ([string]::IsNullOrEmpty($hashFile)) {
        $hashFile = "$(([System.IO.FileInfo]$src).Directory.Name).$algorithm"
    }
    if ($quiet) {
        Hash-Helper $src -algorithm $algorithm -hashFile $hashFile -cpus $cpus -quiet
        Hash-Helper $dest -algorithm $algorithm -hashFile $hashFile -cpus $cpus -quiet
    } else {
        Hash-Helper $src -algorithm $algorithm -hashFile $hashFile -cpus $cpus
        Hash-Helper $dest -algorithm $algorithm -hashFile $hashFile -cpus $cpus
    }
    Remove-Item $hashFile
}

# TODO 重构
function eBookConvert {
    param (
        [Parameter(Mandatory)] [ValidateNotNullOrEmpty()] [string] $base,
        [ValidateNotNullOrEmpty()] [string] $dest = ".\output\",
        [Parameter(ValueFromPipeline)] [ValidateNotNullOrEmpty()] [System.IO.FileSystemInfo[]] $files = (Get-ChildItem -Recurse $base),
        [ValidateNotNullOrEmpty()] [int]$cpus = ((Get-WmiObject win32_processor).NumberOfLogicalProcessors),
        [ValidateNotNullOrEmpty()] [string] $srcExt = ".epub",
        [ValidateNotNullOrEmpty()] [string] $destExt = ".pdf",
        [ValidateNotNullOrEmpty()] [string] $exec = 'ebook-convert.exe "{0}" "{1}"',
        [ValidateNotNullOrEmpty()] [switch] $quiet = $false
    )
    # 官网 https://www.calibre-ebook.com/zh_CN
    function WaitJob {
        param (
            [int]$limit = 1,
            [int]$sleep = 3
        )
        while ((Get-Job -State Running).Length -ge $limit) {
            Get-Job -State Running | Format-Table
            Start-Sleep $sleep
        }
    }
    mkdir -ErrorAction SilentlyContinue $dest | Out-Null
    $base = Join-Path $base "\" | Resolve-Path
    $dest = Join-Path $dest "\" | Resolve-Path
    for ($i = 0; $i -lt $files.Length; $i++) {
        $file = $files[$i]
        $sf = $file.FullName
        $df = Join-Path $dest $sf.Substring($base.Length)
        $type = "~"
        if ($file -is [System.IO.DirectoryInfo]) {
            mkdir -ErrorAction SilentlyContinue $df | Out-Null
            $type = "+"
        }
        elseif ($file.Extension -eq $srcExt) {
            WaitJob $cpus
            $df = $df.Replace($srcExt, $destExt)
            Start-Job -Name $file.BaseName -ArgumentList $exec, $sf, $df {
                cmd /c ([string]::Format($args[0], $args[1], $args[2]))
            } | Out-Null
            $type = "&"
        }
        if (!$quiet) {
            Write-Host "$i/$($files.Length) $type $df"
        }
    }
    WaitJob
    Receive-Job * | Out-Null
    Remove-Job -State Completed
}

function VisualStudio-Download-All {
    .\vs_Enterprise.exe `
        --layout .\vs_Enterprise\ <# 路径 #> `
        --all <# 全部负载 #>`
        --lang en-US zh-CN <# 语言包 #>
}

function OptionView {
    param (
        [Parameter(Mandatory)] [ValidateNotNullOrEmpty()] [string[]] $list,
        [ValidateNotNullOrEmpty()] [int] $min = 0,
        [ValidateNotNullOrEmpty()] [int] $index = $min,
        [ValidateNotNullOrEmpty()] [int] $max = $list.Length - 1
    )
    Clear-Host
    [System.Nullable[System.ConsoleKeyInfo]] $key = $null
    for ($i = 0; $i -lt $list.Length; $i++) {
        [System.Console]::SetCursorPosition(0, $i)
        [System.Console]::Write($i -eq $index ? "> " : "  ")
        [System.Console]::Write($list[$i])
    }
    while (($key = [System.Console]::ReadKey($true)).Key -ne [System.ConsoleKey]::Enter) {
        if ($key.Key -eq [System.ConsoleKey]::UpArrow) {
            $index = $index -le $min ? $max : $index - 1
        } elseif ($key.Key -eq [System.ConsoleKey]::DownArrow) {
            $index = $index -ge $max ? $min : $index + 1
        }
        for ($i = 0; $i -lt $list.Length; $i++) {
            [System.Console]::SetCursorPosition(0, $i)
            [System.Console]::Write($i -eq $index ? "> " : "  ")
        }
    } 
    Clear-Host
    return $list[$index]
}

function Ollama-CLI {
    param (
        [ValidateSet('show', 'run', 'stop', 'rm', 'list', 'ps')] [string] $op = "run"
    )
    if ($op -ieq 'ps') {
        while($true) { 
            Clear-Host
            ollama ps
            sleep 1
        }
        return
    }
    if ($op -ieq 'list') {
        ollama list
        return  
    }
    $list = $op -ieq 'stop' ? (ollama ps) : (ollama list)
    $opt = OptionView $list -min 1
    $split = $opt -split "\s+", 2
    ollama $op $split[0]
}
