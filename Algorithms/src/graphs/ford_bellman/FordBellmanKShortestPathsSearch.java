package graphs.ford_bellman;

import java.util.ArrayList;
import java.util.List;

public class FordBellmanKShortestPathsSearch {
    // Class Graph
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

    private static class Graph {

        private int size;
        private List<Edge> edges;

        public Graph(int size) {
            this.size = size;
            edges = new ArrayList<>();
        }

        public void addEdge(int left, int right, long weight) {
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

    private Graph graph;
    private long[] distances;
    private long[] distancesNext;
    private int[] parents;

    public FordBellmanKShortestPathsSearch(Graph graph) {
        this.graph = graph;
        distances = new long[graph.size()];
        distancesNext = new long[graph.size()];
        parents = new int[graph.size()];
        for (int i = 0; i < graph.size(); ++i) {
            distances[i] = Long.MAX_VALUE;
            distancesNext[i] = Long.MAX_VALUE;
            parents[i] = Integer.MIN_VALUE;
        }
    }

    public void execute(int start, int k) {
        distances[start] = 0;
        for (int i = 0; i < k; ++i) {
            for (Edge edge : graph.getEdges()) {
                if (distances[edge.getFrom()] == Long.MAX_VALUE) {
                    continue;
                }
                if (distancesNext[edge.getTo()] > distances[edge.getFrom()] + edge.getWeight()) {
                    distancesNext[edge.getTo()] = distances[edge.getFrom()] + edge.getWeight();
                    parents[edge.getTo()] = edge.getFrom();
                }
            }
            for (int j = 0;  j < graph.size(); ++j) {
                distances[j] = distancesNext[j];
                distancesNext[j] = Long.MAX_VALUE;
            }
        }
    }

    public long getDistance(int node) {
        return distances[node];
    }
}
