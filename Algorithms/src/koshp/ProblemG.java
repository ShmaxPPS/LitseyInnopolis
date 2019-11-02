package koshp;

import java.io.*;

/**
 * @author m.popov
 */
public class ProblemG {

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    int n = 10;
    long m = (2 << n - 1) - 1;
    System.out.println(m);
    writer.close();
  }
}
