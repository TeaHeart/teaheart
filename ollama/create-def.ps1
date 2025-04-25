# 根据文件创建模型
ls -File def | % {
    $name = "teaheart/" + $_.Name.Replace("@", ":")
    ollama create $name -f $_.FullName
}


