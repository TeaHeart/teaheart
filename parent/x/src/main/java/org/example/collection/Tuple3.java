package org.example.collection;

public class Tuple3<T1, T2, T3> extends Tuple {
    public Tuple3(T1 arg1, T2 arg2, T3 arg3) {
        super(arg1, arg2, arg3);
    }

    public T1 item1() {
        return get(0);
    }

    public T2 item2() {
        return get(1);
    }

    public T3 item3() {
        return get(2);
    }
}
