package segtree;

import java.io.*;

public class ProblemE {

    private int size;
    private long[] mass;
    private long[] segmentTree;
    private long[] addTree;
    private long[] setTree;

    public ProblemE(long[] mass) {
        size = 1 << ((int) (Math.log(mass.length) / Math.log(2)) + 1);
        this.mass = new long[size];
        for (int i = 0; i < mass.length; ++i) {
            this.mass[i] = mass[i];
        }
        for (int i = mass.length; i < size; ++i) {
            this.mass[i] = Long.MAX_VALUE;
        }
        build();
    }

    private void build() {
        segmentTree = new long[2 * size];
        addTree = new long[2 * size];
        setTree = new long[2 * size];
        for (int i = 0; i < 2 * size; ++i) {
            segmentTree[i] = Long.MAX_VALUE;
            setTree[i] = Long.MAX_VALUE;
            addTree[i] = 0;
        }
        build(1, 0, size - 1);
    }

    // build segment tree
    private void build(int mi, int ml, int mr) {
        if (ml == mr) {
            segmentTree[mi] = mass[ml];
        } else {
            int mm = (ml + mr) / 2;
            build(2 * mi, ml, mm);
            build(2 * mi + 1, mm + 1, mr);
            segmentTree[mi] = Math.min(segmentTree[2 * mi], segmentTree[2 * mi + 1]);
        }
    }

    // min[a, b]
    public long min(int a, int b) {
        return min(1, 0, size - 1, a, b);
    }

    private long min(int mi, int ml, int mr, int a, int b) {
        push(mi, ml, mr);
        if (b < ml || mr < a) {
            return Long.MAX_VALUE;
        }
        if (a <= ml && mr <= b) {
            return segmentTree[mi];
        }
        int mm = (ml + mr) / 2;
        long leftMin = min(2 * mi, ml, mm, a, b);
        long rightMin = min(2 * mi + 1, mm + 1, mr, a, b);
        return Math.min(leftMin, rightMin);
    }

    // add[a, b]
    public void add(int a, int b, long value) {
        add(1, 0, size - 1, a, b, value);
    }

    private void add(int mi, int ml, int mr, int a, int b, long value) {
        push(mi, ml, mr);
        if (b < ml || mr < a) {
            return;
        }
        if (a <= ml && mr <= b) {
            addTree[mi] = value;
            push(mi, ml, mr);
            return;
        }
        int mm = (ml + mr) / 2;
        add(2 * mi, ml, mm, a, b, value);
        add(2 * mi + 1, mm + 1, mr, a, b, value);
        segmentTree[mi] = Math.min(segmentTree[2 * mi], segmentTree[2 * mi + 1]);
    }

    // set[a, b]
    public void set(int a, int b, long value) {
        set(1, 0, size - 1, a, b, value);
    }

    private void set(int mi, int ml, int mr, int a, int b, long value) {
        push(mi, ml, mr);
        if (b < ml || mr < a) {
            return;
        }
        if (a <= ml && mr <= b) {
            setTree[mi] = value;
            push(mi, ml, mr);
            return;
        }
        int mm = (ml + mr) / 2;
        set(2 * mi, ml, mm, a, b, value);
        set(2 * mi + 1, mm + 1, mr, a, b, value);
        segmentTree[mi] = Math.min(segmentTree[2 * mi], segmentTree[2 * mi + 1]);
    }

    private void push(int mi, int ml, int mr) {
        if (setTree[mi] != Long.MAX_VALUE) {
            segmentTree[mi] = setTree[mi];
            if (ml != mr) {
                setTree[2 * mi] = setTree[mi];
                setTree[2 * mi + 1] = setTree[mi];
                addTree[2 * mi] = 0;
                addTree[2 * mi + 1] = 0;
            }
            setTree[mi] = Long.MAX_VALUE;
        }
        if (addTree[mi] != 0) {
            segmentTree[mi] += addTree[mi];
            if (ml != mr) {
                addTree[2 * mi] += addTree[mi];
                addTree[2 * mi + 1] += addTree[mi];
            }
            addTree[mi] = 0;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out));
        String line = scanner.readLine();
        int n = Integer.parseInt(line);
        line = scanner.readLine();
        String[] lines = line.split(" ");
        long[] mass = new long[n];
        for (int i = 0; i < n; ++i) {
            mass[i] = Long.parseLong(lines[i]);
        }
        ProblemE tree = new ProblemE(mass);
        while ((line = scanner.readLine()) != null) {
            lines = line.split(" ");
            if (lines[0].equals("min")) {
                int left = Integer.parseInt(lines[1]) - 1;
                int right = Integer.parseInt(lines[2]) - 1;
                writer.println(tree.min(left, right));
            }
            else if (lines[0].equals("add")) {
                int a = Integer.parseInt(lines[1]) - 1;
                int b = Integer.parseInt(lines[2]) - 1;
                long value = Long.parseLong(lines[3]);
                tree.add(a, b, value);
            }
            else if (lines[0].equals("set")) {
                int a = Integer.parseInt(lines[1]) - 1;
                int b = Integer.parseInt(lines[2]) - 1;
                long value = Long.parseLong(lines[3]);
                tree.set(a, b, value);
            }
        }
        writer.close();
    }
}
