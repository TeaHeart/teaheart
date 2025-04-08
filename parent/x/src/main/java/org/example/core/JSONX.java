package org.example.core;

import java.util.*;

public interface JSONX {
    static String stringify(Object obj) {
        if (obj == null) {
            return "null";
        } else if (obj instanceof List<?>) {
            return stringifyArray((List<?>) obj);
        } else if (obj instanceof Map<?, ?>) {
            return stringifyObject((Map<?, ?>) obj);
        } else if (obj instanceof String) {
            return String.format("\"%s\"", obj);
        } else {
            return obj.toString();
        }
    }

    static String stringifyObject(Map<?, ?> map) {
        StringJoiner sj = new StringJoiner(",", "{", "}");
        map.forEach((s, o) -> sj.add(String.format("\"%s\":%s", s, stringify(o))));
        return sj.toString();
    }

    static String stringifyArray(List<?> list) {
        StringJoiner sj = new StringJoiner(",", "[", "]");
        list.forEach(o -> sj.add(stringify(o)));
        return sj.toString();
    }

    static Object parse(String json) {
        if (json == null) {
            return null;
        } else if (json.startsWith("[") && json.endsWith("]")) {
            return parseArray(json);
        } else if (json.startsWith("{") && json.endsWith("}")) {
            return parseObject(json);
        } else if (json.startsWith("\"") && json.endsWith("\"")) {
            return json.substring(1, json.length() - 1);
        } else {
            return json;
        }
    }

    static List<Object> parseArray(String json) {
        List<Object> list = new ArrayList<>();
        for (String item : json.substring(1, json.length() - 1).split(",")) {
            list.add(parse(item));
        }
        return list;
    }

    static Map<String, Object> parseObject(String json) {
        Map<String, Object> map = new HashMap<>();
        for (String item : json.substring(1, json.length() - 1).split(",")) {
            String[] pair = item.split(":");
            map.put(pair[0].substring(1, pair[0].length() - 1), parse(pair[1]));
        }
        return map;
    }
}