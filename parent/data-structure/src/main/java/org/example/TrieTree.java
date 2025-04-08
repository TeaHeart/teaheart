package org.example;

public class TrieTree {
    private final Node root = new Node();

    private Node getNode(String prefix) {
        Node curr = root;
        for (char c : prefix.toCharArray()) {
            Node next = curr.children[c];
            if (next == null) {
                return null;
            }
            curr = next;
        }
        return curr;
    }

    private void clear(Node root) {
        if (root == null) {
            return;
        }
        Node[] children = root.children;
        for (int i = 0; i < children.length; i++) {
            clear(children[i]);
            children[i] = null;
        }
    }

    public boolean insert(String word) {
        Node curr = root;
        for (char c : word.toCharArray()) {
            Node next = curr.children[c];
            if (next == null) {
                curr.children[c] = next = new Node();
            }
            curr = next;
        }
        if (curr.isWord) {
            return false;
        }
        curr.isWord = true;
        return true;
    }

    public boolean contains(String word) {
        Node node = getNode(word);
        return node != null && node.isWord;
    }

    public boolean remove(String word) {
        Node curr = getNode(word);
        if (curr == null || !curr.isWord) {
            return false;
        }
        curr.isWord = false;
        return true;
    }

    public boolean containsPrefix(String prefix) {
        return getNode(prefix) != null;
    }

    public boolean removePrefix(String prefix) {
        Node node = getNode(prefix);
        if (node == null) {
            return false;
        }
        node.isWord = false;
        clear(node);
        return true;
    }

    private static class Node {
        private final Node[] children = new Node[128];
        private boolean isWord;
    }
}
