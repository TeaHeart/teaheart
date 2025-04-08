package org.example;

public class ArrayDeque<E> implements Deque<E>, Stack<E>, List<E> {
    private Object[] data = new Object[1];
    private int mask;
    private int head;
    private int size;

    private void grow() {
        if (size == data.length) {
            Object[] newData = new Object[data.length << 1];
            for (int i = 0; i < size; i++) {
                newData[i] = get(i);
            }
            data = newData;
            mask = data.length - 1;
            head = 0;
        }
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
            set(i, null);
        }
        head = size = 0;
    }

    @Override
    public boolean add(E e) {
        return offerLast(e);
    }

    @Override
    public boolean contains(E e) {
        return indexOf(e) >= 0;
    }

    @Override
    public boolean remove(E e) {
        int index = indexOf(e);
        if (index < 0) {
            return false;
        }
        removeAt(index);
        return true;
    }

    @Override
    public boolean offerFirst(E e) {
        return insert(0, e);
    }

    @Override
    public boolean offerLast(E e) {
        return insert(size, e);
    }

    @Override
    public E peekFirst() {
        return get(0);
    }

    @Override
    public E peekLast() {
        return get(size - 1);
    }

    @Override
    public E pollFirst() {
        return removeAt(0);
    }

    @Override
    public E pollLast() {
        return removeAt(size - 1);
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
                return get(index++);
            }
        };
    }

    @Override
    public boolean insert(int index, E e) {
        grow();
        if (index != 0) {
            for (int i = size; i > index; i--) {
                set(i, get(i - 1));
            }
        } else {
            head = (head - 1) & mask;
        }
        set(index, e);
        size++;
        return true;
    }

    @Override
    public E get(int index) {
        return (E) data[(head + index) & mask];
    }

    @Override
    public E set(int index, E e) {
        index = (head + index) & mask;
        E x = (E) data[index];
        data[index] = e;
        return x;
    }

    @Override
    public E removeAt(int index) {
        E x = get(index);
        if (index != 0) {
            for (int i = index + 1; i < size; i++) {
                set(i - 1, get(i));
            }
            set(size - 1, null);
        } else {
            set(0, null);
            head = (head + 1) & mask;
        }
        size--;
        return x;
    }

    @Override
    public int indexOf(E e) {
        for (int i = 0; i < size; i++) {
            E x = get(i);
            if (e == x || e != null && e.equals(x)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean offer(E e) {
        return offerLast(e);
    }

    @Override
    public boolean push(E e) {
        return offerFirst(e);
    }

    @Override
    public E peek() {
        return peekFirst();
    }

    @Override
    public E pop() {
        return pollFirst();
    }

    @Override
    public E poll() {
        return pollFirst();
    }
}
