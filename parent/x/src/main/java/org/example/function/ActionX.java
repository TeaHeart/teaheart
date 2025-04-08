package org.example.function;

import org.example.X;

public interface ActionX {
    static Action0 of(Action0 action) {
        return action;
    }

    static <T1> Action1<T1> of(Action1<T1> action) {
        return action;
    }

    static <T1, T2> Action2<T1, T2> of(Action2<T1, T2> action) {
        return action;
    }

    static <T1, T2, T3> Action3<T1, T2, T3> of(Action3<T1, T2, T3> action) {
        return action;
    }

    static <T1, T2, T3, T4> Action4<T1, T2, T3, T4> of(Action4<T1, T2, T3, T4> action) {
        return action;
    }

    static <T1> Action0 of(Action1<T1> action, T1 arg1) {
        return () -> action.call(arg1);
    }

    static <T1, T2> Action0 of(Action2<T1, T2> action, T1 arg1, T2 arg2) {
        return () -> action.call(arg1, arg2);
    }

    static <T1, T2, T3> Action0 of(Action3<T1, T2, T3> action, T1 arg1, T2 arg2, T3 arg3) {
        return () -> action.call(arg1, arg2, arg3);
    }

    static <T1, T2, T3, T4> Action0 of(Action4<T1, T2, T3, T4> action, T1 arg1, T2 arg2, T3 arg3, T4 arg4) {
        return () -> action.call(arg1, arg2, arg3, arg4);
    }

    static <T1, T2> Action1<T1> of(Action2<T1, T2> action, T2 arg2) {
        return (arg1) -> action.call(arg1, arg2);
    }

    static <T1, T2, T3> Action1<T1> of(Action3<T1, T2, T3> action, T2 arg2, T3 arg3) {
        return (arg1) -> action.call(arg1, arg2, arg3);
    }

    static <T1, T2, T3, T4> Action1<T1> of(Action4<T1, T2, T3, T4> action, T2 arg2, T3 arg3, T4 arg4) {
        return (arg1) -> action.call(arg1, arg2, arg3, arg4);
    }

    static <T1, T2, T3> Action2<T1, T2> of(Action3<T1, T2, T3> action, T3 arg3) {
        return (arg1, arg2) -> action.call(arg1, arg2, arg3);
    }

    static <T1, T2, T3, T4> Action2<T1, T2> of(Action4<T1, T2, T3, T4> action, T3 arg3, T4 arg4) {
        return (arg1, arg2) -> action.call(arg1, arg2, arg3, arg4);
    }

    static <T1, T2, T3, T4> Action3<T1, T2, T3> of(Action4<T1, T2, T3, T4> action, T4 arg4) {
        return (arg1, arg2, arg3) -> action.call(arg1, arg2, arg3, arg4);
    }

    static void sneaky(Action0 action) {
        try {
            action.call();
        } catch (Throwable e) {
            throw X.throwrt(e);
        }
    }

    static <T1> void sneaky(Action1<T1> action, T1 arg1) {
        sneaky(of(action, arg1));
    }

    static <T1, T2> void sneaky(Action2<T1, T2> action, T1 arg1, T2 arg2) {
        sneaky(of(action, arg1, arg2));
    }

    static <T1, T2, T3> void sneaky(Action3<T1, T2, T3> action, T1 arg1, T2 arg2, T3 arg3) {
        sneaky(of(action, arg1, arg2, arg3));
    }

    static <T1, T2, T3, T4> void sneaky(Action4<T1, T2, T3, T4> action, T1 arg1, T2 arg2, T3 arg3, T4 arg4) {
        sneaky(of(action, arg1, arg2, arg3, arg4));
    }

    static void ignore(Action0 action) {
        try {
            action.call();
        } catch (Throwable e) {
        }
    }

    static <T1> void ignore(Action1<T1> action, T1 arg1) {
        ignore(of(action, arg1));
    }

    static <T1, T2> void ignore(Action2<T1, T2> action, T1 arg1, T2 arg2) {
        ignore(of(action, arg1, arg2));
    }

    static <T1, T2, T3> void ignore(Action3<T1, T2, T3> action, T1 arg1, T2 arg2, T3 arg3) {
        ignore(of(action, arg1, arg2, arg3));
    }

    static <T1, T2, T3, T4> void ignore(Action4<T1, T2, T3, T4> action, T1 arg1, T2 arg2, T3 arg3, T4 arg4) {
        ignore(of(action, arg1, arg2, arg3, arg4));
    }
}