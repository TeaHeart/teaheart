package org.example.reflect;

import org.example.X;
import org.example.core.Equatable;

import java.util.Objects;

public class TypeHolder<T> implements Equatable<TypeHolder<T>> {
    private final Class<T> type;
    private final T value;

    public TypeHolder(T value, Class<T> type) {
        if (type.isPrimitive()) {
            X.assertTrue(X.getWrapperClass(type.getSimpleName()).isInstance(value));
        } else {
            X.assertTrue(value == null || type.isInstance(value));
        }
        this.value = value;
        this.type = type;
    }

    public Class<T> getType() {
        return type;
    }

    public T getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof TypeHolder<?> && equals(X.cast(obj));
    }

    @Override
    public boolean equals(TypeHolder<T> obj) {
        return obj != null && Objects.equals(value, obj.value);
    }

    @Override
    public String toString() {
        return Objects.toString(value);
    }
}