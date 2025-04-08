package org.example.core;

public interface Equatable<T extends Equatable<T>> {
    boolean equals(T obj);
}