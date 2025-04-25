# 拉取模型
(
    'qwen2.5:7b',
    'qwen2.5-coder:1.5b',
    'qwen2.5-coder:7b',
    'deepseek-r1:7b',
    'deepseek-coder-v2:16b',
    'huihui_ai/qwen2.5-1m-abliterated:7b',
    'huihui_ai/qwen2.5-coder-abliterate:1.5b',
    'huihui_ai/qwen2.5-coder-abliterate:7b',
    'huihui_ai/deepseek-r1-abliterated:7b',
    'qllama/bce-embedding-base_v1:q4_k_m',
    'qllama/bce-reranker-base_v1:q4_k_m'
) | % {
    $_
    ollama pull $_
}

# 删除自定义模型
ollama list | % {
    $name = ($_ -split "\s+", 2)[0]
    if ($name -like "teaheart*") {
        ollama rm $name
    }
}

# 创建modelfile文件并写入一些参数
ollama list | % {
    $name = ($_ -split "\s+",2)[0]
    
    if ($name -like "NAME" -or $name -like "teaheart*") {
        return
    }

    $sb = [System.Text.StringBuilder]::new()
    $sb.AppendLine("FROM {0}") > $null
    $sb.AppendLine() > $null
    $sb.AppendLine("PARAMETER num_gpu 32") > $null
    $sb.AppendLine("PARAMETER num_batch 32") > $null
    $sb.Append("{1}") > $null
    $sb = $sb.ToString()

    $pureName = ($name -split "/",2)[-1]
    $temp = $name -like "*coder*" ? "PARAMETER temperature 0" : ""
    $filename = $pureName.Replace(":", "@")
    $modelfile = $sb -f $name, $temp
    $modelfile > "./def/$filename"
}

# 根据文件创建模型
ls -File def | % {
    $name = "teaheart/" + $_.Name.Replace("@", ":")
    ollama create $name -f $_.FullName
}
