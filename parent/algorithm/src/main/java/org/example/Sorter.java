package org.example;

public interface Sorter<E> {
    void sort(E[] array, int from, int to);

    default void sort(E[] array) {
        sort(array, 0, array.length);
    }

    default void swap(E[] array, int i, int j) {
        E temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
