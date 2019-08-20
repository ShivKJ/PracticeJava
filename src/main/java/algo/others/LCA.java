package algo.others;

import java.util.LinkedList;
import java.util.Queue;

public final class LCA {

    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root != null) {

            if (root.val == p.val || root.val == q.val)
                return root;

            TreeNode l = lowestCommonAncestor(root.left, p, q), r = lowestCommonAncestor(root.right, p, q);

            if (l != null && r != null)
                return root;

            return l == null ? r : l;// It is assumed that p and q node is in Tree.
            
        }

        return null;
    }

    public static void main(String[] args) {
        TreeNode root = stringToTreeNode("[3,5,1,6,2,0,8,null,null,7,4]");
        TreeNode p = stringToTreeNode("[5]");
        TreeNode q = stringToTreeNode("[4]");
        System.out.println(lowestCommonAncestor(root, p, q));
    }

    // --------------------------------LeetCode--------------------------------------------------
    public static TreeNode stringToTreeNode(String input) {
        input = input.trim();
        input = input.substring(1, input.length() - 1);
        if (input.length() == 0) {
            return null;
        }

        String[] parts = input.split(",");
        String item = parts[0];
        TreeNode root = new TreeNode(Integer.parseInt(item));
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        nodeQueue.add(root);

        int index = 1;
        while (!nodeQueue.isEmpty()) {
            TreeNode node = nodeQueue.remove();

            if (index == parts.length) {
                break;
            }

            item = parts[index++];
            item = item.trim();
            if (!item.equals("null")) {
                int leftNumber = Integer.parseInt(item);
                node.left = new TreeNode(leftNumber);
                nodeQueue.add(node.left);
            }

            if (index == parts.length) {
                break;
            }

            item = parts[index++];
            item = item.trim();
            if (!item.equals("null")) {
                int rightNumber = Integer.parseInt(item);
                node.right = new TreeNode(rightNumber);
                nodeQueue.add(node.right);
            }
        }
        return root;
    }

    public static class TreeNode {
        int      val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            // TODO Auto-generated method stub
            return "" + val;
        }
    }

}
