import java.io.PrintWriter;
import java.util.*;

public class JohnsonShortestPaths {
    private static class Graph {

        private int size;
        private List<List<Edge>> graph;
        private List<Edge> edges;

        public Graph(int size) {
            this.size = size;
            graph = new ArrayList<>();
            edges = new ArrayList<>();
            for (int i = 0; i < size; ++i) {
                graph.add(new ArrayList<>());
            }
        }

        public void addEdge(int left, int right, long weight) {
            Edge edge = new Edge(left, right, weight);
            graph.get(left).add(edge);
            edges.add(edge);
        }

        public List<Edge> neighbours(int index) {
            return graph.get(index);
        }

        public List<Edge> getEdges() {
            return edges;
        }

        public int size() {
            return size;
        }
    }


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


    public static class FordBellman {

        // Class Graph

        private Graph graph;
        private long[] distances;
        private int[] parents;

        public FordBellman(Graph graph) {
            distances = new long[graph.size()];
            parents = new int[graph.size()];
            for (int i = 0; i < graph.size(); ++i) {
                this.graph = graph;
                distances[i] = Long.MAX_VALUE / 2; // not Long.MAX_VALUE, for searching unreachable negative cycle
                parents[i] = Integer.MIN_VALUE;
            }
        }

        public boolean execute() {
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
                    return true;
                }
            }
            // doesn't have negative cycle
            return false;
        }

        public long getDistance(int node) {
            return distances[node];
        }
    }


    public static class DijkstraSearchSet {

        // Class Graph

        private static class NodeComparator implements Comparator<Integer> {

            private long[] distances;

            public NodeComparator(long[] distances) {
                this.distances = distances;
            }

            @Override
            public int compare(Integer left, Integer right) {
                long difference = distances[left] - distances[right];
                if (difference > 0) {
                    return 1;
                } else if (difference < 0) {
                    return -1;
                } else {
                    return left - right;
                }
            }
        }

        // Dijkstra Search algorithm

        private Graph graph;
        private long[] distances;
        private int[] parents;

        public DijkstraSearchSet(Graph graph) {
            this.graph = graph;
            distances = new long[graph.size()];
            parents = new int[graph.size()];
            for (int i = 0; i < graph.size(); ++i) {
                distances[i] = Long.MAX_VALUE;
                parents[i] = Integer.MAX_VALUE;
            }
        }

        public List<Long> execute(int start) {
            distances[start] = 0L;
            TreeSet<Integer> set = new TreeSet<>(new NodeComparator(distances));
            set.add(start);
            while (!set.isEmpty()) {
                int minNode = set.pollFirst();
                if (distances[minNode] == Long.MAX_VALUE) {
                    break;
                }
                for (Edge edge : graph.neighbours(minNode)) {
                    long distance = distances[minNode] + edge.getWeight();
                    if (distance < distances[edge.getTo()]) {
                        set.remove(edge.getTo());
                        distances[edge.getTo()] = distance;
                        parents[edge.getTo()] = minNode;
                        set.add(edge.getTo());
                    }
                }
            }
            List<Long> ans = new ArrayList<>();
            for (int v = 0; v < distances.length; ++v) {
                ans.add(distances[v]);
            }
            return ans;
        }
    }

    private Graph graph;
    private Graph graphShtrih;
    private Graph graphPhi;
    private FordBellman fordBellman;
    private DijkstraSearchSet dijkstraSearchSet;
    private boolean negativeCycle;

    public JohnsonShortestPaths(Graph graph) {
        this.graph = graph;
        graphShtrih = new Graph(graph.size + 1);
        graphPhi = new Graph(graph.size);
        for (int v = 1; v <= graph.size; ++v) {
            graphShtrih.addEdge(0, v, 0);
        }
        for (Edge edge : graph.getEdges()) {
            graphShtrih.addEdge(edge.getFrom() + 1, edge.getTo() + 1, edge.getWeight());
        }
        fordBellman = new FordBellman(graphShtrih);
        negativeCycle = fordBellman.execute();
        for (Edge edge : graph.getEdges()) {
            long wi = edge.weight + fordBellman.getDistance(edge.getFrom() + 1) - fordBellman.getDistance(edge.getTo() + 1);
            graphPhi.addEdge(edge.getFrom(), edge.getTo(), wi);
        }
    }

    public List<List<Long>> execute() {
        if (negativeCycle) return null;
        List<List<Long>> ans = new ArrayList<>();
        for (int v = 0; v < graphPhi.size; ++v) {
            dijkstraSearchSet = new DijkstraSearchSet(graphPhi);
            ans.add(dijkstraSearchSet.execute(v));
            for (int u = 0; u < graphPhi.size; ++u) {
                ans.get(v).set(u, ans.get(v).get(u) + fordBellman.getDistance(u + 1) - fordBellman.getDistance(v + 1));
            }
        }
        return ans;
    }
}
