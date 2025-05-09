# 拉取模型
(
    'qwen2.5-coder:1.5b',
    'qwen3:1.7b',
    'qwen3:4b',
    'qwen3:8b',
    'qwen3:30b',
    'mistral:7b',
    'huihui_ai/qwen2.5-coder-abliterate:1.5b',
    'huihui_ai/qwen3-abliterated:1.7b',
    'huihui_ai/qwen3-abliterated:4b',
    'qllama/bce-embedding-base_v1:q4_k_m',
    'qllama/bce-reranker-base_v1:q4_k_m'
) | % {
    $_
    ollama pull $_
}
