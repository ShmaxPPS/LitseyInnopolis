package koshp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author m.popov
 */
public class ProblemG {

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    String[] parts = reader.readLine().split(" ");
    int n = Integer.parseInt(parts[0]);
    int m = Integer.parseInt(parts[1]);
    int[] a = new int[n];
    int[] sum = new int[1 << n];
    int[] index = new int[m];
    parts = reader.readLine().split(" ");
    int l = -1;
    int r = -1;
    for (int i = 0; i < n; ++i) {
      a[i] = Integer.parseInt(parts[i]) % m;
      sum[1 << i] = a[i];
      index[a[i]] = 1 << i;
      if (a[i] == 0) {
        l = 0;
        r = 1 << i;
      }
    }
    for (int i = 1; i < 1 << n; ++i) {
      int prev = i & (i - 1);
      if (prev == 0) {
        continue;
      }
      sum[i] = (sum[prev] + sum[i ^ prev]) % m;
      if (sum[i] == 0) {
        l = 0;
        r = i;
        break;
      }
      else if (index[sum[i]] != 0) {
        l = index[sum[i]];
        r = i;
        break;
      }
      index[sum[i]] = i;
    }

    if (l == -1 && r == -1) {
      writer.write("" + -1);
    }
    else {
      List<Integer> lIndexes = new ArrayList<>();
      List<Integer> rIndexes = new ArrayList<>();
      for (int i = 0; i < n; ++i) {
        boolean lExist = (l & (1 << i)) != 0;
        boolean rExist = (r & (1 << i)) != 0;
        if (lExist && !rExist) {
          lIndexes.add(i + 1);
        }
        if (!lExist && rExist) {
          rIndexes.add(i + 1);
        }
      }
      writer.write(lIndexes.size() + "\n");
      for (int i = 0; i < lIndexes.size(); ++i) {
        writer.write(lIndexes.get(i) + " ");
      }
      writer.write("\n" + rIndexes.size() + "\n");
      for (int i = 0; i < rIndexes.size(); ++i) {
        writer.write(rIndexes.get(i) + " ");
      }
    }
    writer.close();
  }
}
