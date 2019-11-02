package koshp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author m.popov
 */
public class ProblemH {

  private static class Similar {
    public int left;
    public int right;

    public Similar(int left, int right) {
      this.left = left;
      this.right = right;
    }
  }

  private static boolean isSimilar(String answer, String left, String right) {
    int size = answer.length();
    int correctLeft = 0;
    int correctRight = 0;
    int correctLeftRight = 0;
    int incorrectLeftRight = 0;
    for (int i = 0; i < size; ++i) {
      char answerChar = answer.charAt(i);
      char leftChar = left.charAt(i);
      char rightChar = right.charAt(i);
      if (answerChar == leftChar) {
        ++correctLeft;
      }
      if (answerChar == rightChar) {
        ++correctRight;
      }
      if (leftChar == rightChar) {
        if (answerChar == leftChar) {
          ++correctLeftRight;
        }
        else {
          ++incorrectLeftRight;
        }
      }
    }
    return 2 * correctLeftRight > correctLeft
           && 2 * correctLeftRight > correctRight
           && 2 * incorrectLeftRight > size - correctLeft
           && 2 * incorrectLeftRight > size - correctRight;
  }

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    int n = Integer.parseInt(reader.readLine());
    String answer = reader.readLine();
    int m = Integer.parseInt(reader.readLine());

    String[] children = new String[m];
    for (int i = 0; i < m; ++i) {
      children[i] = reader.readLine();
    }

    int count = 0;
    List<Similar> similars = new ArrayList<>();
    for (int i = 0; i < m; ++i) {
      for (int j = i + 1; j < m; ++j) {
        if (isSimilar(answer, children[i], children[j])) {
          ++count;
          similars.add(new Similar(i + 1, j + 1));
        }
      }
    }
    writer.write(count + "\n");
    for (int i = 0; i < similars.size(); ++i) {
      writer.write(similars.get(i).left + " " + similars.get(i).right + "\n");
    }

    writer.close();
  }
}
