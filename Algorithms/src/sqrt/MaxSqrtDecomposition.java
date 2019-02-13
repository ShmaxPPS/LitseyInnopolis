package sqrt;

import java.io.*;

public class MaxSqrtDecomposition {
    private long[] array;
    private long[] blocks;
    private int blockSize;

    public MaxSqrtDecomposition(long[] array) {
        this.array = array;
        this.blockSize = (int) Math.sqrt(array.length);
        int blockCount = (int) Math.ceil(1f * array.length / blockSize);
        blocks = new long[blockCount];
        for (int i = 0; i < blocks.length; ++i) {
            blocks[i] = Long.MIN_VALUE; // neutral element
        }
        for (int i = 0; i < array.length; ++i) {
            blocks[i / blockSize] = Math.max(blocks[i / blockSize], array[i]);
        }
    }

    // add operation value >= 0
    public void add(int index, long value) {
        array[index] += value;
        blocks[index / blockSize] = Math.max(blocks[index / blockSize], array[index]);
    }

    public long query(int l, int r) {
        int left = l / blockSize;
        int right = r / blockSize;
        long result = Long.MIN_VALUE;
        if (left == right) {
            for (int i = l; i <= r; ++i) {
                result = Math.max(result, array[i]);
            }
        } else {
            for (int i = l; i <= (left + 1) * blockSize - 1; ++i) {
                result = Math.max(array[i], result);
            }
            for (int i = left + 1; i <= right - 1; ++i) {
                result = Math.max(blocks[i], result);
            }
            for (int i = right * blockSize; i <= r; ++i) {
                result = Math.max(array[i], result);
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
        String[] lines = scanner.readLine().split(" ");
        int n = Integer.parseInt(lines[0]);
        lines = scanner.readLine().split(" ");
        long[] mass = new long[n];
        for (int i = 0; i < n; ++i) {
            mass[i] = Long.parseLong(lines[i]);
        }
        lines = scanner.readLine().split(" ");
        int k = Integer.parseInt(lines[0]);
        MaxSqrtDecomposition decomposition = new MaxSqrtDecomposition(mass);
        for (int i = 0; i < k; ++i) {
            lines = scanner.readLine().split(" ");
            int l = Integer.parseInt(lines[0]) - 1;
            int r = Integer.parseInt(lines[1]) - 1;
            writer.print(decomposition.query(l, r) + " ");
        }
        writer.close();
    }
}
