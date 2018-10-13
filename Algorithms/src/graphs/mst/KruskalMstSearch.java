package graphs.mst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KruskalMstSearch {

    private static class Edge implements Comparable<Edge> {
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

    private static class Graph {
        private int size;
        private List<Edge> edges;

        public Graph(int size) {
            this.size = size;
            edges = new ArrayList<>();
        }

        public void addEdge(int left, int right, int weight) {
            Edge edge = new Edge(left, right, weight);
            edges.add(edge);
        }

        public List<Edge> getEdges() {
            return edges;
        }

        public int size() {
            return size;
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
    private List<Edge> edges;

    public KruskalMstSearch(Graph graph) {
        dsu = new DisjointSetUnion(graph.size());
        edges = new ArrayList<>(graph.getEdges());
        Collections.sort(edges);
    }

    public List<Edge> execute() {
        List<Edge> ans = new ArrayList<>();
        for (Edge edge : edges) {
            if (!dsu.equivalent(edge.from, edge.to)) {
                ans.add(edge);
                dsu.unite(edge.from, edge.to);
            }
        }
        return ans;
    }
}
