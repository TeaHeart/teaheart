(
    'qwen2.5:1.5b-instruct-q4_K_M',
    'qwen2.5:1.5b-instruct-q8_0',
    'qwen2.5:1.5b-instruct-fp16',
    'qwen2.5:7b-instruct-q4_K_M',
    'qwen2.5-coder:1.5b-instruct-q4_K_M',
    'qwen2.5-coder:7b-instruct-q4_K_M',
    'deepseek-r1:1.5b-qwen-distill-q4_K_M',
    'deepseek-r1:7b-qwen-distill-q4_K_M',
    'deepseek-coder-v2:16b-lite-instruct-q4_0',
    'gemma3:4b-it-q4_K_M',
    'nomic-embed-text:137m-v1.5-fp16',
    'bge-m3:567m-fp16',
    'qllama/bge-reranker-v2-m3:f16'
) | % {
    ollama pull $_
}

# ls -File def | % {
#     ollama create "teaheart/${$_.Name.Replace("@", ":")}" -f $_.FullName
# }

ollama list
