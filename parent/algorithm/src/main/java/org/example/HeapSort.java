package org.example;

public class HeapSort<E> implements CompareSorter<E> {
    private final Comparator<? super E> comparator;

    public HeapSort(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    @Override
    public Comparator<? super E> comparator() {
        return comparator;
    }

    @Override
    public void sort(E[] array, int from, int to) {
        int n = to - from;
        for (int i = n >>> 1; i >= 0; i--) {
            siftDown(array, i, n, from);
        }
        for (int i = n - 1; i > 0; i--) {
            swap(array, from, i + from);
            siftDown(array, 0, i, from);
        }
    }

    private void siftDown(E[] array, int k, int n, int offset) {
        E x = array[k + offset];
        int half = n >>> 1;
        while (k < half) {
            int child = (k << 1) | 1;
            int right = child + 1;
            if (right < n && comparator.compare(array[child + offset], array[right + offset]) < 0) {
                child = right;
            }
            if (comparator.compare(array[child + offset], x) <= 0) {
                break;
            }
            array[k + offset] = array[child + offset];
            k = child;
        }
        array[k + offset] = x;
    }
}
