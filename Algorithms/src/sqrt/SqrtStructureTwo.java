package sqrt;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class SqrtStructureTwo {
    private long[] array;
    private long[] addition;
    private Set<Long>[] sets;
    private int blockSize;

    public SqrtStructureTwo(long[] array) {
        this.array = array;
        this.blockSize = (int) Math.sqrt(array.length) / 3;
        int blockCount = (int) Math.ceil(1f * array.length / blockSize);
        this.addition = new long[blockCount];
        this.sets = new HashSet[blockCount];
        for (int i = 0; i < blockCount; ++i) {
            sets[i] = set(i);
        }
    }

    public void add(int l, int r, int value) {
        int left = l / blockSize;
        int right = r / blockSize;
        if (left == right) {
            for (int i = l; i <= r; ++i) {
                array[i] += value;
            }
            sets[left] = set(left);
        } else {
            for (int i = l; i <= (left + 1) * blockSize - 1; ++i) {
                array[i] += value;
            }
            sets[left] = set(left);
            for (int i = left + 1; i <= right - 1; ++i) {
                addition[i] += value;
            }
            for (int i = right * blockSize; i <= r; ++i) {
                array[i] += value;
            }
            sets[right] = set(right);
        }
    }

    public Set<Long> set(int blockIndex) {
        int left = blockIndex * blockSize;
        int right = Math.min((blockIndex + 1) * blockSize, array.length);
        Set<Long> set = new HashSet<>();
        for (int i = left; i < right; ++i) {
            set.add(array[i]);
        }
        return set;
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
                if (sets[i].contains(value - addition[i])) {
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
        SqrtStructureTwo sqrtStructure = new SqrtStructureTwo(array);
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
