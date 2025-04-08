package org.example.collection;

public class Tuple2<T1, T2> extends Tuple {
    public Tuple2(T1 arg1, T2 arg2) {
        super(arg1, arg2);
    }

    public T1 item1() {
        return get(0);
    }

    public T2 item2() {
        return get(1);
    }
}
