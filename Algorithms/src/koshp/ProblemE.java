package koshp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author m.popov
 */
public class ProblemE {

  private static int nonZeros(List<Integer> a) {
    int count = 0;
    for (Integer num : a) {
      if (num != 0) {
        ++count;
      }
    }
    return count;
  }

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    int n = Integer.parseInt(reader.readLine());
    List<Integer> a = new ArrayList<>();
    String[] parts = reader.readLine().split(" ");
    for (int i = 0; i < n; ++i) {
      a.add(Integer.parseInt(parts[i]));
    }

    long m = (1 << n) - 1;
    List<Integer> x = new ArrayList<>();
    int nonZeros = nonZeros(a);
    int zeros = n - nonZeros;
    boolean hasZeros = zeros > 0;
    int zeroPow = 0;
    int pow = n - 1;
    for (int i = 0; i < a.size(); ++i) {
      int cur = a.get(i);
      if (cur == 1) {
        if (hasZeros) {
          x.add((1 << pow) + (1 << zeros) - 1);
          hasZeros = false;
        }
        else {
          x.add(1 << pow);
        }
        --pow;
      }
      if (cur == -1) {
        if (hasZeros) {
          x.add(((1 << pow) + (1 << zeros) - 1) * -1);
          hasZeros = false;
        }
        else {
          x.add((1 << pow) * -1);
        }
        --pow;
      }
      if (cur == 0) {
        x.add(1 << zeroPow);
        ++zeroPow;
      }
    }

    writer.write(m + "\n");
    for (int i = 0; i < x.size(); ++i) {
      writer.write(x.get(i) + " ");
    }

    writer.close();
  }
}
