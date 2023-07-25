import java.util.Iterator;
import java.util.Stack;

public class BST<K extends Comparable<K>, V> implements Iterable <BST<K, V>. BSTEntry>{
    private Node root;
    private int size;
    public BST() {
        root = null;
        size = 0;
    }
    public void put(K key, V value) {
        root = put(root, key, value);
    }
    private Node put(Node node, K key, V value) {
        if(node == null){
            size++;
            return new Node(key, value);
        }
        int cmp = key.compareTo(node.key);
        if(cmp < 0) {
            node.left = put(node.left, key, value);
        }
        else if(cmp > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value;
        }
        return node;
    }
    public V get(K key) {
        Node node = get(root, key);
        return node == null ? null : node.value;
    }
    private Node get(Node node, K key) {
        if(node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if(cmp < 0) {
            return get(node.left, key);
        }
        else if(cmp > 0) {
            return get(node.right, key);
        } else {
            return node;
        }
    }
    public void delete(K key) {
        root = delete(root, key);
    }
    private Node delete(Node node, K key) {
        if(node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if(cmp < 0) {
            node.left = delete(node.left, key);
        }
        else if(cmp > 0) {
            node.right = delete(node.right, key);
        } else {
            if(node.left == null) {
                return node.right;
            }
            else if(node.right == null) {
                    return node.left;
            } else {
                Node minNode = findMin(node.right);
                node.key = minNode.key;
                node.value = minNode.value;
                node.right = delete(node.right, minNode.key);
                }
        }
        return node;
    }
    private Node findMin(Node node) {
        if(node.left == null){
            return node;
        }
        return findMin(node.left);
    }
    public int size() {
        return size;
    }
    public class BSTEntry {
        private K key;
        private V value;

        public BSTEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
        public K getKey() {
            return key;
        }
        public V getValue() {
            return value;
        }
    }
    private class Node {
        private K key;
        private V value;
        private Node left;
        private Node right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }
    private class InOrderIterator implements Iterator<BSTEntry> {
        private Node current;
        private Stack<Node> stack;

        public InOrderIterator() {
            current = root;
            stack = new Stack<>();
        }
        @Override
        public boolean hasNext() {
            return current != null || !stack.isEmpty();
        }
        @Override
        public BSTEntry next() {
            while(current != null) {
                stack.push(current);
                current = current.left;
            }
            if(!hasNext()) {
                throw new java.util.NoSuchElementException("No more elements to iterate.");
            }
            Node node = stack.pop();
            current = node.right;
            return new BSTEntry(node.key, node.value);
        }
    }
    @Override
    public Iterator<BSTEntry> iterator() {
        return new InOrderIterator();
    }
}
