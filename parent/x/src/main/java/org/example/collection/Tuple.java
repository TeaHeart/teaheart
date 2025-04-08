package org.example.collection;

import org.example.X;
import org.example.core.Cloneable;
import org.example.core.Equatable;
import org.example.function.FuncX;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;

public class Tuple implements Iterable<Object>, Equatable<Tuple>, Cloneable<Tuple> {
    private final Object[] data;

    public Tuple(Object... data) {
        this.data = data;
    }

    public int size() {
        return data.length;
    }

    public boolean contains(Object o) {
        return ArrayX.contains(data, o);
    }

    public <T> T get(int index) {
        return X.cast(data[index]);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(data);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Tuple && equals(X.cast(obj));
    }

    @Override
    public boolean equals(Tuple obj) {
        return obj != null && Arrays.deepEquals(data, obj.data);
    }

    @Override
    public String toString() {
        return Arrays.deepToString(data);
    }

    @SuppressWarnings("Convert2MethodRef")
    @Override
    public Tuple clone() {
        return X.cast(FuncX.sneaky(() -> super.clone()));
    }

    @Override
    public Iterator<Object> iterator() {
        return new ArrayIterator<>(data);
    }

    @Override
    public Spliterator<Object> spliterator() {
        return Spliterators.spliterator(data, Spliterator.ORDERED | Spliterator.IMMUTABLE);
    }
}