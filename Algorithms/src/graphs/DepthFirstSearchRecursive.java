package graphs;

import java.util.*;

public class DepthFirstSearchRecursive {

    /* Class Graph */

    private static class Graph {
        private List<List<Integer>> adjacencyList;

        public Graph(int size) {
            adjacencyList = new ArrayList<>();
            for (int i = 0; i < size; ++i) {
                adjacencyList.add(new ArrayList<>());
            }
        }

        public void addEdge(int left, int right) {
            adjacencyList.get(left).add(right);
        }

        public List<Integer> neighbours(int index) {
            return adjacencyList.get(index);
        }

        public int size() {
            return adjacencyList.size();
        }
    }

    /* Depth First Search algorithm */

    private List<Color> isUsed;
    private List<Integer> inTime;
    private List<Integer> outTime;
    private List<Integer> parent;
    private Graph graph;
    private int time;

    enum Color {
        WHITE, GREY, BLACK
    }

    public DepthFirstSearchRecursive(Graph graph) {
        this.graph = graph;
        time = 0;
        isUsed = new ArrayList<>();
        inTime = new ArrayList<>();
        outTime = new ArrayList<>();
        parent = new ArrayList<>();
        for (int i = 0; i < graph.size(); ++i) {
            isUsed.add(Color.WHITE);
            inTime.add(Integer.MAX_VALUE);
            outTime.add(Integer.MAX_VALUE);
            parent.add(Integer.MAX_VALUE);
        }
    }

    public void execute() {
        for (int i = 0; i < graph.size(); ++i) {
            if (isUsed.get(i) == Color.WHITE) {
                execute(i);
            }
        }
    }

    public void execute(int start) {
        inTime.set(start, time++);
        isUsed.set(start, Color.GREY);
        for (int child : graph.neighbours(start)) {
            if (isUsed.get(child) == Color.WHITE) {
                parent.set(child, start);
                execute(child);
            }
        }
        outTime.set(start, time++);
        isUsed.set(start, Color.BLACK);
    }

    public int getInTime(int node) {
        return inTime.get(node);
    }

    public int getOutTime(int node) {
        return outTime.get(node);
    }

    public int getParent(int node) {
        return parent.get(node);
    }
}
