package graphs.ford_bellman;

import java.util.ArrayList;
import java.util.List;

public class FordBellmanSearch {

    // Class Graph
    private static class Edge {
        private int from;
        private int to;
        private int weight;

        public Edge(int from, int to, int weight) {
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

        public int getWeight() {
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

    private Graph graph;
    private long[] distances;
    private int[] parents;

    public FordBellmanSearch(Graph graph) {
        for (int i = 0; i < graph.size(); ++i) {
            this.graph = graph;
            distances[i] = Long.MAX_VALUE;
            parents[i] = Integer.MIN_VALUE;
        }
    }

    public void execute(int start) {
        distances[start] = 0;
        for (int i = 0; i < graph.size() - 1; ++i) {
            for (Edge edge : graph.getEdges()) {
                if (distances[edge.getFrom()] == Long.MAX_VALUE) {
                    continue;
                }
                if (distances[edge.getTo()] > distances[edge.getFrom()] + edge.getWeight()) {
                    distances[edge.getTo()] = distances[edge.getFrom()] + edge.getWeight();
                    parents[edge.getTo()] = edge.getFrom();
                }
            }
        }
        for (Edge edge : graph.getEdges()) {
            if (distances[edge.getTo()] > distances[edge.getFrom()] + edge.getWeight()) {
                // have negative cycle
                return;
            }
        }
        // doesn't have negative cycle
    }
}
