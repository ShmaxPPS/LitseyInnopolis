package graphs;

import java.util.*;

public class DepthFirstSearchNonRecursive {

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
    private List<Iterator<Integer>> neighboursIterator;
    private Graph graph;
    private int time;

    enum Color {
        WHITE, GREY, BLACK
    }

    public DepthFirstSearchNonRecursive(Graph graph) {
        this.graph = graph;
        time = 0;
        isUsed = new ArrayList<>();
        inTime = new ArrayList<>();
        outTime = new ArrayList<>();
        parent = new ArrayList<>();
        neighboursIterator = new ArrayList<>();
        for (int i = 0; i < graph.size(); ++i) {
            isUsed.add(Color.WHITE);
            inTime.add(Integer.MAX_VALUE);
            outTime.add(Integer.MAX_VALUE);
            parent.add(Integer.MAX_VALUE);
            neighboursIterator.add(graph.neighbours(i).iterator());
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
        LinkedList<Integer> stack = new LinkedList<>();
        stack.addLast(start);
        inTime.set(start, time++);
        isUsed.set(start, Color.GREY);
        while (!stack.isEmpty()) {
            int node = stack.peekLast();
            Iterator<Integer> iterator = neighboursIterator.get(node);
            if (iterator.hasNext()) {
                int child = iterator.next();
                if (isUsed.get(child) == Color.WHITE) {
                    inTime.set(child, time++);
                    isUsed.set(child, Color.GREY);
                    parent.set(child, node);
                    stack.addLast(child);
                }
            } else {
                outTime.set(node, time++);
                isUsed.set(node, Color.BLACK);
                stack.pollLast();
            }
        }
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
