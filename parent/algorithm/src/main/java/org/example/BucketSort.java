package org.example;

public class BucketSort<E> implements GroupingSorter<E> {
    private final Grouper<? super E> grouper;
    private final Sorter<E> sorter;

    public BucketSort(Grouper<? super E> grouper, Sorter<E> sorter) {
        this.grouper = grouper;
        this.sorter = sorter;
    }

    @Override
    public Grouper<? super E> grouping() {
        return grouper;
    }

    @Override
    public void sort(E[] array, int from, int to) {
        int bucketSize = Math.max(array.length >> 8, 100);
        int[] minAndMax = findMinAndMax(array, from, to);
        int min = minAndMax[0];
        int max = minAndMax[1];
        E[][] buckets = (E[][]) new Object[(max - min + 1) / bucketSize + 1][to - from];
        int[] lengths = new int[buckets.length];
        for (int i = from; i < to; i++) {
            E x = array[i];
            int slot = (grouper.grouping(x) - min) / bucketSize;
            buckets[slot][lengths[slot]++] = x;
        }
        for (int i = 0; i < buckets.length; i++) {
            E[] bucket = buckets[i];
            sorter.sort(bucket, 0, lengths[i]);
            for (int j = 0; j < lengths[i]; j++) {
                array[from++] = bucket[j];
            }
        }
    }
}
