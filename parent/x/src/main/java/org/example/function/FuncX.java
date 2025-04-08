package org.example.function;

import org.example.X;

public interface FuncX {
    static <R> Func0<R> of(Func0<R> func) {
        return func;
    }

    static <T1, R> Func1<T1, R> of(Func1<T1, R> func) {
        return func;
    }

    static <T1, T2, R> Func2<T1, T2, R> of(Func2<T1, T2, R> func) {
        return func;
    }

    static <T1, T2, T3, R> Func3<T1, T2, T3, R> of(Func3<T1, T2, T3, R> func) {
        return func;
    }

    static <T1, T2, T3, T4, R> Func4<T1, T2, T3, T4, R> of(Func4<T1, T2, T3, T4, R> func) {
        return func;
    }

    static <T1, R> Func0<R> of(Func1<T1, R> func, T1 arg1) {
        return () -> func.call(arg1);
    }

    static <T1, T2, R> Func0<R> of(Func2<T1, T2, R> func, T1 arg1, T2 arg2) {
        return () -> func.call(arg1, arg2);
    }

    static <T1, T2, T3, R> Func0<R> of(Func3<T1, T2, T3, R> func, T1 arg1, T2 arg2, T3 arg3) {
        return () -> func.call(arg1, arg2, arg3);
    }

    static <T1, T2, T3, T4, R> Func0<R> of(Func4<T1, T2, T3, T4, R> func, T1 arg1, T2 arg2, T3 arg3, T4 arg4) {
        return () -> func.call(arg1, arg2, arg3, arg4);
    }

    static <T1, T2, R> Func1<T1, R> of(Func2<T1, T2, R> func, T2 arg2) {
        return (arg1) -> func.call(arg1, arg2);
    }

    static <T1, T2, T3, R> Func1<T1, R> of(Func3<T1, T2, T3, R> func, T2 arg2, T3 arg3) {
        return (arg1) -> func.call(arg1, arg2, arg3);
    }

    static <T1, T2, T3, T4, R> Func1<T1, R> of(Func4<T1, T2, T3, T4, R> func, T2 arg2, T3 arg3, T4 arg4) {
        return (arg1) -> func.call(arg1, arg2, arg3, arg4);
    }

    static <T1, T2, T3, R> Func2<T1, T2, R> of(Func3<T1, T2, T3, R> func, T3 arg3) {
        return (arg1, arg2) -> func.call(arg1, arg2, arg3);
    }

    static <T1, T2, T3, T4, R> Func2<T1, T2, R> of(Func4<T1, T2, T3, T4, R> func, T3 arg3, T4 arg4) {
        return (arg1, arg2) -> func.call(arg1, arg2, arg3, arg4);
    }

    static <T1, T2, T3, T4, R> Func3<T1, T2, T3, R> of(Func4<T1, T2, T3, T4, R> func, T4 arg4) {
        return (arg1, arg2, arg3) -> func.call(arg1, arg2, arg3, arg4);
    }

    static <R> R sneaky(Func0<R> func) {
        try {
            return func.call();
        } catch (Throwable e) {
            throw X.throwrt(e);
        }
    }

    static <T1, R> R sneaky(Func1<T1, R> func, T1 arg1) {
        return sneaky(of(func, arg1));
    }

    static <T1, T2, R> R sneaky(Func2<T1, T2, R> func, T1 arg1, T2 arg2) {
        return sneaky(of(func, arg1, arg2));
    }

    static <T1, T2, T3, R> R sneaky(Func3<T1, T2, T3, R> func, T1 arg1, T2 arg2, T3 arg3) {
        return sneaky(of(func, arg1, arg2, arg3));
    }

    static <T1, T2, T3, T4, R> R sneaky(Func4<T1, T2, T3, T4, R> func, T1 arg1, T2 arg2, T3 arg3, T4 arg4) {
        return sneaky(of(func, arg1, arg2, arg3, arg4));
    }

    static <R> R ignore(Func0<R> func) {
        try {
            return func.call();
        } catch (Throwable e) {
            return null;
        }
    }

    static <T1, R> R ignore(Func1<T1, R> func, T1 arg1) {
        return ignore(of(func, arg1));
    }

    static <T1, T2, R> R ignore(Func2<T1, T2, R> func, T1 arg1, T2 arg2) {
        return ignore(of(func, arg1, arg2));
    }

    static <T1, T2, T3, R> R ignore(Func3<T1, T2, T3, R> func, T1 arg1, T2 arg2, T3 arg3) {
        return ignore(of(func, arg1, arg2, arg3));
    }

    static <T1, T2, T3, T4, R> R ignore(Func4<T1, T2, T3, T4, R> func, T1 arg1, T2 arg2, T3 arg3, T4 arg4) {
        return ignore(of(func, arg1, arg2, arg3, arg4));
    }
}