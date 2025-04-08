package org.example.collection;

public interface TupleX {
    static Tuple0 of() {
        return new Tuple0();
    }

    static <T1> Tuple1<T1> of(T1 arg1) {
        return new Tuple1<>(arg1);
    }

    static <T1, T2> Tuple2<T1, T2> of(T1 arg1, T2 arg2) {
        return new Tuple2<>(arg1, arg2);
    }

    static <T1, T2, T3> Tuple3<T1, T2, T3> of(T1 arg1, T2 arg2, T3 arg3) {
        return new Tuple3<>(arg1, arg2, arg3);
    }

    static <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> of(T1 arg1, T2 arg2, T3 arg3, T4 arg4) {
        return new Tuple4<>(arg1, arg2, arg3, arg4);
    }
}