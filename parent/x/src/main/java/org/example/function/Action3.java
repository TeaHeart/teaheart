package org.example.function;

public interface Action3<T1, T2, T3> {
    void call(T1 arg1, T2 arg2, T3 arg3) throws Throwable;
}