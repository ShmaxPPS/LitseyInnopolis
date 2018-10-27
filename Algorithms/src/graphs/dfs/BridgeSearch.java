package graphs.dfs;

import java.util.ArrayList;
import java.util.List;

public class BridgeSearch {

    // only for returning bridges
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

    public BridgeSearch(Graph graph) {
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

    public List<Edge> execute() {
        List<Edge> bridges = new ArrayList<>();
        for (int i = 0; i < graph.size(); ++i) {
            if (isUsed[i] == Color.WHITE) {
                bridges.addAll(execute(new Edge(Integer.MIN_VALUE, i)));
            }
        }
        return bridges;
    }

    public List<Edge> execute(Edge edge) {
        List<Edge> bridges = new ArrayList<>();
        inTime[edge.getTo()] = time;
        fupTime[edge.getTo()] = time;
        time++;
        isUsed[edge.getTo()] = Color.GREY;
        for (Edge next : graph.neighbours(edge.getTo())) {
            if (next.getTo() == edge.getFrom()) {
                continue;
            }
            if (isUsed[next.getTo()] == Color.GREY) {
                fupTime[edge.getTo()] =  Math.min(fupTime[edge.getTo()], inTime[next.getTo()]);
            }
            else if (isUsed[next.getTo()] == Color.WHITE){
                bridges.addAll(execute(next));
                fupTime[edge.getTo()] = Math.min(fupTime[edge.getTo()], fupTime[next.getTo()]);
                if (fupTime[next.getTo()] > inTime[edge.getTo()]) {
                    bridges.add(next);
                }
            }
        }
        isUsed[edge.getTo()] = Color.BLACK;
        return bridges;
    }

    public static void main(String[] args) {
        Graph graph = new Graph(11);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 3);
        graph.addEdge(3, 0);

        graph.addEdge(4, 5);
        graph.addEdge(5, 6);
        graph.addEdge(6, 4);

        graph.addEdge(8, 9);
        graph.addEdge(9, 10);
        graph.addEdge(10, 8);

        graph.addEdge(2, 4);
        graph.addEdge(4, 7);
        graph.addEdge(7, 8);

        BridgeSearch bridgeSearch = new BridgeSearch(graph);
        for (Edge edge : bridgeSearch.execute()) {
            System.out.println(edge.from + " " + edge.to);
        }
    }
}
