package sqrt;

import java.io.*;
import java.util.*;

public class TriangleCounter {

    public static class Graph {

        private static int HEAVY_SIZE;
        private List<List<Integer>> adjacencyList;
        private List<List<Integer>> lightAdjacencyList;
        private List<List<Integer>> heavyAdjacencyList;

        public Graph(int size) {
            adjacencyList = new ArrayList<>();
            lightAdjacencyList = new ArrayList<>();
            heavyAdjacencyList = new ArrayList<>();
            for (int i = 0; i < size; ++i) {
                adjacencyList.add(new ArrayList<>());
                lightAdjacencyList.add(new ArrayList<>());
                heavyAdjacencyList.add(new ArrayList<>());
            }
        }

        public void setHeavySize(int size) {
            HEAVY_SIZE = (int) Math.sqrt(size) + 1;
        }

        public void addEdge(int from, int to) {
            adjacencyList.get(from).add(to);
            adjacencyList.get(to).add(from);
        }

        public void calculateTypes() {
            for (int from = 0; from < adjacencyList.size(); ++from) {
                for (int to : adjacencyList.get(from)) {
                    if (isHeavy(from)) {
                        heavyAdjacencyList.get(to).add(from);
                    } else {
                        lightAdjacencyList.get(to).add(from);
                    }
                }
            }
        }

        public List<Integer> getHeavyNeighbours(int from) {
            return heavyAdjacencyList.get(from);
        }

        public List<Integer> getLightNeighbours(int from) {
            return lightAdjacencyList.get(from);
        }

        public boolean isHeavy(int from) {
            return adjacencyList.get(from).size() >= HEAVY_SIZE;
        }

        public int countTripleHeavyTriangles() {
            int count = 0;
            for (int u = 0; u < adjacencyList.size(); ++u) {
                if (!isHeavy(u)) {
                    continue;
                }
                Set<Integer> isUsed = new HashSet<>(getHeavyNeighbours(u));
                for (int v : getHeavyNeighbours(u)) {
                    if (u > v) {
                        continue;
                    }
                    for (int w : getHeavyNeighbours(v)) {
                        if (w > v && isUsed.contains(w)) {
                            ++count;
                        }
                    }
                }
            }
            return count;
        }

        public int countDoubleHeavyTriangles() {
            int count = 0;
            for (int u = 0; u < adjacencyList.size(); ++u) {
                if (!isHeavy(u)) {
                    continue;
                }
                Set<Integer> isUsed = new HashSet<>(getLightNeighbours(u));
                for (int v : getHeavyNeighbours(u)) {
                    if (u > v) {
                        continue;
                    }
                    for (int w : getLightNeighbours(v)) {
                        if (isUsed.contains(w)) {
                            ++count;
                        }
                    }
                }
            }
            return count;
        }

        public int countDoubleLightTriangles() {
            int count = 0;
            for (int u = 0; u < adjacencyList.size(); ++u) {
                if (!isHeavy(u)) {
                    continue;
                }
                Set<Integer> isUsed = new HashSet<>(getLightNeighbours(u));
                for (int v : getLightNeighbours(u)) {
                    for (int w : getLightNeighbours(v)) {
                        if (w > v && isUsed.contains(w)) {
                            ++count;
                        }
                    }
                }
            }
            return count;
        }

        public int countTripleLightTriangles() {
            int count = 0;
            for (int u = 0; u < adjacencyList.size(); ++u) {
                if (isHeavy(u)) {
                    continue;
                }
                Set<Integer> isUsed = new HashSet<>(getLightNeighbours(u));
                for (int v : getLightNeighbours(u)) {
                    if (u > v) {
                        continue;
                    }
                    for (int w : getLightNeighbours(v)) {
                        if (w > v && isUsed.contains(w)) {
                            ++count;
                        }
                    }
                }
            }
            return count;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
        String[] lines = scanner.readLine().split(" ");
        int n = Integer.parseInt(lines[0]);
        int m = Integer.parseInt(lines[1]);
        Graph graph = new Graph(n);
        for (int i = 0; i < m; ++i) {
            lines = scanner.readLine().split(" ");
            int from = Integer.parseInt(lines[0]) - 1;
            int to = Integer.parseInt(lines[1]) - 1;
            graph.addEdge(from, to);
        }
        graph.setHeavySize(m);
        graph.calculateTypes();
        int count = 0;
        count += graph.countTripleHeavyTriangles();
        count += graph.countDoubleHeavyTriangles();
        count += graph.countDoubleLightTriangles();
        count += graph.countTripleLightTriangles();
        writer.println(count);
        writer.close();
    }
}

