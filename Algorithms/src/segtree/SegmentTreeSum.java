package segtree;

import java.io.*;

// sum
public class SegmentTreeSum {
    // set element to segment tree
    public static void set(long[] segmentTree, int index, long value) {
        int temp = index + segmentTree.length / 2;
        segmentTree[temp] = value;
        while (temp > 1) {
            segmentTree[temp >> 1] = segmentTree[temp] + segmentTree[temp ^ 1];
            temp >>= 1;
        }
    }

    // build segment tree from mass
    public static long[] build(long[] mass) {
        // next power of 2
        int size = 1 << ((int) (Math.log(mass.length) / Math.log(2)) + 1);
        long[] segmentTree = new long[2 * size];
        for (int i = 0; i < 2 * size; ++i) {
            segmentTree[i] = 0;
        }
        for (int i = 0; i < mass.length; ++i) {
            set(segmentTree, i, mass[i]);
        }
        return segmentTree;
    }

    // sum[a, b]
    public static long sum(long[] segmentTree, int a, int b) {
        long result = 0;
        int left = a + segmentTree.length / 2;
        int right = b + segmentTree.length / 2;
        while (left <= right) {
            if ((left & 1) != 0) {
                result += segmentTree[left];
            }
            if ((right & 1) == 0) {
                result += segmentTree[right];
            }
            left = (left + 1) >> 1;
            right = (right - 1) >> 1;
        }
        return result;
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
        long[] segmentTree = build(mass);
        while ((line = scanner.readLine()) != null) {
            lines = line.split(" ");
            if (lines[0].equals("sum")) {
                int left = Integer.parseInt(lines[1]) - 1;
                int right = Integer.parseInt(lines[2]) - 1;
                writer.println(sum(segmentTree, left, right));
            }
            else if (lines[0].equals("set")) {
                int index = Integer.parseInt(lines[1]) - 1;
                long value = Long.parseLong(lines[2]);
                set(segmentTree, index, value);
            }
        }
        writer.close();
    }
}
