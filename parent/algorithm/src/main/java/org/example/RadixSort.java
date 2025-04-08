package org.example;

public class RadixSort<E> implements GroupingSorter<E> {
    private final Grouper<? super E> grouper;

    public RadixSort(Grouper<? super E> grouper) {
        this.grouper = grouper;
    }

    @Override
    public Grouper<? super E> grouping() {
        return grouper;
    }

    @Override
    public void sort(E[] array, int from, int to) {
        int[] minAndMax = findMinAndMax(array, from, to);
        int min = minAndMax[0];
        int max = minAndMax[1];
        int step = 0;
        for (int i = Math.max(max, -min); i != 0; i /= 10) {
            step++;
        }
        E[][] buckets = (E[][]) new Object[20][to - from];
        int[] lengths = new int[buckets.length];
        for (int radix = 1; step-- != 0; radix *= 10) {
            for (int i = from; i < to; i++) {
                E x = array[i];
                int slot = grouper.grouping(x) / radix % 10 + 10;
                buckets[slot][lengths[slot]++] = x;
            }
            for (int i = 0, k = from; i < buckets.length; i++) {
                for (int j = 0; j < lengths[i]; j++) {
                    array[k++] = buckets[i][j];
                }
                lengths[i] = 0;
            }
        }
    }
}
