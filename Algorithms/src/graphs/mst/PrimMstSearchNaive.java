package graphs.mst;

import java.util.ArrayList;
import java.util.List;

public class PrimMstSearchNaive {

    // Class Graph
    private static class Graph {

        private int[][] adjacencyMatrix;

        public Graph(int size) {
            adjacencyMatrix = new int[size][size];
            for (int i = 0; i < size; ++i) {
                for (int j = 0; j < size; ++j) {
                    if (i == j) {
                        adjacencyMatrix[i][j] = 0;
                    } else {
                        adjacencyMatrix[i][j] = Integer.MAX_VALUE;
                    }
                }
            }
        }

        public void addEdge(int left, int right, int weight) {
            // for multi-edges
            if (weight < adjacencyMatrix[left][right]) {
                adjacencyMatrix[left][right] = weight;
            }
        }

        public int[] neighbours(int left) {
            return adjacencyMatrix[left];
        }

        public int getWeight(int left, int right) {
            return adjacencyMatrix[left][right];
        }

        public int size() {
            return adjacencyMatrix.length;
        }
    }

    // only for returning mst
    private static class Edge {
        private int from;
        private int to;
        private long weight;

        public Edge(int from, int to, long weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public int getFrom() {
            return from;
        }

        public int getTo() {
            return to;
        }

        public long getWeight() {
            return weight;
        }
    }

    private Graph graph;
    private boolean[] used;
    private int[] weights;
    private int[] parents;

    public PrimMstSearchNaive(Graph graph) {
        this.graph = graph;
        used = new boolean[graph.size()];
        weights = new int[graph.size()];
        parents = new int[graph.size()];
        for (int i = 0; i < graph.size(); ++i) {
            weights[i] = Integer.MAX_VALUE;
            parents[i] = Integer.MIN_VALUE;
        }
    }

    public List<Edge> execute() {
        List<Edge> ans = new ArrayList<>();
        weights[0] = 0;
        for (int from = 0; from < graph.size(); ++from) {
            int minNode = Integer.MAX_VALUE;
            for (int to = 0; to < graph.size(); ++to) {
                if (!used[to] && (minNode == Integer.MAX_VALUE || weights[to] < weights[minNode])) {
                    minNode = to;
                }
            }

            if (minNode == Integer.MAX_VALUE) {
                // mst doesn't exist
                return null;
            }
            used[minNode] = true;
            if (parents[minNode] != Integer.MIN_VALUE) {
                ans.add(new Edge(parents[minNode], minNode, weights[minNode]));
            }

            for (int to = 0; to < graph.size(); ++to) {
                int weight = graph.getWeight(minNode, to);
                if (weight < weights[to]) {
                    weights[to] = weight;
                    parents[to] = minNode;
                }
            }
        }
        return ans;
    }
}
