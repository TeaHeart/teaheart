package org.example.config;

import cn.hutool.core.util.StrUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class ZCloudDataConverter implements Converter<String, Map<String, String>> {
    @NonNull
    @Override
    public Map<String, String> convert(String source) {
        if (StrUtil.isEmpty(source) || !source.startsWith("{") || !source.endsWith("}")) {
            return Collections.emptyMap();
        }
        Map<String, String> result = new HashMap<>();
        for (String kv : source.substring(1, source.length() - 1).split(",")) {
            String[] kvp = kv.split("=");
            result.put(kvp[0], kvp[1]);
        }
        return result;
    }
}
