package koshp;

import java.io.*;
import java.util.*;

/**
 * @author m.popov
 */
public class ProblemI {

  private static class Node {
    private int first;
    private int second;
    private int third;

    public Node(int first, int second, int third) {
      this.first = first;
      this.second = second;
      this.third = third;
    }

    public int getFirst() {
      return first;
    }

    public int getSecond() {
      return second;
    }

    public int getThird() {
      return third;
    }
  }

  private static int getEqual(int prev, int pos, Node left, Node right) {
    if (right.getFirst() != prev && right.getFirst() != pos && (right.getFirst() == left.getFirst()
                                                                || right.getFirst() == left.getSecond()
                                                                || right.getFirst() == left.getThird())) {
      return right.getFirst();
    }
    if (right.getSecond() != prev && right.getSecond() != pos && (right.getSecond() == left.getFirst()
                                                                  || right.getSecond() == left.getSecond()
                                                                  || right.getSecond() == left.getThird())) {
      return right.getSecond();
    }
    if (right.getThird() != prev && right.getThird() != pos && (right.getThird() == left.getFirst()
                                                                || right.getThird() == left.getSecond()
                                                                || right.getThird() == left.getThird())) {
      return right.getThird();
    }
    return Integer.MIN_VALUE;
  }

  private static int getNext(int prev, int pos, List<List<Node>> mapping) {
    Node nodeFirst = mapping.get(pos - 1).get(0);
    Node nodeSecond = mapping.get(pos - 1).get(1);
    Node nodeThird = mapping.get(pos - 1).get(2);
    int next = getEqual(prev, pos, nodeFirst, nodeSecond);
    if (next != Integer.MIN_VALUE && next != prev) {
      return next;
    }
    else {
      next = getEqual(prev, pos, nodeSecond, nodeThird);
      if (next != Integer.MIN_VALUE && next != prev) {
        return next;
      }
      else {
        return getEqual(prev, pos, nodeThird, nodeFirst);
      }
    }
  }

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    int n = Integer.parseInt(reader.readLine());
    List<List<Node>> mapping = new ArrayList<>();
    for (int i = 0; i < n; ++i) {
      mapping.add(new ArrayList<>());
    }
    for (int i = 0; i < n; ++i) {
      String[] parts = reader.readLine().split(" ");
      int first = Integer.parseInt(parts[0]);
      int second = Integer.parseInt(parts[1]);
      int third = Integer.parseInt(parts[2]);

      Node node = new Node(first, second, third);
      mapping.get(first - 1).add(node);
      mapping.get(second - 1).add(node);
      mapping.get(third - 1).add(node);
    }

    if (n == 3) {
      writer.write("1 2 3");
    }
    else if (n == 4) {
      writer.write("1 2 3 4");
    }
    else {
      int prev = Integer.MIN_VALUE;
      int pos = 1;
      writer.write(pos + " ");
      for (int i = 0; i < n - 1; ++i) {
        int temp = pos;
        pos = getNext(prev, pos, mapping);
        prev = temp;
        writer.write(pos + " ");
      }
    }
    writer.close();
  }
}
