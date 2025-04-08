package org.example;

public class BubbleSort<E> implements CompareSorter<E> {
    private final Comparator<? super E> comparator;

    public BubbleSort(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    @Override
    public Comparator<? super E> comparator() {
        return comparator;
    }

    @Override
    public void sort(E[] array, int from, int to) {
        for (int i = from; i < to; i++) {
            for (int j = from; j < to - i + from - 1; j++) {
                if (comparator.compare(array[j + 1], array[j]) < 0) {
                    swap(array, j + 1, j);
                }
            }
        }
    }
}
