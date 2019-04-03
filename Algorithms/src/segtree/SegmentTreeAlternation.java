package segtree;

import java.io.*;

public class SegmentTreeAlternation {

    private static class Segment {
        private int even;
        private int odd;

        public Segment(int even, int odd) {
            this.even = even;
            this.odd = odd;
        }

        public Segment(Segment left, Segment right) {
            this.even = left.getEven() + right.getEven();
            this.odd = left.getOdd() + right.getOdd();
        }

        public int getEven() {
            return even;
        }

        public int getOdd() {
            return odd;
        }
    }

    // set element to segment tree
    public static void set(Segment[] segmentTree, int index, int value) {
        int temp = index + segmentTree.length / 2;
        segmentTree[temp] = index % 2 == 0 ? new Segment(value, 0) : new Segment(0, value);
        while (temp > 1) {
            segmentTree[temp >> 1] = new Segment(segmentTree[temp], segmentTree[temp ^ 1]);
            temp >>= 1;
        }
    }

    // build segment tree from mass
    public static Segment[] buildSlow(int[] mass) {
        // next power of 2
        int size = 1 << ((int) (Math.log(mass.length) / Math.log(2)) + 1);
        Segment[] segmentTree = new Segment[2 * size];
        for (int i = 0; i < 2 * size; ++i) {
            segmentTree[i] = new Segment(0, 0);
        }
        for (int i = 0; i < mass.length; ++i) {
            set(segmentTree, i, mass[i]);
        }
        return segmentTree;
    }

    // alternation[a, b]
    public static int alternation(Segment[] segmentTree, int a, int b) {
        int result = 0;
        int factor = a % 2 == 0 ? 1 : -1;
        int left = a + segmentTree.length / 2;
        int right = b + segmentTree.length / 2;
        while (left <= right) {
            if ((left & 1) != 0) {
                result += factor * (segmentTree[left].getEven() - segmentTree[left].getOdd());
            }
            if ((right & 1) == 0) {
                result += factor * (segmentTree[right].getEven() - segmentTree[right].getOdd());
            }
            left = (left + 1) >> 1;
            right = (right - 1) >> 1;
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(scanner.readLine());
        String[] lines = scanner.readLine().split(" ");
        int[] mass = new int[n];
        for (int i = 0; i < n; ++i) {
            mass[i] = Integer.parseInt(lines[i]);
        }
        Segment[] segmentTree = buildSlow(mass);
        int m = Integer.parseInt(scanner.readLine());
        for (int i = 0; i < m; ++i) {
            lines = scanner.readLine().split(" ");
            int operation = Integer.parseInt(lines[0]);
            if (operation == 0) {
                int index = Integer.parseInt(lines[1]) - 1;
                int value = Integer.parseInt(lines[2]);
                set(segmentTree, index, value);
            }
            else {
                int left = Integer.parseInt(lines[1]) - 1;
                int right = Integer.parseInt(lines[2]) - 1;
                writer.println(alternation(segmentTree, left, right)) ;
            }
        }
        writer.close();
    }

}
