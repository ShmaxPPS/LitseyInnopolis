package segtree;

import java.io.*;

// max queries with index
public class SegmentTreeMax {

    private static class Segment {
        private int index;
        private int max;

        public Segment(int index, int max) {
            this.index = index;
            this.max = max;
        }

        public Segment(Segment copy) {
            this.index = copy.getIndex();
            this.max = copy.getMax();
        }

        public int getIndex() {
            return index;
        }

        public int getMax() {
            return max;
        }
    }

    // set element to segment tree
    public static void set(Segment[] segmentTree, int index, int value) {
        int temp = index + segmentTree.length / 2;
        segmentTree[temp] = new Segment(index, value);
        while (temp > 1) {
            if (segmentTree[temp].getMax() > segmentTree[temp ^ 1].getMax()) {
                segmentTree[temp >> 1] = new Segment(segmentTree[temp]);
            } else {
                segmentTree[temp >> 1] = new Segment(segmentTree[temp ^ 1]);
            }
            temp >>= 1;
        }
    }

    // build segment tree from mass O(n)
    public static Segment[] buildFast(int[] mass) {
        // next power of 2
        int size = 1 << ((int) (Math.log(mass.length) / Math.log(2)) + 1);
        Segment[] segmentTree = new Segment[2 * size];
        for (int i = 0; i < 2 * size; ++i) {
            segmentTree[i] = new Segment(Integer.MIN_VALUE, Integer.MIN_VALUE);
        }
        for (int i = 0; i < mass.length; ++i) {
            segmentTree[i + size] = new Segment(i, mass[i]);
        }
        for (int i = size - 1; i >= 1; --i) {
            if (segmentTree[i << 1].getMax() > segmentTree[(i << 1) + 1].getMax()) {
                segmentTree[i] = new Segment(segmentTree[i << 1]);
            } else {
                segmentTree[i] = new Segment(segmentTree[(i << 1) + 1]);
            }
        }
        return segmentTree;
    }

    // build segment tree from mass
    public static Segment[] buildSlow(int[] mass) {
        // next power of 2
        int size = 1 << ((int) (Math.log(mass.length) / Math.log(2)) + 1);
        Segment[] segmentTree = new Segment[2 * size];
        for (int i = 0; i < 2 * size; ++i) {
            segmentTree[i] = new Segment(Integer.MIN_VALUE, Integer.MIN_VALUE);
        }
        for (int i = 0; i < mass.length; ++i) {
            set(segmentTree, i, mass[i]);
        }
        return segmentTree;
    }

    // max[a, b]
    public static Segment max(Segment[] segmentTree, int a, int b) {
        int max = Integer.MIN_VALUE;
        int maxIndex = Integer.MIN_VALUE;
        int left = a + segmentTree.length / 2;
        int right = b + segmentTree.length / 2;
        while (left <= right) {
            if ((left & 1) != 0) {
                if (segmentTree[left].getMax() > max) {
                    maxIndex = segmentTree[left].getIndex();
                    max = segmentTree[left].getMax();
                }
            }
            if ((right & 1) == 0) {
                if (segmentTree[right].getMax() > max) {
                    maxIndex = segmentTree[right].getIndex();
                    max = segmentTree[right].getMax();
                }
            }
            left = (left + 1) >> 1;
            right = (right - 1) >> 1;
        }
        return new Segment(maxIndex, max);
    }

    // Usage example
    public static void main(String[] args) throws IOException {
        BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(scanner.readLine());
        String[] lines = scanner.readLine().split(" ");
        int[] mass = new int[n];
        for (int i = 0; i < n; ++i) {
            mass[i] = Integer.parseInt(lines[i]);
        }
        Segment[] segmentTree = buildFast(mass);
        // Segment[] segmentTree = buildSlow(mass);
        int k = Integer.parseInt(scanner.readLine());
        for (int i = 0; i < k; ++i) {
            lines = scanner.readLine().split(" ");
            int left = Integer.parseInt(lines[0]) - 1;
            int right = Integer.parseInt(lines[1]) - 1;
            Segment segment = max(segmentTree, left, right);
            writer.println(segment.getMax() + " " + (segment.getIndex() + 1)) ;
        }
        writer.close();
    }
}
