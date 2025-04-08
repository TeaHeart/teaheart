package org.example;

public interface SortedSet<E> extends Set<E> {
    Comparator<? super E> comparator();

    E first();

    E last();
}
