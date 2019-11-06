package treap;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ImplicitKeyTreap {
    private static final long MOD = 10000000000L;
    private Node root;

    public class Node {
        private Node left;
        private Node right;
        private long priority;
        private long count;
        private long value;

        public Node(long value) {
            this.priority = (long) (Math.random() * MOD);
            this.count = 1;
            this.value = value;
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

    private long getImplicitKey(Node node) {
        if (node == null) {
            return Long.MIN_VALUE;
        }
        return node.left != null ? node.left.count : 0;
    }

    private Node[] split(Node node, long index) {
        if (node == null) {
            return new Node[]{null, null, null};
        }
        long implicitKey = getImplicitKey(node);
        if (implicitKey == index) {
            Node[] splittedNode = new Node[]{node.left, node, node.right};
            node.left = null;
            node.right = null;
            validate(node);
            return splittedNode;
        }
        if (implicitKey < index) {
            Node[] splittedRight = split(node.right, index - implicitKey - 1);
            node.right = splittedRight[0];
            validate(node);
            return new Node[]{node, splittedRight[1], splittedRight[2]};
        }
        else {
            Node[] splittedLeft = split(node.left, index);
            node.left = splittedLeft[2];
            validate(node);
            return new Node[]{splittedLeft[0], splittedLeft[1], node};
        }
    }

    private Node[] split(Node node, long... indexes) {
        List<Node> nodes = new ArrayList<>();
        Node tail = node;
        long shift = 0;
        for (long index : indexes) {
            Node[] splitted = split(tail, index - shift);
            nodes.add(splitted[0]);
            nodes.add(splitted[1]);
            tail = splitted[2];
            if (splitted[0] != null) {
                shift += splitted[0].count;
            }
            if (splitted[1] != null) {
                shift += splitted[1].count;
            }
        }
        nodes.add(tail);
        return nodes.stream().toArray(Node[]::new);
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

    private Node merge(Node... nodes) {
        Node merged = null;
        for (Node node : nodes) {
            merged = merge(merged, node);
        }
        return merged;
    }

    public void insert(long index, long value) {
        if (root == null) {
            root = new Node(value);
            return;
        }
        Node[] splitted = split(root, index);
        Node middle = splitted[1] != null ? splitted[1] : new Node(value);
        root = merge(splitted[0], middle, splitted[2]);
    }

    public void remove(long index) {
        Node[] splitted = split(root, index);
        root = merge(splitted[0], splitted[2]);
    }

    public void moveToStart(long left, long right) {
        Node[] splitted = split(root, left - 1, right + 1);
        root = merge(splitted[2], splitted[0], splitted[1], splitted[3], splitted[4]);
    }

    public void write(PrintWriter writer) {
        write(root, writer);
    }

    private void write(Node node, PrintWriter writer) {
        if (node == null) {
            return;
        }
        write(node.left, writer);
        writer.print(node.value + " ");
        write(node.right, writer);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
        String line = scanner.readLine();
        String[] lines = line.split(" ");
        int n = Integer.parseInt(lines[0]);
        int m = Integer.parseInt(lines[1]);
        ImplicitKeyTreap treap = new ImplicitKeyTreap();
        for (int i = 0; i < n; ++i) {
            treap.insert(i, i + 1);
        }
        for (int i = 0; i < m; ++i) {
            line = scanner.readLine();
            lines = line.split(" ");
            int left = Integer.parseInt(lines[0]) - 1;
            int right = Integer.parseInt(lines[1]) - 1;
            treap.moveToStart(left, right);
        }
        treap.write(writer);
        writer.close();
    }
}
