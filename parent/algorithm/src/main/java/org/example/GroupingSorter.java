package org.example;

public interface GroupingSorter<E> extends Sorter<E> {
    Grouper<? super E> grouping();

    default int[] findMinAndMax(E[] array, int from, int to) {
        Grouper<? super E> grouping = grouping();
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = from; i < to; i++) {
            int slot = grouping.grouping(array[i]);
            if (slot < min) {
                min = slot;
            }
            if (slot > max) {
                max = slot;
            }
        }
        return new int[]{min, max};
    }
}
