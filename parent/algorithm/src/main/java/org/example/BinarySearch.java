package org.example;

import java.util.Comparator;

public class BinarySearch<E> {

    private final Comparator<? super E> comparator;

    public BinarySearch(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    public int binarySearch(E[] array, int left, int right, E target) {
        while (left < right) {
            int mid = (left + right) >>> 1;
            int cmp = comparator.compare(array[mid], target);
            if (cmp < 0) {
                left = mid + 1;
            } else if (cmp > 0) {
                right = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    public int binarySearchLeft(E[] array, int left, int right, E target) {
        while (left < right) {
            int mid = (left + right) >>> 1;
            if (comparator.compare(array[mid], target) < 0) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    public int binarySearchRight(E[] array, int left, int right, E target) {
        while (left < right) {
            int mid = (left + right) >>> 1;
            if (comparator.compare(array[mid], target) <= 0) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
}
