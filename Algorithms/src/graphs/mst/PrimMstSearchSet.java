package graphs.mst;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class PrimMstSearchSet {

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

    private static class NodeComparator implements Comparator<Integer> {

        private int[] weights;

        public NodeComparator(int[] weights) {
            this.weights = weights;
        }

        @Override
        public int compare(Integer left, Integer right) {
            int difference = weights[left] - weights[right];
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
            Edge edge = new Edge(left, right, weight);
            adjacencyList.get(left).add(edge);
        }

        public List<Edge> neighbours(int index) {
            return adjacencyList.get(index);
        }

        public int size() {
            return adjacencyList.size();
        }
    }

    private Graph graph;
    private int[] weights;
    private int[] parents;

    public PrimMstSearchSet(Graph graph) {
        this.graph = graph;
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
        TreeSet<Integer> set = new TreeSet<>(new NodeComparator(weights));
        set.add(0);
        while (!set.isEmpty()) {
            int minNode = set.pollFirst();
            if (weights[minNode] == Integer.MAX_VALUE) {
                break;
            }
            if (parents[minNode] != Integer.MIN_VALUE) {
                ans.add(new Edge(parents[minNode], minNode, weights[minNode]));
            }
            for (Edge edge : graph.neighbours(minNode)) {
                int weight = edge.getWeight();
                if (weight < weights[edge.getTo()] && (set.remove(edge.getTo()) || weights[edge.getTo()] == Integer.MAX_VALUE)) {
                    weights[edge.getTo()] = weight;
                    parents[edge.getTo()] = minNode;
                    set.add(edge.getTo());
                }
            }
        }
        return ans;
    }
}
