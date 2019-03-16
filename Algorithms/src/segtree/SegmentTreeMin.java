package segtree;

import java.io.*;

// min queries
public class SegmentTreeMin {

    // set element to segment tree
    public static void set(int[] segmentTree, int index, int value) {
        int temp = index + segmentTree.length / 2;
        segmentTree[temp] = value;
        while (temp > 1) {
            segmentTree[temp >> 1] = Math.min(segmentTree[temp], segmentTree[temp ^ 1]);
            temp >>= 1;
        }
    }

    // build segment tree from mass
    public static int[] build(int[] mass) {
        // next power of 2
        int size = 1 << ((int) (Math.log(mass.length) / Math.log(2)) + 1);
        int[] segmentTree = new int[2 * size];
        for (int i = 0; i < 2 * size; ++i) {
            segmentTree[i] = Integer.MAX_VALUE;
        }
        for (int i = 0; i < mass.length; ++i) {
            set(segmentTree, i, mass[i]);
        }
        return segmentTree;
    }

    // min[a, b]
    public static int min(int[] segmentTree, int a, int b) {
        int result = Integer.MAX_VALUE;
        int left = a + segmentTree.length / 2;
        int right = b + segmentTree.length / 2;
        while (left <= right) {
            if ((left & 1) != 0) {
                result = Math.min(result, segmentTree[left]);
            }
            if ((right & 1) == 0) {
                result = Math.min(result, segmentTree[right]);
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
        String[] lines = scanner.readLine().split(" ");
        int n = Integer.parseInt(lines[0]);
        int k = Integer.parseInt(lines[1]);

        lines = scanner.readLine().split(" ");
        int[] mass = new int[n];
        for (int i = 0; i < n; ++i) {
            mass[i] = Integer.parseInt(lines[i]);
        }
        int[] segmentTree = build(mass);
        for (int i = 0; i < mass.length - k + 1; ++i) {
            writer.println(min(segmentTree, i, i + k - 1));
        }
        writer.close();
    }
}


