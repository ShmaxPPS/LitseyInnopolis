package strings;

import java.io.*;

public class ZFunction {

    int[] execute(String string) {
        int[] z = new int[string.length()];
        int left = 0;
        int right = 0;
        for (int i = 1; i < string.length(); ++i) {
            z[i] = Math.max(Math.min(right - i, z[i - left]), 0);
            while (i + z[i] < string.length() && string.charAt(z[i]) == string.charAt(i + z[i])) {
                ++z[i];
            }
            if (i + z[i] > right) {
                left = i;
                right = i + z[i];
            }
        }
        return z;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        String string = reader.readLine();
        ZFunction zFunction = new ZFunction();
        int[] z = zFunction.execute(string);
        for (int i = 1; i < z.length; ++i) {
            writer.write(z[i] + " ");
        }
        writer.close();
    }
}
