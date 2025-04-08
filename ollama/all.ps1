(
    'qwen2.5:1.5b',
    'qwen2.5-coder:1.5b',
    'deepseek-r1:1.5b',
    'deepseek-coder-v2:16b',
    'gemma3:1b',
    'gemma3:4b',
    'llama3.2:1b',
    'nomic-embed-text'
) | % {
    ollama pull $_
}

# ls -File def | % {
#     ollama create "teaheart/${$_.Name.Replace("@", ":")}" -f $_.FullName
# }

ollama list
