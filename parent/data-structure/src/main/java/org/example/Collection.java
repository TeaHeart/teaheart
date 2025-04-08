package org.example;

public interface Collection<E> extends Iterable<E> {
    int size();

    boolean isEmpty();

    void clear();

    boolean add(E e);

    boolean contains(E e);

    boolean remove(E e);
}
