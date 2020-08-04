package probe;

public class BinaryTree<K extends Comparable<K>> {

    private class TreeNode {
        private K key;
        private TreeNode left, right;

        TreeNode(K key) {
            this.key = key;
        }
    }

    private TreeNode root;

    public boolean twoChildren(TreeNode node) {
        if (node.left == null) {
            return node.right == null;
        }
        return twoChildren(node.left) && twoChildren(node.right);
    }
}
