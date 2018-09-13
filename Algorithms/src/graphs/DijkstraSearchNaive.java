package graphs;

import java.util.ArrayList;
import java.util.List;

public class DijkstraSearchNaive {

    // Class Graph
    private static class Edge {
        private int to;
        private int weight;

        public Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }

        public int getTo() {
            return to;
        }

        public int getWeight() {
            return weight;
        }
    }

    private static class Graph {

        private List<List<Edge>> adjacencyList;

        public Graph(int size) {
            adjacencyList = new ArrayList<>();
            for (int i = 0; i < size; ++i) {
                adjacencyList.add(new ArrayList<>());
            }
        }

        public void addEdge(int left, int right, int weight) {
            Edge edge = new Edge(right, weight);
            adjacencyList.get(left).add(edge);
        }

        public List<Edge> neighbours(int index) {
            return adjacencyList.get(index);
        }

        public int size() {
            return adjacencyList.size();
        }
    }

    // Dijkstra Search algorithm

    private Graph graph;
    private long[] distances;
    private int[] parents;
    private boolean[] used;

    public DijkstraSearchNaive(Graph graph) {
        this.graph = graph;
        distances = new long[graph.size()];
        used = new boolean[graph.size()];
        parents = new int[graph.size()];
        for (int i = 0; i < graph.size(); ++i) {
            distances[i] = Long.MAX_VALUE;
            parents[i] = Integer.MAX_VALUE;
            // used[] false for default
        }
    }

    public void execute(int start) {
        distances[start] = 0L;
        for (int i = 0; i < graph.size(); ++i) {
            int minNode = Integer.MIN_VALUE;
            // find node with minimal distance
            for (int j = 0; j < graph.size(); ++j) {
                if (!used[j] && (minNode == Integer.MIN_VALUE || distances[j] < distances[minNode])) {
                    minNode = j;
                }
            }
            if (distances[minNode] == Long.MAX_VALUE) {
                break;
            }
            used[minNode] = true;
            // relaxation for every edge
            for (Edge edge : graph.neighbours(minNode)) {
                long distance = distances[minNode] + edge.getWeight();
                if (distance < distances[edge.getTo()]) {
                    distances[edge.getTo()] = distance;
                    parents[edge.getTo()] = minNode;
                }
            }
        }
    }
}
