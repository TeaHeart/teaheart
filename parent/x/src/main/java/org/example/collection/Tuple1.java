package org.example.collection;

public class Tuple1<T1> extends Tuple {
    public Tuple1(T1 arg1) {
        super(arg1);
    }

    public T1 item1() {
        return get(0);
    }
}
