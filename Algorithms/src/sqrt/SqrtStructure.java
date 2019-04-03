package sqrt;

import java.io.*;
import java.util.Arrays;

public class SqrtStructure {

    private long[] array;
    private long[] addition;
    private long[][] sorted;
    private int blockSize;

    public SqrtStructure(long[] array) {
        this.array = array;
        this.blockSize = (int) Math.sqrt(array.length) * 4 / 5;
        int blockCount = (int) Math.ceil(1f * array.length / blockSize);
        this.addition = new long[blockCount];
        this.sorted = new long[blockCount][blockSize];
        for (int i = 0; i < blockCount; ++i) {
            sorted[i] = sort(i);
        }
    }

    public void add(int l, int r, int value) {
        int left = l / blockSize;
        int right = r / blockSize;
        if (left == right) {
            for (int i = l; i <= r; ++i) {
                array[i] += value;
            }
            sorted[left] = sort(left);
        } else {
            for (int i = l; i <= (left + 1) * blockSize - 1; ++i) {
                array[i] += value;
            }
            sorted[left] = sort(left);
            for (int i = left + 1; i <= right - 1; ++i) {
                addition[i] += value;
            }
            for (int i = right * blockSize; i <= r; ++i) {
                array[i] += value;
            }
            sorted[right] = sort(right);
        }
    }

    public long[] sort(int blockIndex) {
        int left = blockIndex * blockSize;
        int right = Math.min((blockIndex + 1) * blockSize, array.length);
        long[] block = Arrays.copyOfRange(array, left, right);
        Arrays.sort(block);
        return block;
    }

    public boolean check(int l, int r, int value) {
        int left = l / blockSize;
        int right = r / blockSize;
        if (left == right) {
            for (int i = l; i <= r; ++i) {
                if (array[i] == (value - addition[left])) {
                    return true;
                }
            }
        } else {
            for (int i = l; i <= (left + 1) * blockSize - 1; ++i) {
                if (array[i] == (value - addition[left])) {
                    return true;
                }
            }
            for (int i = left + 1; i <= right - 1; ++i) {
                if (Arrays.binarySearch(sorted[i], value - addition[i]) >= 0) {
                    return true;
                }
            }
            for (int i = right * blockSize; i <= r; ++i) {
                if (array[i] == (value - addition[right])) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
        String[] lines = scanner.readLine().split(" ");
        int n = Integer.parseInt(lines[0]);
        int m = Integer.parseInt(lines[1]);
        long array[] = new long[n];
        lines = scanner.readLine().split(" ");
        for (int i = 0; i < n; ++i) {
            array[i] = Integer.parseInt(lines[i]);
        }
        SqrtStructure sqrtStructure = new SqrtStructure(array);
        for (int i = 0; i < m; ++i) {
            lines = scanner.readLine().split(" ");
            int left = Integer.parseInt(lines[1]) - 1;
            int right = Integer.parseInt(lines[2]) - 1;
            int value = Integer.parseInt(lines[3]);
            if (lines[0].equals("+")) {
                sqrtStructure.add(left, right, value);
            }
            else if (lines[0].equals("?")){
                if (sqrtStructure.check(left, right, value)) {
                    writer.println("YES");
                }
                else {
                    writer.println("NO");
                }
            }
        }
        writer.close();
    }
}
