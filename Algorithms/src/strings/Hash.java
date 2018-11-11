package strings;

import java.io.*;

public class Hash {
    private static final long PRIME = 31L;
    private static final long MOD = 1000000009L;

    long[] execute(String string) {
        long[] h = new long[string.length()];
        h[0] = string.charAt(0);
        for (int i = 1; i < string.length(); ++i) {
            h[i] = (h[i - 1] * PRIME + string.charAt(i)) % MOD;
        }
        return h;
    }

    static long[] powers(String string) {
        long[] p = new long[string.length() + 1];
        p[0] = 1;
        for (int i = 1; i < string.length() + 1; ++i) {
            p[i] = (p[i - 1] * PRIME) % MOD;
        }
        return p;
    }

    static long hash(int left, int right, long[] h, long[] p) {
        long hashRight = h[right];
        long hashLeft = left == 0 ? 0 : h[left - 1];
        return ((hashRight - hashLeft * p[right - left + 1]) % MOD + MOD) % MOD;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        String string = reader.readLine();
        Hash hash = new Hash();
        long[] h = hash.execute(string);
        long[] p = powers(string);
        int n = Integer.parseInt(reader.readLine());
        for (int i = 0; i < n; ++i) {
            String line = reader.readLine();
            String[] parts = line.split(" ");
            int a = Integer.parseInt(parts[0]) - 1;
            int b = Integer.parseInt(parts[1]) - 1;
            int c = Integer.parseInt(parts[2]) - 1;
            int d = Integer.parseInt(parts[3]) - 1;
            long hashOne = hash(a, b, h, p);
            long hashTwo = hash(c, d, h, p);
            if (hashOne == hashTwo) {
                writer.write("Yes");
            }
            else {
                writer.write("No");
            }
            writer.newLine();
        }
        writer.close();
    }
}
