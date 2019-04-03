package graphs.flows;

public class FordFulkersonMaxFlowSearch {

    public static int maxFlow(int[][] capacity, int s, int t) {
        for (int flow = 0;;) {
            int df = findPath(capacity, new boolean[capacity.length], s, t, Integer.MAX_VALUE);
            if (df == 0)
                return flow;
            flow += df;
        }
    }

    public static int findPath(int[][] capacity, boolean[] visited, int u, int t, int f) {
        if (u == t) {
            return f;
        }
        visited[u] = true;
        for (int v = 0; v < visited.length; v++) {
            if (!visited[v] && capacity[u][v] > 0) {
                int df = findPath(capacity, visited, v, t, Math.min(f, capacity[u][v]));
                if (df > 0) {
                    capacity[u][v] -= df;
                    capacity[v][u] += df;
                    return df;
                }
            }
        }
        return 0;
    }

    // Usage example
    public static void main(String[] args) {
        int[][] capacity = { { 0, 3, 2 }, { 0, 0, 2 }, { 0, 0, 0 } };
        System.out.println(maxFlow(capacity, 0, 2) == 4);
    }
}
