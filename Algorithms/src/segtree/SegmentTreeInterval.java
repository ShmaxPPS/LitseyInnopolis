package segtree;

public class SegmentTreeInterval {

    private int size;
    private long[] mass;
    private long[] segmentTree;
    private long[] addTree;

    public SegmentTreeInterval(long[] mass) {
        size = 1 << ((int) (Math.log(mass.length) / Math.log(2)) + 1);
        this.mass = new long[size];
        for (int i = 0; i < mass.length; ++i) {
            this.mass[i] = mass[i];
        }
        for (int i = mass.length; i < size; ++i) {
            this.mass[i] = 0;
        }
        build();
    }

    private void build() {
        segmentTree = new long[2 * size];
        addTree = new long[2 * size];
        for (int i = 0; i < 2 * size; ++i) {
            segmentTree[i] = 0;
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
            segmentTree[mi] = segmentTree[2 * mi] + segmentTree[2 * mi + 1];
        }
    }

    // sum[a, b]
    public long sum(int a, int b) {
        return sum(1, 0, size - 1, a, b);
    }

    private long sum(int mi, int ml, int mr, int a, int b) {
        push(mi, ml, mr);
        if (b < ml || mr < a) {
            return 0;
        }
        if (a <= ml && mr <= b) {
            return segmentTree[mi];
        }
        int mm = (ml + mr) / 2;
        long leftSum = sum(2 * mi, ml, mm, a, b);
        long rightSum = sum(2 * mi + 1, mm + 1, mr, a, b);
        return leftSum + rightSum;
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
            addTree[mi] += value;
            push(mi, ml, mr);
            return;
        }
        int mm = (ml + mr) / 2;
        add(2 * mi, ml, mm, a, b, value);
        add(2 * mi + 1, mm + 1, mr, a, b, value);
        segmentTree[mi] = segmentTree[2 * mi] + segmentTree[2 * mi + 1];
    }

    private void push(int mi, int ml, int mr) {
        if (addTree[mi] != 0) {
            segmentTree[mi] += (mr - ml + 1) * addTree[mi];
            if (ml != mr) {
                addTree[2 * mi] += addTree[mi];
                addTree[2 * mi + 1] += addTree[mi];
            }
            addTree[mi] = 0;
        }
    }
}
