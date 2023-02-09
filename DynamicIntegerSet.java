package cse214hw2;

public class DynamicIntegerSet implements DynamicSet {

    public static class Node implements PrintableNode {
        Integer data;
        Node left, right;

        Node(int x) {
            this(x, null, null);
        }

        Node(int x, Node left, Node right) {
            this.data = x;
            this.left = left;
            this.right = right;
        }

        @Override
        public String getValueAsString() {
            return data.toString();
        }

        @Override
        public PrintableNode getLeft() {
            return left;
        }

        @Override
        public PrintableNode getRight() {
            return right;
        }
    }

    private Node root;
    // this method must be there exactly in this form
    public Node root() {
        return this.root;
    }
// rest of your code for this class, including the size, contains, add, and remove methods
    @Override
    public int size() {
        return size(root);
    }
    public int size(Node node) {
        if (node == null)
            return 0;
        else
            return (size(node.left) + 1 + size(node.right)); //why +1?
    }

    @Override
    public boolean contains(Integer x) {
        if (root == null) return false;
        else return contains(x, root);
    }

    private boolean contains(Integer x, Node node) {
        if (node.data.equals(x)) return true;
        if (x < node.data && node.left != null && contains(x,node.left)) return true;
        return x > node.data && node.right != null && contains(x, node.right);
    }

    @Override
    public boolean add(Integer x) {
        if (root == null) {
            root = new Node(x);
            return true;
        }
        else
            return add(x, root);
    }

    private boolean add(Integer x, Node node) {
        if (node.data.equals(x)) return false;
        if (x < node.data) {
            if (node.left == null) {
                node.left = new Node(x);
                return true;
            } else return add(x, node.left);
        }
        if (node.right == null) {
            node.right = new Node(x);
            return true;
        } else return add(x, node.right);
    }

    @Override
    public boolean remove(Integer x) {
        if (root == null) return false;

        Node toBeDeleted = root;
        Node parent = root;
        boolean isLeft = true;

        while (!toBeDeleted.data.equals(x)) {
            parent = toBeDeleted;
            if (x < toBeDeleted.data) {
                isLeft = true;
                toBeDeleted = toBeDeleted.left;
            } else {
                isLeft = false;
                toBeDeleted = toBeDeleted.right;
            }
            if (toBeDeleted == null) return false;
        }

        if (toBeDeleted.left == null && toBeDeleted.right == null) {
            if (isLeft)
                parent.left = null;
            else
                parent.right = null;
        } else if (toBeDeleted.left == null) {
            if (isLeft)
                parent.left = toBeDeleted.right;
            else
                parent.right = toBeDeleted.right;
        } else if (toBeDeleted.right == null) {
            if (isLeft)
                parent.left = toBeDeleted.left;
            else
                parent.right = toBeDeleted.left;
        } else {
            Node successor = successor(toBeDeleted);
            remove(successor.data);
            toBeDeleted.data = successor.data;
        }
        return true;
    }

    private Node successor(Node node) {
        Node successor = node.right;
        while (successor.left != null)
            successor = successor.left;
        return successor;
    }
}
