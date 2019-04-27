package segtree;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistentSegmentTreeSum {

    private long[] mass;
    private List<Node> roots;

    private class Node {
        private Node leftNode;
        private Node rightNode;
        private long sum;
        private int leftIndex;
        private int rightIndex;

        // constructor for leaf node
        public Node(int leftIndex, int rightIndex, long sum) {
            this.leftIndex = leftIndex;
            this.rightIndex = rightIndex;
            this.sum = sum;
        }

        // constructor for intermediate node
        public Node(Node leftNode, Node rightNode) {
            this.leftIndex = leftNode.leftIndex;
            this.rightIndex = rightNode.rightIndex;
            this.sum = leftNode.sum + rightNode.sum;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
        }
    }

    public PersistentSegmentTreeSum(long[] mass) {
        int size = 1 << ((int) (Math.log(mass.length) / Math.log(2)) + 1);
        this.mass = new long[size];
        for (int i = 0; i < mass.length; ++i) {
            this.mass[i] = mass[i];
        }
        for (int i = mass.length; i < size; ++i) {
            this.mass[i] = 0; // initial value
        }
        roots = new ArrayList<>();
        Node initialRoot = build( 0, size - 1);
        roots.add(initialRoot);
    }

    // build segment tree
    private Node build(int ml, int mr) {
        if (ml == mr) {
            return new Node(ml, mr, mass[ml]); // leaf node
        } else {
            int mm = (ml + mr) / 2;
            Node leftNode = build(ml, mm);
            Node rightNode = build(mm + 1, mr);
            return new Node(leftNode, rightNode);
        }
    }

    // sum[left, right] in last history point
    public long sum(int left, int right) {
        return sum(left, right, roots.size() - 1);
    }

    // sum[left, right] in history point = time
    public long sum(int left, int right, int time) {
        if (roots.size() <= time) {
            throw new RuntimeException("Error: time is bigger than history of segment trees");
        }
        return sum(roots.get(time), left, right);
    }

    private long sum(Node node, int left, int right) {
        if (right < node.leftIndex || node.rightIndex < left) {
            return 0;
        }
        if (left <= node.leftIndex && node.rightIndex <= right) {
            return node.sum;
        }
        return sum(node.leftNode, left, right) + sum(node.rightNode, left, right);
    }

    public void set(int index, long value) {
        Node prevVersion = roots.get(roots.size() - 1);
        Node nextVersion = set(prevVersion, index, value);
        roots.add(nextVersion);
    }

    private Node set(Node prevVersion, int index, long value) {
        if (prevVersion.leftIndex == index && prevVersion.rightIndex == index) {
            return new Node(index, index, value);
        } else {
            int mm = (prevVersion.leftIndex + prevVersion.rightIndex) / 2;
            Node prevVersionLeft = prevVersion.leftNode;
            Node prevVersionRight = prevVersion.rightNode;
            if (index <= mm) {
                Node nextVersionLeft = set(prevVersionLeft, index, value);
                return new Node(nextVersionLeft, prevVersionRight);
            } else {
                Node nextVersionRight = set(prevVersionRight, index, value);
                return new Node(prevVersionLeft, nextVersionRight);
            }
        }
    }


    // Usage example
    public static void main(String[] args) throws IOException {
        BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
        String line = scanner.readLine();
        int n = Integer.parseInt(line);
        line = scanner.readLine();
        String[] lines = line.split(" ");
        long[] mass = new long[n];
        for (int i = 0; i < n; ++i) {
            mass[i] = Long.parseLong(lines[i]);
        }
        PersistentSegmentTreeSum segmentTreeSum = new PersistentSegmentTreeSum(mass);
        while ((line = scanner.readLine()) != null) {
            lines = line.split(" ");
            if (lines[0].equals("sum")) {
                int left = Integer.parseInt(lines[1]) - 1;
                int right = Integer.parseInt(lines[2]) - 1;
                long sum = segmentTreeSum.sum(left, right);
                writer.println(sum);
            }
            else if (lines[0].equals("set")) {
                int index = Integer.parseInt(lines[1]) - 1;
                long value = Long.parseLong(lines[2]);
                segmentTreeSum.set(index, value);
            }
        }
        writer.close();
    }
}


