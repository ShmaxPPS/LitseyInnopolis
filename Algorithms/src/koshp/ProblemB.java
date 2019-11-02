package koshp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author m.popov
 */
public class ProblemB {

  private static class Operation {
    private long count;
    private int from;
    private int to;

    public Operation(long count, int from, int to) {
      this.count = count;
      this.from = from;
      this.to = to;
    }

    public long getCount() {
      return count;
    }

    public int getFrom() {
      return from;
    }

    public int getTo() {
      return to;
    }
  }

  private static long getOperationsInCount(List<Operation> operations, int day) {
    long count = 0;
    for (Operation operation : operations) {
      if (operation.getFrom() <= day && operation.getTo() >= day) {
        count += operation.getCount();
      }
    }
    return count;
  }

  private static long getOperationsBeforeCount(List<Operation> operations, int day) {
    long count = 0;
    for (Operation operation : operations) {
      if (operation.getTo() < day) {
        count += operation.getCount();
      }
    }
    return count;
  }

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    String[] parts = reader.readLine().split(" ");
    int n = Integer.parseInt(parts[0]);
    int m = Integer.parseInt(parts[1]);
    long s = Long.parseLong(parts[2]);

    List<Operation> posOperations = new ArrayList<>();
    List<Operation> negOperations = new ArrayList<>();
    for (int i = 0; i < n; ++i) {
      parts = reader.readLine().split(" ");
      Operation operation = new Operation(
          Long.parseLong(parts[0]),
          Integer.parseInt(parts[1]) - 1,
          Integer.parseInt(parts[2]) - 1
      );
      if (operation.getCount() < 0) {
        negOperations.add(operation);
      }
      else {
        posOperations.add(operation);
      }
    }
    boolean isOverflow = false;
    for (int i = 0; i < m; ++i) {
      long posBeforeCount = getOperationsBeforeCount(posOperations, i);
      long negBeforeCount = getOperationsBeforeCount(negOperations, i);
      long negInCount = getOperationsInCount(negOperations, i);
      if (s + posBeforeCount + negBeforeCount + negInCount < 0) {
        isOverflow = true;
        break;
      }
    }

    if (isOverflow) {
      writer.write("YES");
    }
    else {
      writer.write("NO");
    }

    writer.close();
  }
}
