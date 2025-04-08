package org.example.reflect;

import org.example.X;

public interface TypeHolderX {
    static <T> TypeHolder<T> of(T obj) {
        return new TypeHolder<>(obj, X.cast(obj.getClass()));
    }

    static <T> TypeHolder<T> of(Class<T> type) {
        return new TypeHolder<>(null, type);
    }

    static <T> TypeHolder<T> of(T obj, Class<T> type) {
        return new TypeHolder<>(obj, type);
    }
}