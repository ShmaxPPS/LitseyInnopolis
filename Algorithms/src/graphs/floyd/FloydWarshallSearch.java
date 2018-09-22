package graphs.floyd;

public class FloydWarshallSearch {

    // Class Graph
    private static class Graph {

        private long[][] adjacencyMatrix;

        public Graph(int size) {
            adjacencyMatrix = new long[size][size];
            for (int i = 0; i < size; ++i) {
                for (int j = 0; j < size; ++j) {
                    if (i == j) {
                        adjacencyMatrix[i][j] = 0L;
                    } else {
                        adjacencyMatrix[i][j] = Long.MAX_VALUE;
                    }
                }
            }
        }

        public void addEdge(int left, int right, long weight) {
            adjacencyMatrix[left][right] = weight;
        }

        public long[] neighbours(int left) {
            return adjacencyMatrix[left];
        }

        public long getWeight(int left, int right) {
            return adjacencyMatrix[left][right];
        }

        public int size() {
            return adjacencyMatrix.length;
        }
    }

    private Graph graph;
    private long[][] distances;

    public FloydWarshallSearch(Graph graph) {
        this.graph = graph;
        distances = new long[graph.size()][graph.size()];
        for (int i = 0; i < graph.size(); ++i) {
            for (int j = 0; j < graph.size(); ++j) {
                distances[i][j] = graph.getWeight(i, j);
            }
        }
    }

    public void execute() {
        for (int k = 0; k < graph.size(); ++k) {
            for (int i = 0; i < graph.size(); ++i) {
                for (int j = 0; j < graph.size(); ++j) {
                    if (distances[i][k] < Long.MAX_VALUE && distances[k][j] < Long.MAX_VALUE) {
                        distances[i][j] = Math.min(distances[i][j], distances[i][k] + distances[k][j]);
                    }
                }
            }
        }
        for (int i = 0; i < graph.size(); ++i) {
            for (int j = 0; j < graph.size(); ++j) {
                for (int k = 0; k < graph.size(); ++k) {
                    if (distances[i][k] < Long.MAX_VALUE && distances[k][k] < 0 && distances[k][j] < Long.MAX_VALUE) {
                        distances[i][j] = Long.MIN_VALUE;
                    }
                }
            }
        }
    }

    public long getDistance(int left, int right) {
        return distances[left][right];
    }
}
