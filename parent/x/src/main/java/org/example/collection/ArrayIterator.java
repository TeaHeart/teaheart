package org.example.collection;

import org.example.X;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIterator<T> implements Iterator<T> {
    private final Object array;
    private final int length;
    private int index;

    public ArrayIterator(Object array) {
        length = Array.getLength(this.array = array);
    }

    @Override
    public boolean hasNext() {
        return index < length;
    }

    @Override
    public T next() {
        X.assertTrue(hasNext(), NoSuchElementException::new);
        return X.cast(Array.get(array, index++));
    }
}