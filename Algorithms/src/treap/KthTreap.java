package treap;

import java.io.*;

public class KthTreap {
    private static final long MOD = 10000000000L;
    private Node root;

    public class Node {
        private Node left;
        private Node right;
        private long key;
        private long priority;
        private long count;

        public Node(long key) {
            this.key = key;
            this.priority = (long) (Math.random() * MOD);
            this.count = 1;
        }
    }

    private void validate(Node node) {
        if (node == null) {
            return;
        }
        node.count = 1;
        node.count += node.left != null ? node.left.count : 0;
        node.count += node.right != null ? node.right.count : 0;
    }

    private Node[] split(Node node, long key) {
        if (node == null) {
            return new Node[]{null, null, null};
        }
        if (node.key == key) {
            Node[] splittedNode = new Node[]{node.left, node, node.right};
            node.left = null;
            node.right = null;
            validate(node);
            return splittedNode;
        }
        if (node.key < key) {
            Node[] splittedRight = split(node.right, key);
            node.right = splittedRight[0];
            validate(node);
            return new Node[]{node, splittedRight[1], splittedRight[2]};
        }
        else {
            Node[] splittedLeft = split(node.left, key);
            node.left = splittedLeft[2];
            validate(node);
            return new Node[]{splittedLeft[0], splittedLeft[1], node};
        }
    }

    private Node merge(Node left, Node right) {
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }
        if (left.priority < right.priority) {
            left.right = merge(left.right, right);
            validate(left);
            return left;
        } else {
            right.left = merge(left, right.left);
            validate(right);
            return right;
        }
    }

    public void insert(long key) {
        if (root == null) {
            root = new Node(key);
            return;
        }
        Node[] splitted = split(root, key);
        Node middle = splitted[1] != null ? splitted[1] : new Node(key);
        root = merge(merge(splitted[0], middle), splitted[2]);
        validate(root);
    }

    public void remove(long key) {
        Node[] splitted = split(root, key);
        root = merge(splitted[0], splitted[2]);
        validate(root);
    }

    public long kthMin(long k) {
        Node node = kthNode(root, k);
        if (node == null) {
            return Long.MIN_VALUE;
        }
        return node.key;
    }

    public long kthMax(long k) {
        return kthMin(root.count + 1 - k);
    }

    private Node kthNode(Node node, long k) {
        if (node == null || node.count < k) {
            return null;
        }
        long leftCount = node.left != null ? node.left.count : 0;
        if (leftCount + 1 > k) {
            return kthNode(node.left, k);
        } else if (leftCount + 1 < k) {
            return kthNode(node.right, k - leftCount - 1);
        }
        else {
            return node;
        }
    }
}
