package org.example;

public class MergeSort<E> implements CompareSorter<E> {
    private final Comparator<? super E> comparator;

    public MergeSort(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    @Override
    public Comparator<? super E> comparator() {
        return comparator;
    }

    @Override
    public void sort(E[] array, int from, int to) {
        merge(array, from, to, (E[]) new Object[to - from]);
    }

    private void merge(E[] array, int from, int to, E[] temp) {
        if (to - from <= 1) {
            return;
        }
        int mid = (from + to) >>> 1;
        merge(array, from, mid, temp);
        merge(array, mid, to, temp);
        int i = from;
        int j = mid;
        int k = 0;
        while (i < mid && j < to) {
            if (comparator.compare(array[i], array[j]) < 0) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }
        while (i < mid) {
            temp[k++] = array[i++];
        }
        while (j < to) {
            temp[k++] = array[j++];
        }
        for (k = from; k < to; k++) {
            array[k] = temp[k - from];
        }
    }
}
