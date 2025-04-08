package org.example;

public class SelectSort<E> implements CompareSorter<E> {
    private final Comparator<? super E> comparator;

    public SelectSort(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    @Override
    public Comparator<? super E> comparator() {
        return comparator;
    }

    @Override
    public void sort(E[] array, int from, int to) {
        for (int i = from; i < to; i++) {
            int minIndex = i;
            for (int j = i; j < to; j++) {
                if (comparator.compare(array[j], array[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            swap(array, i, minIndex);
        }
    }
}
