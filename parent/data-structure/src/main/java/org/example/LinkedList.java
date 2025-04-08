package org.example;

public class LinkedList<E> implements Deque<E>, Stack<E>, List<E> {
    private final Node<E> head;
    private final Node<E> tail;
    private int size;

    public LinkedList() {
        head = new Node<>();
        tail = new Node<>();
        head.next = tail;
        tail.prev = head;
    }

    private static <E> E unlinkNode(Node<E> curr, boolean relink) {
        if (relink) {
            curr.prev.next = curr.next;
            curr.next.prev = curr.prev;
        }
        E x = curr.setValue(null);
        curr.prev = curr.next = null;
        return x;
    }

    private static <E> void insertPrev(Node<E> curr, E e) {
        Node<E> node = new Node<>(e, curr.prev, curr);
        node.prev.next = node.next.prev = node;
    }

    private Node<E> getNode(int index) {
        Node<E> curr;
        if (index <= (size >>> 1)) {
            curr = head.next;
            while (index-- > 0) {
                curr = curr.next;
            }
        } else {
            curr = tail;
            while (index++ < size) {
                curr = curr.prev;
            }
        }
        return curr;
    }

    private Node<E> getNode(E e) {
        for (Node<E> curr = head.next; curr != tail; curr = curr.next) {
            if (e == curr.value || e != null && e.equals(curr.value)) {
                return curr;
            }
        }
        return null;
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
        Node<E> curr = head.next;
        while (curr != tail) {
            Node<E> next = curr.next;
            unlinkNode(curr, false);
            curr = next;
        }
        head.next = tail;
        tail.prev = head;
        size = 0;
    }

    @Override
    public boolean add(E e) {
        return offerLast(e);
    }

    @Override
    public boolean contains(E e) {
        return getNode(e) != null;
    }

    @Override
    public boolean remove(E e) {
        Node<E> node = getNode(e);
        if (node == null) {
            return false;
        }
        unlinkNode(node, true);
        size--;
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
            Node<E> curr = head;

            @Override
            public boolean hasNext() {
                return curr.next != tail;
            }

            @Override
            public E next() {
                return (curr = curr.next).value;
            }
        };
    }

    @Override
    public boolean insert(int index, E e) {
        insertPrev(getNode(index), e);
        size++;
        return true;
    }

    @Override
    public E get(int index) {
        return getNode(index).value;
    }

    @Override
    public E set(int index, E e) {
        return getNode(index).setValue(e);
    }

    @Override
    public E removeAt(int index) {
        E x = unlinkNode(getNode(index), true);
        size--;
        return x;
    }

    @Override
    public int indexOf(E e) {
        int index = 0;
        for (Node<E> curr = head.next; curr != tail; curr = curr.next) {
            if (e == curr.value || e != null && e.equals(curr.value)) {
                return index;
            }
            index++;
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

    private static final class Node<E> {
        public E value;
        public Node<E> prev;
        public Node<E> next;

        public Node() {
        }

        public Node(E value, Node<E> prev, Node<E> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }

        public E setValue(E value) {
            E x = this.value;
            this.value = value;
            return x;
        }
    }
}
