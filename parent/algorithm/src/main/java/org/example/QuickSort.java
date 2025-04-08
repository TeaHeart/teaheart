package org.example;

public class QuickSort<E> implements CompareSorter<E> {
    private final Comparator<? super E> comparator;

    public QuickSort(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    @Override
    public Comparator<? super E> comparator() {
        return comparator;
    }

    @Override
    public void sort(E[] array, int from, int to) {
        if (to - from <= 1) {
            return;
        }
        int i = from;
        int lt = from;
        int gt = to;
        E x = array[(from + to) >>> 1];
        while (i < gt) {
            int cmp = comparator.compare(array[i], x);
            if (cmp < 0) {
                swap(array, lt++, i++);
            } else if (cmp > 0) {
                swap(array, i, --gt);
            } else {
                i++;
            }
        }
        sort(array, from, lt);
        sort(array, gt, to);
    }
}
