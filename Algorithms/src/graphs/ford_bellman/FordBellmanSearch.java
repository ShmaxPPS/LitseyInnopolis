package graphs.ford_bellman;

import java.util.ArrayList;
import java.util.Collections;
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
        distances = new long[graph.size()];
        parents = new int[graph.size()];
        for (int i = 0; i < graph.size(); ++i) {
            this.graph = graph;
            distances[i] = Long.MAX_VALUE / 2; // not Long.MAX_VALUE, for searching unreachable negative cycle
            parents[i] = Integer.MIN_VALUE;
        }
    }

    public List<Integer> execute(int start) {
        distances[start] = 0;
        for (int i = 0; i < graph.size() - 1; ++i) {
            for (Edge edge : graph.getEdges()) {
                if (distances[edge.getTo()] > distances[edge.getFrom()] + edge.getWeight()) {
                    distances[edge.getTo()] = distances[edge.getFrom()] + edge.getWeight();
                    parents[edge.getTo()] = edge.getFrom();
                }
            }
        }
        for (Edge edge : graph.getEdges()) {
            if (distances[edge.getTo()] > distances[edge.getFrom()] + edge.getWeight()) {
                parents[edge.getTo()] = edge.getFrom(); // for one node graph
                // have negative cycle
                int node = edge.getTo();
                for (int i = 0; i < graph.size - 1; ++i) {
                    node = parents[node];
                }
                List<Integer> cycle = new ArrayList<>();
                cycle.add(node);
                int cur = parents[node];
                while (cur != node) {
                    cycle.add(cur);
                    cur = parents[cur];
                }
                Collections.reverse(cycle);
                return cycle;
            }
        }
        // doesn't have negative cycle
        return null;
    }

    public long getDistance(int node) {
        return distances[node];
    }
}
