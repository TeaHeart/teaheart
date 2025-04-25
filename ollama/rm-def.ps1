# 删除自定义模型
ollama list | % {
    $name = ($_ -split "\s+", 2)[0]
    if ($name -like "teaheart*") {
        ollama rm $name
    }
}