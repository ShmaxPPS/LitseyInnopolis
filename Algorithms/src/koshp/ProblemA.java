package koshp;

import java.io.*;
import java.util.Arrays;

/**
 * @author m.popov
 */
public class ProblemA {

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    long[] mass = new long[3];
    mass[0] = Long.parseLong(reader.readLine());
    mass[1] = Long.parseLong(reader.readLine());
    mass[2] = Long.parseLong(reader.readLine());
    Arrays.sort(mass);

    long bDiff = mass[2] - mass[0];
    long sDiff = mass[2] - mass[1];
    writer.write(String.valueOf(2 * bDiff - sDiff));
    writer.close();
  }
}
