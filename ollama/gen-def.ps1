# 创建modelfile文件并写入一些参数
$exclude = @("NAME", "teaheart*", "*embedding*", "*reranker*")

ollama list | % {
    $name = ($_ -split "\s+",2)[0]

    foreach ($item in $exclude) {
        if ($name -like $item) { 
            return
        }
    }

    $sb = [System.Text.StringBuilder]::new()
    $sb.AppendLine("FROM {0}") > $null
    $sb.AppendLine() > $null
    $sb.AppendLine("PARAMETER num_gpu 64") > $null
    $sb.AppendLine("PARAMETER num_batch 32") > $null
    $sb.Append("{1}") > $null
    $sb = $sb.ToString()

    $pureName = ($name -split "/",2)[-1]
    $temp = $name -like "*coder*" ? "PARAMETER temperature 0" : ""
    $filename = $pureName.Replace(":", "@")
    $modelfile = $sb -f $name, $temp
    $dest = "./def/$filename"
    if (!(Test-Path $dest)) {
        $modelfile > $dest
    }
}
