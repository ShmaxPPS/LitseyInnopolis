package segtree;

import java.io.*;

public class SegmentTreeSumRec {

    private int size;
    private long[] mass;
    private long[] segmentTree;

    public SegmentTreeSumRec(long[] mass) {
        size = 1 << ((int) (Math.log(mass.length) / Math.log(2)) + 1);
        this.mass = new long[size];
        for (int i = 0; i < mass.length; ++i) {
            this.mass[i] = mass[i];
        }
        for (int i = mass.length; i < size; ++i) {
            this.mass[i] = 0; // initial value
        }
        build();
    }

    private void build() {
        segmentTree = new long[2 * size];
        for (int i = 0; i < 2 * size; ++i) {
            segmentTree[i] = 0;
        }
        build(1, 0, size - 1);
    }

    // build segment tree
    private void build(int mi, int ml, int mr) {
        if (ml == mr) {
            segmentTree[mi] = mass[ml];
        } else {
            int mm = (ml + mr) / 2;
            build(2 * mi, ml, mm);
            build(2 * mi + 1, mm + 1, mr);
            segmentTree[mi] = segmentTree[2 * mi] + segmentTree[2 * mi + 1];
        }
    }

    // sum[a, b]
    public long sum(int a, int b) {
        return sum(1, 0, size - 1, a, b);
    }

    private long sum(int mi, int ml, int mr, int a, int b) {
        if (b < ml || mr < a) {
            return 0;
        }
        if (a <= ml && mr <= b) {
            return segmentTree[mi];
        }
        int mm = (ml + mr) / 2;
        long leftSum = sum(2 * mi, ml, mm, a, b);
        long rightSum = sum(2 * mi + 1, mm + 1, mr, a, b);
        return leftSum + rightSum;
    }

    public void set(int index, long value) {
        set(1, 0, size - 1, index, value);
    }

    private void set(int mi, int ml, int mr, int index, long value) {
        if (ml == mr) {
            segmentTree[mi] = value;
        } else {
            int mm = (mr + ml) / 2;
            if (index <= mm) {
                set(2 * mi, ml, mm, index, value);
            } else {
                set(2 * mi + 1, mm + 1, mr, index, value);
            }
            segmentTree[mi] = segmentTree[2 * mi] + segmentTree[2 * mi + 1];
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
        SegmentTreeSumRec tree = new SegmentTreeSumRec(mass);
        while ((line = scanner.readLine()) != null) {
            lines = line.split(" ");
            if (lines[0].equals("sum")) {
                int left = Integer.parseInt(lines[1]) - 1;
                int right = Integer.parseInt(lines[2]) - 1;
                writer.println(tree.sum(left, right));
            }
            else if (lines[0].equals("set")) {
                int index = Integer.parseInt(lines[1]) - 1;
                long value = Long.parseLong(lines[2]);
                tree.set(index, value);
            }
        }
        writer.close();
    }
}
