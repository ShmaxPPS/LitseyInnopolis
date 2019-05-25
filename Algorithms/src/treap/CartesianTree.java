package treap;

public class CartesianTree {

    private Node root;

    public class Node {
        private Node left;
        private Node right;
        private long key;
        private long priority;

        public Node(long key, long priority) {
            this.key = key;
            this.priority = priority;
        }
    }

    private Node[] split(Node node, long key) {
        if (node == null) {
            return new Node[]{null, null, null};
        }
        if (node.key == key) {
            Node[] splittedNode = new Node[]{node.left, node, node.right};
            node.left = null;
            node.right = null;
            return splittedNode;
        }
        if (node.key < key) {
            Node[] splittedRight = split(node.right, key);
            node.right = splittedRight[0];
            return new Node[]{node, splittedRight[1], splittedRight[2]};
        }
        else {
            Node[] splittedLeft = split(node.left, key);
            node.left = splittedLeft[2];
            return new Node[]{splittedLeft[0], splittedLeft[1], node};
        }
    }

    private Node merge(Node left, Node right) {
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }
        if (left.priority < right.priority) {
            left.right = merge(left.right, right);
            return left;
        } else {
            right.left = merge(left, right.left);
            return right;
        }
    }

    public void insert(long key, long priority) {
        if (root == null) {
            root = new Node(key, priority);
            return;
        }
        Node[] splitted = split(root, key);
        root = merge(merge(splitted[0], new Node(key, priority)), splitted[2]);
    }

    public void remove(long key) {
        Node[] splitted = split(root, key);
        root = merge(splitted[0], splitted[2]);
    }
}
