package graphs.dijkstra;

import java.util.*;

public class DijkstraSearchQueue {

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

    private static class NodeComparator implements Comparator<Integer> {

        private long[] distances;

        public NodeComparator(long[] distances) {
            this.distances = distances;
        }

        @Override
        public int compare(Integer left, Integer right) {
            long difference = distances[left] - distances[right];
            if (difference > 0){
                return 1;
            } else if (difference < 0) {
                return -1;
            } else {
                return left - right;
            }
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

    public DijkstraSearchQueue(Graph graph) {
        this.graph = graph;
        distances = new long[graph.size()];
        parents = new int[graph.size()];
        for (int i = 0; i < graph.size(); ++i) {
            distances[i] = Long.MAX_VALUE;
            parents[i] = Integer.MAX_VALUE;
        }
    }

    public void execute(int start) {
        distances[start] = 0L;
        PriorityQueue<Integer> queue = new PriorityQueue<>(new NodeComparator(distances));
        queue.add(start);
        while (!queue.isEmpty()) {
            int minNode = queue.poll();
            if (distances[minNode] == Integer.MAX_VALUE) {
                break;
            }
            for (Edge edge : graph.neighbours(minNode)) {
                long distance = distances[minNode] + edge.getWeight();
                if (distance < distances[edge.getTo()]) {
                    queue.remove(edge.getTo());
                    distances[edge.getTo()] = distance;
                    parents[edge.getTo()] = minNode;
                    queue.add(edge.getTo());
                }
            }
        }
    }
}
