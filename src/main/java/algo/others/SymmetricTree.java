package algo.others;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SymmetricTree {

    public static boolean isSymmetric(TreeNode root) {
        if (isNull(root))
            return true;

        List<Integer> l1 = new LinkedList<>(), l2 = new LinkedList<>();

        preOrder(root.left, l1);
        reversePreorder(root.right, l2);

        return isEqual(l1, l2);
    }

    private static boolean isEqual(List<Integer> l1, List<Integer> l2) {

        Iterator<Integer> itr1 = l1.iterator(), itr2 = l2.iterator();

        while (itr1.hasNext() && itr2.hasNext())
            if (itr1.next() != itr2.next())
                return false;

        while (itr1.hasNext())
            if (nonNull(itr1.next()))
                return false;

        while (itr2.hasNext())
            if (nonNull(itr2.next()))
                return false;

        return true;

    }

    public static void preOrder(TreeNode n, List<Integer> nodes) {

        if (isNull(n))
            nodes.add(null);
        else {
            nodes.add(n.val);

            preOrder(n.left, nodes);
            preOrder(n.right, nodes);
        }
    }

    public static void reversePreorder(TreeNode n, List<Integer> nodes) {
        if (isNull(n))
            nodes.add(null);
        else {
            nodes.add(n.val);
            reversePreorder(n.right, nodes);
            reversePreorder(n.left, nodes);
        }

    }

    private static class TreeNode {
        int      val;
        TreeNode left;
        TreeNode right;

    }

}
