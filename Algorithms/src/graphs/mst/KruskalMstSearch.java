package graphs.mst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KruskalMstSearch {

    private class Edge implements Comparable<Edge> {
        private int from;
        private int to;
        private int weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            return weight - o.weight;
        }
    }

    private class DisjointSetUnion {
        private int[] parents;

        public DisjointSetUnion(int n) {
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

    private DisjointSetUnion dsu;
    private Edge[] edges;
    private int cost = 0;

    public KruskalMstSearch(int size, Edge[] eds) {
        edges = eds.clone();
        dsu = new DisjointSetUnion(size);
        Arrays.sort(edges);
    }

    public List<Edge> execute() {
        List<Edge> ans = new ArrayList<>();
        for (Edge edge : edges) {
            int from = edge.from;
            int to = edge.to;
            int weight = edge.weight;
            if (!dsu.equivalent(from, to)) {
                ++cost;
                ans.add(edge);
                dsu.unite(from, to);
            }
        }
        return ans;
    }
}
