package org.example;

public class InsertSort<E> implements CompareSorter<E> {
    private final Comparator<? super E> comparator;

    public InsertSort(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    @Override
    public Comparator<? super E> comparator() {
        return comparator;
    }

    @Override
    public void sort(E[] array, int from, int to) {
        for (int i = from + 1; i < to; i++) {
            E x = array[i];
            int j;
            for (j = i; j - 1 >= from; j--) {
                if (comparator.compare(array[j - 1], x) <= 0) {
                    break;
                }
                array[j] = array[j - 1];
            }
            array[j] = x;
        }
    }
}
