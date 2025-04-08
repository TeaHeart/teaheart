package org.example;

public class ShellSort<E> implements CompareSorter<E> {
    private final Comparator<? super E> comparator;

    public ShellSort(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    @Override
    public Comparator<? super E> comparator() {
        return comparator;
    }

    @Override
    public void sort(E[] array, int from, int to) {
        for (int gap = (to - from) >>> 1; gap > 0; gap >>= 1) {
            for (int i = gap; i < to; i++) {
                E x = array[i];
                int j;
                for (j = i; j - gap >= from; j -= gap) {
                    if (comparator.compare(array[j - gap], x) <= 0) {
                        break;
                    }
                    array[j] = array[j - gap];
                }
                array[j] = x;
            }
        }
    }
}
