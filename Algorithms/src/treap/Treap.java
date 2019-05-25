package treap;

import java.io.*;

public class Treap {
    private static final long MOD = 10000000000L;
    private Node root;

    public class Node {
        private Node left;
        private Node right;
        private long key;
        private long priority;

        public Node(long key) {
            this.key = key;
            this.priority = (long) (Math.random() * MOD);
        }
    }

    private Node[] split(Node node, long key) {
        if (node == null) {
            return new Node[]{null, null, null};
        }
        if (node.key == key) {
            Node[] splittedNode = new Node[]{node.left, node, node.right};
            node.left = null;
            node.right = null;
            return splittedNode;
        }
        if (node.key < key) {
            Node[] splittedRight = split(node.right, key);
            node.right = splittedRight[0];
            return new Node[]{node, splittedRight[1], splittedRight[2]};
        }
        else {
            Node[] splittedLeft = split(node.left, key);
            node.left = splittedLeft[2];
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
            return left;
        } else {
            right.left = merge(left, right.left);
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
    }

    public void remove(long key) {
        Node[] splitted = split(root, key);
        root = merge(splitted[0], splitted[2]);
    }

    public boolean exist(long key) {
        Node[] splitted = split(root, key);
        root = merge(merge(splitted[0], splitted[1]), splitted[2]);
        return splitted[1] != null;
    }

    public long next(long key) {
        Node[] splitted = split(root, key);
        Node nextNode = minNode(splitted[2]);
        root = merge(merge(splitted[0], splitted[1]), splitted[2]);
        return nextNode != null ? nextNode.key : Long.MIN_VALUE;
    }

    public long prev(long key) {
        Node[] splitted = split(root, key);
        Node prevNode = maxNode(splitted[0]);
        root = merge(merge(splitted[0], splitted[1]), splitted[2]);
        return prevNode != null ? prevNode.key : Long.MIN_VALUE;
    }

    public long min() {
        Node minNode = minNode(root);
        return minNode != null ? minNode.key : Long.MIN_VALUE;
    }

    public long max() {
        Node maxNode = maxNode(root);
        return maxNode != null ? maxNode.key : Long.MIN_VALUE;
    }

    private Node minNode(Node node) {
        if (node == null) {
            return null;
        }
        Node min = node;
        while (min.left != null) {
            min = min.left;
        }
        return min;
    }

    private Node maxNode(Node node) {
        if (node == null) {
            return null;
        }
        Node max = node;
        while (max.right != null) {
            max = max.right;
        }
        return max;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
        Treap treap = new Treap();
        String line;
        while ((line = scanner.readLine()) != null) {
            String[] lines = line.split(" ");
            long key = Long.parseLong(lines[1]);
            if (lines[0].equals("insert")) {
                treap.insert(key);
            } else if (lines[0].equals("delete")) {
                treap.remove(key);
            } else if (lines[0].equals("exists")) {
                writer.println(treap.exist(key));
            } else if (lines[0].equals("next")) {
                long next = treap.next(key);
                writer.println(next != Long.MIN_VALUE ? next : "none");
            } else if (lines[0].equals("prev")) {
                long prev = treap.prev(key);
                writer.println(prev != Long.MIN_VALUE ? prev : "none");
            }
        }
        writer.close();
    }
}
