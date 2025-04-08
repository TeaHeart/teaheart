package org.example.function;

public interface Func2<T1, T2, R> {
    R call(T1 arg1, T2 arg2) throws Throwable;
}