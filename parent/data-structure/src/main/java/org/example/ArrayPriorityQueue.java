package org.example;

public class ArrayPriorityQueue<E> implements PriorityQueue<E> {
    private final Comparator<? super E> comparator;
    private Object[] data = new Object[1];
    private int size;

    public ArrayPriorityQueue(Comparator<? super E> comparator) {
        this.comparator = comparator;
    }

    private void grow() {
        if (size == data.length) {
            Object[] newData = new Object[data.length << 1];
            for (int i = 0; i < size; i++) {
                newData[i] = data[i];
            }
            data = newData;
        }
    }

    private void siftUp(int k) {
        E x = (E) data[k];
        while (k > 0) {
            int parent = (k - 1) >>> 1;
            if (comparator.compare(x, (E) data[parent]) >= 0) {
                break;
            }
            data[k] = data[parent];
            k = parent;
        }
        data[k] = x;
    }

    private void siftDown(int k, int n) {
        E x = (E) data[k];
        int half = n >>> 1;
        while (k < half) {
            int child = (k << 1) | 1;
            int right = child + 1;
            if (right < n && comparator.compare((E) data[child], (E) data[right]) > 0) {
                child = right;
            }
            if (comparator.compare(x, (E) data[child]) <= 0) {
                break;
            }
            data[k] = data[child];
            k = child;
        }
        data[k] = x;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        size = 0;
    }

    @Override
    public boolean add(E e) {
        return offer(e);
    }

    @Override
    public boolean contains(E e) {
        for (int i = 0; i < size; i++) {
            if (e == data[i] || e != null && e.equals(data[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remove(E e) {
        while (size != 0) {
            E x = poll();
            if (e == x || e != null && e.equals(x)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int index;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public E next() {
                return (E) data[index++];
            }
        };
    }

    @Override
    public Comparator<? super E> comparator() {
        return comparator;
    }

    @Override
    public boolean offer(E e) {
        grow();
        data[size] = e;
        siftUp(size++);
        return false;
    }

    @Override
    public E peek() {
        return (E) data[0];
    }

    @Override
    public E poll() {
        E x = (E) data[0];
        data[0] = data[--size];
        data[size] = null;
        siftDown(0, size);
        return x;
    }
}
