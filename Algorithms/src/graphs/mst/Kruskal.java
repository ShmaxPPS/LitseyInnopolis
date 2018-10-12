import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Kruskal {
    private class Edge implements Comparable<Edge> {
        private int from, to, w;

        public Edge(int v, int u, int we) {
            from = v;
            to = u;
            w = we;
        }

        @Override
        public int compareTo(Edge o) {
            return this.w - o.w;
        }
    }

    private class DSU {
        private int[] parents;

        public DSU(int n) {
            this.parents = new int[n];
            for (int i = 0; i < n; ++i) {
                this.parents[i] = i;
            }
        }

        private int root(int xy) {
            int root = xy;
            while (this.parents[root] != root) {
                root = this.parents[root];
            }
            int i = xy;
            while (this.parents[i] != i) {
                int j = this.parents[i];
                this.parents[i] = root;
                i = j;
            }
            return root;
        }

        public void unite(int x, int y) {
            x = root(x);
            y = root(y);
            if (x == y) {
                return;
            }
            if (x < y) {
                this.parents[x] = y;
            } else {
                this.parents[y] = x;
            }
        }

        public boolean equivalent(int x, int y) {
            return root(x) == root(y);
        }

    }

    private DSU dsu;
    private Edge[] edges;
    private int cost = 0;

    public Kruskal(int size, Edge[] eds) {
        edges = eds.clone();
        dsu = new DSU(size);
        Arrays.sort(edges);
    }

    public List<Edge> KruskalAlgorithm() {
        List<Edge> ans = new ArrayList<>();
        for (Edge edge : edges) {
            int v = edge.from, u = edge.to, w = edge.w;
            if (!dsu.equivalent(v, u)) {
                ++cost;
                ans.add(edge);
                dsu.unite(v, u);
            }
        }
        return ans;
    }
}
