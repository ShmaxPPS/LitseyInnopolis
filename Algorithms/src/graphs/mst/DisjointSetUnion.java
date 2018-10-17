package graphs.mst;

public class DisjointSetUnion {

    private int[] parents;
    private int[] ranks;

    public DisjointSetUnion(int size) {
        parents = new int[size];
        ranks = new int[size];
        for (int i = 0; i < size; ++i) {
            parents[i] = i;
        }
    }

    // recursive version
    /*
    private int root(int x) {
        if (parents[x] != x) {
            // path reduction heuristic
            parents[x] = root(parents[x]);
        }
        return parents[x];
    }
    */

    // non recursive version
    private int root(int x) {
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

    public void unite(int x, int y) {
        x = root(x);
        y = root(y);
        if (x == y) {
            // in one set yet
            return;
        }
        // rank heuristic
        if (ranks[x] == ranks[y]) {
            ranks[y]++;
        }
        if (ranks[x] < ranks[y]) {
            parents[x] = y;
        } else {
            parents[y] = x;
        }
    }

    public boolean equivalent(int x, int y) {
        return root(x) == root(y);
    }
}
