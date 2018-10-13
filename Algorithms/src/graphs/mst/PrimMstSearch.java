import java.util.ArrayList;
import java.util.List;

public class Prim {
    public final long INF = Long.MAX_VALUE;
    private boolean[] used;
    private int[] selEdge;
    private long[] minEdge;
    private Graph g;

    public Prim(int size) {
        used = new boolean[size];
        minEdge = new long[size];
        selEdge = new int[size];
        g = new Graph(size);
        for (int i = 0; i < size; ++i) {
            minEdge[i] = INF;
            selEdge[i] = -1;
        }
    }

    public class Graph {

        private List<List<Integer>> edges;
        private int[][] e;

        public Graph(int size) {
            e = new int[size][size];
            edges = new ArrayList<>();
            for (int i = 0; i < size; ++i) {
                edges.add(new ArrayList<>());
            }
        }

        public int size() { // return size of graph
            return edges.size();
        }

        public void addEdge(int v, int u, int w) {
            edges.get(v).add(u);
            e[v][u] = w;
        }

        public List<Integer> neighbours(int v) { // return list of neighbours from vertex v
            return edges.get(v);
        }

        public int get_Edge(int v, int u) {
            return e[v][u];
        }
    }

    public void addEdge(int v, int u, int w) {
        g.addEdge(v, u, w);
        g.addEdge(u, v, w);
    }

    public List<Integer> neighbours(int v) {
        return g.neighbours(v);
    }

    public Graph get_MST() {
        int size = minEdge.length;
        Graph mst = new Graph(size);
        for (int i = 0; i < size; ++i) {
            int v = -1;
            for (int j = 0; j < size; ++j) {
                if (!used[j] && (v == -1 || minEdge[j] < minEdge[v])) {
                    v = j;
                }
            }
            if (minEdge[v] == INF) {
                return new Graph(0);
            }
            used[v] = true;
            if (selEdge[v] != -1) {
                mst.addEdge(v, selEdge[v], g.get_Edge(v, selEdge[v]));
                mst.addEdge(selEdge[v], v, g.get_Edge(v, selEdge[v]));
            }

            for (int to : g.neighbours(v)) {
                if (g.get_Edge(v, to) < minEdge[to]) {
                    minEdge[to] = to;
                    selEdge[to] = v;
                }
            }
        }
        return mst;
    }
}
