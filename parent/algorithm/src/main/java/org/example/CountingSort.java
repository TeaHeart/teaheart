package org.example;

public class CountingSort<E> implements GroupingSorter<E> {
    private final Grouper<? super E> grouper;

    public CountingSort(Grouper<? super E> grouper) {
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
        E[] data = (E[]) new Object[max - min + 1];
        int[] count = new int[data.length];
        for (int i = from; i < to; i++) {
            E x = array[i];
            int slot = grouper.grouping(x) - min;
            data[slot] = x;
            count[slot]++;
        }
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < count[i]; j++) {
                array[from++] = data[i];
            }
        }
    }
}
