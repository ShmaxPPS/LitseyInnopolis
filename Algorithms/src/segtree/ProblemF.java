package segtree;

import java.io.*;

public class ProblemF {

    private static long max = Long.MIN_VALUE;
    private static int leftMax = Integer.MIN_VALUE;
    private static int rightMax = Integer.MIN_VALUE;

    private static class Segment {
        private int index;
        private long min;

        public Segment(int index, long min) {
            this.index = index;
            this.min = min;
        }

        public Segment(Segment copy) {
            this.index = copy.getIndex();
            this.min = copy.getMin();
        }

        public int getIndex() {
            return index;
        }

        public long getMin() {
            return min;
        }
    }

    // build segment tree from mass O(n)
    public static Segment[] buildFast(long[] mass) {
        // next power of 2
        int size = 1 << ((int) (Math.log(mass.length) / Math.log(2)) + 1);
        Segment[] segmentTree = new Segment[2 * size];
        for (int i = 0; i < 2 * size; ++i) {
            segmentTree[i] = new Segment(Integer.MAX_VALUE, Long.MAX_VALUE);
        }
        for (int i = 0; i < mass.length; ++i) {
            segmentTree[i + size] = new Segment(i, mass[i]);
        }
        for (int i = size - 1; i >= 1; --i) {
            if (segmentTree[i << 1].getMin() < segmentTree[(i << 1) + 1].getMin()) {
                segmentTree[i] = new Segment(segmentTree[i << 1]);
            }
            else {
                segmentTree[i] = new Segment(segmentTree[(i << 1) + 1]);
            }
        }
        return segmentTree;
    }

    // min[a, b]
    public static Segment min(Segment[] segmentTree, int l, int r) {
        long min = Long.MAX_VALUE;
        int minIndex = Integer.MAX_VALUE;
        int left = l + segmentTree.length / 2;
        int right = r + segmentTree.length / 2;
        while (left <= right) {
            if ((left & 1) != 0) {
                if (segmentTree[left].getMin() < min) {
                    min = segmentTree[left].getMin();
                    minIndex = segmentTree[left].getIndex();
                }
            }
            if ((right & 1) == 0) {
                if (segmentTree[right].getMin() < min) {
                    min = segmentTree[right].getMin();
                    minIndex = segmentTree[right].getIndex();
                }
            }
            left = (left + 1) >> 1;
            right = (right - 1) >> 1;
        }
        return new Segment(minIndex, min);
    }

    public static long[] prefixes(long[] mass) {
        long[] prefixes = new long[mass.length + 1];
        prefixes[0] = 0;
        for (int i = 0; i < mass.length; ++i) {
            prefixes[i + 1] = prefixes[i] + mass[i];
        }
        return prefixes;
    }

    public static long sum(int l, int r, long[] prefixes) {
        return prefixes[r + 1] - prefixes[l];
    }

    public static void importance(Segment[] segmentTree, int l, int r, long[] prefixes) {
        if (l > r) {
            return;
        }
        Segment min = min(segmentTree, l, r);
        long currentMax = sum(l, r, prefixes) * min.getMin();
        if (max < currentMax) {
            max = currentMax;
            leftMax = l;
            rightMax = r;
        }
        importance(segmentTree, l, min.getIndex() - 1, prefixes);
        importance(segmentTree, min.getIndex() + 1, r, prefixes);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
        int n = Integer.parseInt(scanner.readLine());
        String[] lines = scanner.readLine().split(" ");
        long[] mass = new long[n];
        for (int i = 0; i < n; ++i) {
            mass[i] = Integer.parseInt(lines[i]);
        }
        Segment[] segmentTree = buildFast(mass);
        long[] prefixes = prefixes(mass);
        importance(segmentTree, 0, n - 1, prefixes);
        writer.println(max);
        writer.println((leftMax + 1) + " " + (rightMax + 1));
        writer.close();
    }

}
