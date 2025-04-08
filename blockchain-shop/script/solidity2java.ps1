$rootPath = $pwd

$contractPath = "$rootPath\contract"

$scriptPath = "$rootPath\script"

$javaSrcPath = "$rootPath\shop\src"

$old = "$contractPath\build", "$javaPath\main\java\org\example\contract"

foreach ($item in $old) {
    if (Test-path($item)) {
        Remove-Item $item -Force -Recurse
    }
}

cd $contractPath
npm run build

cd "$contractPath\build"
$arr = Get-ChildItem -File | Where-Object {$_.Name -like "src*"} | %{$_.Name}
$set = New-Object System.Collections.Generic.HashSet"[string]"

foreach($oldName in $arr) {
    $newName = $oldName.Substring($oldName.LastIndexOf("_") + 1);
    if ($newName.EndsWith(".bin")) { 
        ("0x" + (Get-Content $oldName)) | Set-Content -NoNewline $newName
        Remove-Item $oldName
    } else {
        Rename-Item $oldName -NewName $newName
    }
    $set.Add($newName.Substring(0, $newName.LastIndexOf("."))) | Out-Null
}

foreach($item in $set) {
    &"$scriptPath\web3j-4.5.5\bin\web3j" solidity generate -b ".\$item.bin" -a ".\$item.abi" -c "$item" -o "$javaSrcPath\main\java" -p org.example.contract
}
