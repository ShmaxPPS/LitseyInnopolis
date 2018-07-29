package graphs;

import java.util.*;

public class BreadthFirstSearch {

    /* Class Graph */

    private static class Graph {
        private List<Set<Integer>> adjacencyList;

        public Graph(int size) {
            adjacencyList = new ArrayList<>();
            for (int i = 0; i < size; ++i) {
                adjacencyList.add(new HashSet<>());
            }
        }

        public void addEdge(int left, int right) {
            adjacencyList.get(left).add(right);
        }

        public Set<Integer> neighbours(int index) {
            return adjacencyList.get(index);
        }

        public int size() {
            return adjacencyList.size();
        }
    }

    /* Breath First Search algorithm */

    private List<Boolean> isUsed;
    private List<Integer> distances;
    private List<Integer> parent;
    private Graph graph;

    public BreadthFirstSearch(Graph graph) {
        this.graph = graph;
        isUsed = new ArrayList<>();
        distances = new ArrayList<>();
        parent = new ArrayList<>();
        for (int i = 0; i < graph.size(); ++i) {
            isUsed.add(false);
            distances.add(Integer.MAX_VALUE);
            parent.add(Integer.MAX_VALUE);
        }
    }

    public void execute(int start) {
        isUsed.set(start, true);
        distances.set(start, 0);
        LinkedList<Integer> queue = new LinkedList<>();
        queue.addLast(start);
        while (!queue.isEmpty()) {
            int node = queue.pollFirst();
            for (int child : graph.neighbours(node)) {
                if (!isUsed.get(child)) {
                    queue.addLast(child);
                    isUsed.set(child, true);
                    distances.set(child, distances.get(node) + 1);
                    parent.set(child, node);
                }
            }
        }
    }

    public int getDistance(int node) {
        return distances.get(node);
    }

    public int getParent(int node) {
        return parent.get(node);
    }
}
