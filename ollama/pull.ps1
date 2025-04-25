# 拉取模型
(
    'qwen2.5:7b',
    'qwen2.5-coder:1.5b',
    'qwen2.5-coder:7b',
    'deepseek-r1:7b',
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
