package koshp;

import java.io.*;

/**
 * @author m.popov
 */
public class ProblemF {

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    String[] parts = reader.readLine().split(" ");
    long x = Long.parseLong(parts[0]);
    long y = Long.parseLong(parts[1]);
    long z = Long.parseLong(parts[2]);

    parts = reader.readLine().split(" ");
    long a = Long.parseLong(parts[0]);
    long b = Long.parseLong(parts[1]);
    long c = Long.parseLong(parts[2]);

    long[] cr = {a, b, c};

    long[] t = new long[3];
    parts = reader.readLine().split(" ");
    t[0] = Long.parseLong(parts[0]);
    t[1] = Long.parseLong(parts[1]);
    t[2] = Long.parseLong(parts[2]);
    long d = Long.parseLong(parts[3]);

    parts = reader.readLine().split(" ");
    int k = Integer.parseInt(parts[0]) - 1;
    int i = Integer.parseInt(parts[1]);
    int l = Integer.parseInt(parts[2]) - 1;
    int j = Integer.parseInt(parts[3]);

    long dist = Long.MAX_VALUE;
    if (k == l) {
      if ((cr[k] > i && j > cr[l] + 1) || (i > cr[k] + 1 && cr[l] > j)) {
        dist = t[k] * Math.abs(i - j);
        long diff = 2 * t[k] - (t[0] + t[1] + t[2] + 3 * d);
        if (diff > 0) {
          dist -= diff;
        }
      }
      else {
        dist = t[k] * Math.abs(i - j);
      }
    }
    else {
      if (i < cr[k] && j > cr[l] + 1 && ((k == 0 && l == 1) || (k == 1 && l == 2) || (k == 2 && l == 0))) {
        dist = t[k] * (cr[k] - i) + t[l] * (j - (cr[l] + 1)) + d;
      }
      else if (i > cr[k] + 1 && j < cr[l] && ((k == 1 && l == 0) || (k == 2 && l == 1) || (k == 0 && l == 2))) {
        dist = t[k] * (i - (cr[k] + 1)) + t[l] * (cr[l] - j) + d;
      }
      else if (i < cr[k] && j > cr[l] + 1 && ((k == 0 && l == 2) || (k == 1 && l == 0) || (k == 2 && l == 1))) {
        dist = t[k] * (cr[k] + 1 - i) + d + t[l] * (j - cr[l]);
        long diff = t[0] + t[1] + t[2] - (2 * d + t[3 - k - l]);
        if (diff > 0) {
          dist -= diff;
        }
      }
      else if (i > cr[k] + 1 && j < cr[l] && ((k == 2 && l == 0) || (k == 0 && l == 1) || (k == 1 && l == 2))) {
        dist = t[k] * (i - cr[k]) + d + t[l] * (cr[l] + 1 - j);
        long diff = t[0] + t[1] + t[2] - (2 * d + t[3 - k - l]);
        if (diff > 0) {
          dist -= diff;
        }
      }
      else if (i < cr[k] && j < cr[l] && ((k == 0 && l == 2) || (k == 1 && l == 0) || (k == 2 && l == 1))) {
        dist = t[k] * (cr[k] + 1 - i) + d + t[l] * (cr[l] - j);
        long diff = 2 * t[k] - (t[0] + t[1] + t[2] + d);
        if (diff > 0) {
          dist -= diff;
        }
      }
      else if (i < cr[k] && j < cr[l] && ((k == 2 && l == 0) || (k == 0 && l == 1) || (k == 1 && l == 2))) {
        dist = t[k] * (cr[k] - i) + d + t[l] * (cr[l] + 1 - j);
        long diff = 2 * t[l] - (t[0] + t[1] + t[2] + d);
        if (diff > 0) {
          dist -= diff;
        }
      }
      else if (i > cr[k] + 1 && j > cr[l] + 1 && ((k == 0 && l == 1) || (k == 1 && l == 2) || (k == 2 && l == 0))) {
        dist = t[k] * (i - (cr[k] + 1)) + d + t[l] * (j - cr[l]);
        long diff = 2 * t[k] - (t[0] + t[1] + t[2] + d);
        if (diff > 0) {
          dist -= diff;
        }
      }
      else if (i > cr[k] + 1 && j > cr[l] + 1 && ((k == 1 && l == 0) || (k == 2 && l == 1) || (k == 0 && l == 2))) {
        dist = t[k] * (i - cr[k]) + d + t[l] * (j - (cr[l] + 1));
        long diff = 2 * t[l] - (t[0] + t[1] + t[2] + d);
        if (diff > 0) {
          dist -= diff;
        }
      }
    }
    writer.write("" + dist);
    writer.close();
  }
}
