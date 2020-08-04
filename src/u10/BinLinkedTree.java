package u10;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class BinLinkedTree<T extends Comparable<T>, D> implements Iterable<T> {

    private class DTreeNode {
        DTreeNode parent;
        DTreeNode left;
        DTreeNode right;
        T key;
        D data;

        public DTreeNode(T key, D data) {
            this.key = key;
            this.data = data;
        }

        public void setParent(DTreeNode parent) {
            this.parent = parent;
        }

        public void setLeft(DTreeNode left) {
            this.left = left;
        }

        public void setRight(DTreeNode right) {
            this.right = right;
        }

        public void setKey(T key) {
            this.key = key;
        }

        public void setData(D data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "DTreeNode{" +
                    "key=" + key +
                    ", data=" + data +
                    '}';
        }
    }

    public class TreeIterator implements Iterator<T> {
        private final Deque<DTreeNode> stack;
        private DTreeNode next;

        public TreeIterator(BinLinkedTree<T, D> tree) {
            stack = new ArrayDeque<>(tree.size());
            postOrderTraverse(stack, tree.head);
        }

        public boolean hasNext() {
            return !stack.isEmpty();
        }

        public T next() {
            if (stack.isEmpty())
                throw new NoSuchElementException();

            return stack.pop().key;
        }
    }

    private int size;
    private DTreeNode head;

    public BinLinkedTree() {
        this.size = 0;
        this.head = null;
    }

    public boolean empty() {
        return head == null;
    }

    private void clearHead() {
        head = null;
        size = 0;
    }

    public DTreeNode minNode(DTreeNode node) {
        DTreeNode current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    public DTreeNode maxNode(DTreeNode node) {
        DTreeNode current = node;
        while (current.right != null) {
            current = current.right;
        }
        return current;
    }

    public DTreeNode succ(DTreeNode node) {
        // If the node has a child that is greater, go in that direction
        if (node.right != null)
            return node.right;

        // If the node has no child greater than itself
        // and no parent, then there is no greater element.
        if (node.parent == null)
            return null;

        DTreeNode prev;

        // While there is a parent element, go up.
        // With each step, try to go right. If the element
        // to the right is not the element we came from,
        // return it.
        // If the top of the tree was reached, break the loop.
        do {
            prev = node;
            node = node.parent;

            if (node.right != prev) {
                return node.right;
            }
        } while (node.parent != null);

        // Return the element if we didn't return from the loop
        return node;
    }

    public DTreeNode pred(DTreeNode node) {
        return null;
    }

    /* A recursive function to insert a new key in BST */
    private DTreeNode deleteRec(DTreeNode root, T key) {
        /* Base Case: If the tree is empty */
        if (root == null)
            return root;

        /* Otherwise, recur down the tree */
        int comparison = key.compareTo(root.key);
        if (comparison < 0) {
            root.left = deleteRec(root.left, key);
        } else if (comparison > 0) {
            root.right = deleteRec(root.right, key);
        } else {
            // node with only one child or no child
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            // node with two children: Get the inorder successor (smallest
            // in the right subtree)
            root.key = minNode(root.right).key;

            // Delete the inorder successor
            root.right = deleteRec(root.right, root.key);
        }

        return root;
    }

    public boolean delete(T key) {
        head = deleteRec(head, key);
        return true;
    }

    public int deep() {
        return 0;
    }

    public int size() {
        return size;
    }

    public Object[] toArray() {
        if (empty()) {
            return new Object[0];
        }
        Deque<DTreeNode> stack = new ArrayDeque<>();
        inOrderTraverse(stack, head);
        Object[] result = new Object[stack.size()];
        for (int i = 0; i < size(); i++) {
            result[i] = stack.pollFirst().data;
        }
        return result;
    }

    public Deque<DTreeNode> ioStack() {
        Deque<DTreeNode> stack = new ArrayDeque<>();
        inOrderTraverse(stack, head);
        return stack;
    }

    // TODO
    public boolean perfectBalanced(DTreeNode node) {
        return false;
    }

    public void insert(T key, D data) {
        head = insert(head, key, data);
    }

    private DTreeNode insert(DTreeNode node, T key, D data) {
        if (node == null) {
            size++;
            return new DTreeNode(key, data);
        }

        int compare = key.compareTo(node.key);

        if (compare < 0) {
            node.left = insert(node.left, key, data);
        } else if (compare > 0) {
            node.right = insert(node.right, key, data);
        } else {
            node.data = data;
        }

        return node;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new TreeIterator(this);
    }

    public String toString() {
        return ((Object) this).toString();
    }

    public void postOrderTraverse(Deque<DTreeNode> stack, DTreeNode dtn) {
        if (dtn != null) {
            postOrderTraverse(stack, dtn.left);
            postOrderTraverse(stack, dtn.right);
            stack.add(dtn);
        }
    }

    public void inOrderTraverse(Deque<DTreeNode> stack, DTreeNode dtn) {
        if (dtn != null) {
            inOrderTraverse(stack, dtn.left);
            stack.add(dtn);
            inOrderTraverse(stack, dtn.right);
        }
    }

    public DTreeNode getHead() {
        return new DTreeNode(head.key, head.data);
    }

    private DTreeNode dtn(T key, D data) {
        return new DTreeNode(key, data);
    }

    public static void main(String[] args) {
        var tree = new BinLinkedTree<Integer, String>();

        tree.insert(1, "12345");
        tree.insert(1, "123");
        tree.insert(2, "234");
        tree.insert(0, "012");
        tree.insert(3, "345");
        tree.insert(4, "456");
        tree.insert(5, "567");
        System.out.println(tree.ioStack());
        System.out.println(Arrays.asList(tree.toArray()));
        System.out.println(Arrays.toString(tree.toArray()));

        tree.delete(1);
        System.out.println(tree.ioStack());
    }
}
