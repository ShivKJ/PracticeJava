package algo.others;

import static java.lang.Integer.MIN_VALUE;

import java.util.ArrayList;
import java.util.List;

public class MaxLevelSum {

    public static int maxLevelSum(TreeNode root) {
        List<Integer> levelSum = new ArrayList<>();

        updateLevelSum(root, 0, levelSum);

        int out = 0, best = MIN_VALUE;

        for (int i = 0; i < levelSum.size(); i++)
            if (levelSum.get(i) > best) {
                best = levelSum.get(i);
                out = i;
            }

        return out + 1;
    }

    private static void updateLevelSum(TreeNode n, int depth, List<Integer> sum) {
        if (n != null) {
            if (sum.size() == depth)
                sum.add(n.val);
            else
                sum.set(depth, sum.get(depth) + n.val);
        }

        if (n.left != null)
            updateLevelSum(n.left, depth + 1, sum);

        if (n.right != null)
            updateLevelSum(n.right, depth + 1, sum);

    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(7);
        root.right = new TreeNode(0);
        root.left.left = new TreeNode(7);
        root.left.right = new TreeNode(-8);

        System.out.println(maxLevelSum(root));
    }

    public static class TreeNode {
        int      val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
