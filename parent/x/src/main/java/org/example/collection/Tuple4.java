package org.example.collection;

public class Tuple4<T1, T2, T3, T4> extends Tuple {
    public Tuple4(T1 arg1, T2 arg2, T3 arg3, T4 arg4) {
        super(arg1, arg2, arg3, arg4);
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

    public T4 item4() {
        return get(3);
    }
}
