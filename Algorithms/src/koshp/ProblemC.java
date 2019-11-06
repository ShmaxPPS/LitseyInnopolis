package koshp;

import java.io.*;
import java.util.*;

/**
 * @author m.popov
 */
public class ProblemC {

  private static int[] parents;
  private static LinkedList<Integer>[] components;
  private static Map<Integer, Set<Integer>> edges;
  private static int[] answer;

  // non recursive version
  private static int root(int x) {
    int root = x;
    // find root
    while (parents[root] != root) {
      root = parents[root];
    }
    // path reduction heuristic
    int i = x;
    while (parents[i] != i) {
      int j = parents[i];
      parents[i] = root;
      i = j;
    }
    return root;
  }

  public static void unite(int x, int y) {
    int rootX = root(x);
    int rootY = root(y);
    if (rootX == rootY) {
      // in one set yet
      return;
    }
    if (components[rootX].size() < components[rootY].size()) {
      int t = rootY;
      rootY = rootX;
      rootX = t;
    }
    while (!components[rootY].isEmpty()) {
      int v = components[rootY].pollFirst();
      parents[v] = rootX;
      components[rootX].add(v);
    }
  }

  public static boolean equivalent(int x, int y) {
    return root(x) == root(y);
  }


  private static void addRoad(int l, int r) {
    if (equivalent(l, r)) {
      return;
    }
    int rootL = root(l);
    int rootR = root(r);
    int maxIndex = components[rootL].size() < components[rootR].size()
        ? rootR : rootL;
    List<Integer> minComponent = components[rootL].size() < components[rootR].size()
        ? components[rootL] : components[rootR];
    for (int v : minComponent) {
      for (int u : edges.get(v)) {
        if (equivalent(u, maxIndex)) {
          ++answer[v];
          ++answer[u];
        }
      }
    }
    unite(rootL, rootR);
  }

  private static void addFriendship(int l, int r) {
    edges.get(l).add(r);
    edges.get(r).add(l);
    if (equivalent(l, r)) {
      ++answer[l];
      ++answer[r];
    }
  }

  public static void main(String[] args) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    String[] parts = reader.readLine().split(" ");
    int n = Integer.parseInt(parts[0]);
    int m = Integer.parseInt(parts[1]);
    int k = Integer.parseInt(parts[2]);

    parents = new int[n];
    components = new LinkedList[n];
    edges = new HashMap<>();
    answer = new int[n];
    for (int i = 0; i < n; ++i) {
      parents[i] = i;
      components[i] = new LinkedList<>();
      components[i].add(i);
      edges.put(i, new HashSet<>());
    }

    for (int i = 0; i < m; ++i) {
      parts = reader.readLine().split(" ");
      int l = Integer.parseInt(parts[0]) - 1;
      int r = Integer.parseInt(parts[1]) - 1;
      addFriendship(l, r);
    }

    for (int i = 0; i < k; ++i) {
      parts = reader.readLine().split(" ");
      int l = Integer.parseInt(parts[0]) - 1;
      int r = Integer.parseInt(parts[1]) - 1;
      addRoad(l, r);
    }

    int q = Integer.parseInt(reader.readLine());
    for (int i = 0; i < q; ++i) {
      parts = reader.readLine().split(" ");
      if (parts.length == 2) {
        int v = Integer.parseInt(parts[1]) - 1;
        writer.write(answer[v] + "\n");
      }
      else {
        char t = parts[0].charAt(0);
        int l = Integer.parseInt(parts[1]) - 1;
        int r = Integer.parseInt(parts[2]) - 1;
        if (t == 'T') {
          addRoad(l ,r);
        }
        else {
          addFriendship(l, r);
        }
      }
    }
    writer.close();
  }
}
