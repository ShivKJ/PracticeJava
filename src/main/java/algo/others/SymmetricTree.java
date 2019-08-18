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

        t1(root.left, l1);
        t2(root.right, l2);

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

    public static void t1(TreeNode n, List<Integer> nodes) {

        if (isNull(n))
            nodes.add(null);
        else {
            nodes.add(n.val);

            t1(n.left, nodes);
            t1(n.right, nodes);
        }
    }

    public static void t2(TreeNode n, List<Integer> nodes) {
        if (isNull(n))
            nodes.add(null);
        else {
            nodes.add(n.val);
            t2(n.right, nodes);
            t2(n.left, nodes);
        }

    }

    private static class TreeNode {
        int      val;
        TreeNode left;
        TreeNode right;

    }

}
