package graphs.dfs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CutPointSearch {

    private static class Edge {
        private int from;
        private int to;

        public Edge(int from, int to) {
            this.from = from;
            this.to = to;
        }

        public int getFrom() {
            return from;
        }

        public int getTo() {
            return to;
        }
    }

    /* Class Graph */

    private static class Graph {
        private List<List<Edge>> adjacencyList;

        public Graph(int size) {
            adjacencyList = new ArrayList<>();
            for (int i = 0; i < size; ++i) {
                adjacencyList.add(new ArrayList<>());
            }
        }

        // not oriented graph
        public void addEdge(int left, int right) {
            adjacencyList.get(left).add(new Edge(left, right));
            adjacencyList.get(right).add(new Edge(right, left));
        }

        public List<Edge> neighbours(int index) {
            return adjacencyList.get(index);
        }

        public int size() {
            return adjacencyList.size();
        }
    }

    /* Depth First Search algorithm */

    private Graph graph;
    private Color[] isUsed;
    private int[] inTime;
    private int[] fupTime;
    private int time;

    enum Color {
        WHITE, GREY, BLACK
    }

    public CutPointSearch(Graph graph) {
        this.graph = graph;
        time = 0;
        isUsed = new Color[graph.size()];
        inTime = new int[graph.size()];
        fupTime = new int[graph.size()];
        for (int i = 0; i < graph.size(); ++i) {
            isUsed[i] = Color.WHITE;
            inTime[i] = Integer.MAX_VALUE;
            fupTime[i] = Integer.MAX_VALUE;
        }
    }

    public Set<Integer> execute() {
        Set<Integer> cutPoints = new HashSet<>();
        for (int i = 0; i < graph.size(); ++i) {
            if (isUsed[i] == Color.WHITE) {
                cutPoints.addAll(execute(new Edge(Integer.MIN_VALUE, i)));
            }
        }
        return cutPoints;
    }

    public Set<Integer> execute(Edge edge) {
        Set<Integer> cutPoints = new HashSet<>();
        inTime[edge.getTo()] = time;
        fupTime[edge.getTo()] = time;
        time++;
        isUsed[edge.getTo()] = Color.GREY;
        int children = 0;
        for (Edge next : graph.neighbours(edge.getTo())) {
            if (next.getTo() == edge.getFrom()) {
                continue;
            }
            if (isUsed[next.getTo()] == Color.GREY) {
                fupTime[edge.getTo()] =  Math.min(fupTime[edge.getTo()], inTime[next.getTo()]);
            }
            else if (isUsed[next.getTo()] == Color.WHITE) {
                cutPoints.addAll(execute(next));
                fupTime[edge.getTo()] = Math.min(fupTime[edge.getTo()], fupTime[next.getTo()]);
                if (fupTime[next.getTo()] >= inTime[edge.getTo()] && edge.getFrom() != Integer.MIN_VALUE) {
                    cutPoints.add(edge.getTo());
                }
                ++children;
            }
        }
        isUsed[edge.getTo()] = Color.BLACK;
        if (edge.getFrom() == Integer.MIN_VALUE && children > 1) {
            cutPoints.add(edge.getTo());
        }
        return cutPoints;
    }
}
