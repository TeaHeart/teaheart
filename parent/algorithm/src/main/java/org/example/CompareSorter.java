package org.example;

public interface CompareSorter<E> extends Sorter<E> {
    Comparator<? super E> comparator();
}
