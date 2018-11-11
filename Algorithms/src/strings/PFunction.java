package strings;

import java.io.*;

public class PFunction {

    int[] execute(String string) {
        int[] p = new int[string.length()];
        for (int i = 1; i < string.length(); ++i) {
            int k = p[i - 1];
            while (k > 0 && string.charAt(i) != string.charAt(k)) {
                k = p[k - 1];
            }
            if (string.charAt(i) == string.charAt(k)) {
                ++k;
            }
            p[i] = k;
        }
        return p;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        String string = reader.readLine();
        PFunction pFunction = new PFunction();
        int[] p = pFunction.execute(string);
        for (int i = 0; i < p.length; ++i) {
            writer.write(p[i] + " ");
        }
        writer.close();
    }
}
