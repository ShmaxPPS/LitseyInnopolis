package sqrt;

import java.io.*;
import java.util.Arrays;

/**
 * Problem:
 * https://codeforces.com/problemset/problem/86/D
 */
public class MosAlgorithm {

    private static int blockSize;

    public static class Query implements Comparable<Query> {
        private int left;
        private int right;
        private int index;

        public Query(int left, int right, int index) {
            this.left = left;
            this.right = right;
            this.index = index;
        }

        @Override
        public int compareTo(Query o) {
            int compare = Integer.compare(left / blockSize, o.left / blockSize);
            if (compare == 0) {
                return Integer.compare(right, o.right);
            }
            return compare;
        }
    }

    public static long add(int[] a, int[] cnt, int index) {
        ++cnt[a[index]];
        return (long)(2 * cnt[a[index]] - 1) * a[index];
    }

    public static long remove(int[] a, int[] cnt, int index) {
        --cnt[a[index]];
        return (long)(2 * cnt[a[index]] + 1) * a[index];
    }

    public static long[] process(int[] a, Query[] queries) {
        Arrays.sort(queries);
        int[] cnt = new int[1000001];
        long[] result = new long[queries.length];
        int left = 1;
        int right = 0;
        long cur = 0;
        for (Query query : queries) {
            while (left < query.left) {
                cur -= remove(a, cnt, left++);
            }
            while (left > query.left) {
                cur += add(a, cnt, --left);
            }
            while (right < query.right) {
                cur += add(a, cnt, ++right);
            }
            while (right > query.right) {
                cur -= remove(a, cnt, right--);
            }
            result[query.index] = cur;
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
        String[] lines = scanner.readLine().split(" ");
        int n = Integer.parseInt(lines[0]);
        int m = Integer.parseInt(lines[1]);
        blockSize = (int) Math.sqrt(n);
        int a[] = new int[n];
        lines = scanner.readLine().split(" ");
        for (int i = 0; i < n; ++i) {
            a[i] = Integer.parseInt(lines[i]);
        }
        Query[] queries = new Query[m];
        for (int i = 0; i < m; ++i) {
            lines = scanner.readLine().split(" ");
            int left = Integer.parseInt(lines[0]) - 1;
            int right = Integer.parseInt(lines[1]) - 1;
            queries[i] = new Query(left, right, i);
        }
        long[] result = process(a, queries);
        for (int i = 0; i < m; ++i) {
            writer.println(result[i]);
        }
        writer.close();
    }
}