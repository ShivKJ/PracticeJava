package algo.others;

import static java.lang.System.out;
import static java.util.stream.Collectors.joining;

import java.util.Objects;
import java.util.stream.Stream;

public class MergingTwoSortedLists {
    public static final class ListNode {
        final int val;
        ListNode  next;

        ListNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            return "" + val;
        }
    }

    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {

        if (l1 == null)
            return l2;

        if (l2 == null)
            return l1;

        if (l1.val > l2.val)
            return mergeTwoLists(l2, l1);

        ListNode s = new ListNode(l1.val), out = s;

        l1 = l1.next;

        while (l1 != null && l2 != null) {
            int val;

            if (l1.val <= l2.val) {
                val = l1.val;
                l1 = l1.next;
            } else {
                val = l2.val;
                l2 = l2.next;
            }

            s.next = new ListNode(val);
            s = s.next;
        }

        s.next = l1 == null ? l2 : l1;

        return out;
    }

    public static void main(String[] args) {
//        ListNode a = new ListNode(0);
//        a.next = new ListNode(5);
//        a.next.next = new ListNode(10);
//        a.next.next.next = new ListNode(11);
//
//        ListNode b = new ListNode(2);
//        b.next = new ListNode(4);
//        b.next.next = new ListNode(5);
//        b.next.next.next = new ListNode(6);
//        b.next.next.next.next = new ListNode(7);
        ListNode a = new ListNode(1);
        a.next = new ListNode(2);
        a.next.next = new ListNode(4);

        ListNode b = new ListNode(1);
        b.next = new ListNode(3);
        b.next.next = new ListNode(4);

        print(mergeTwoLists(a, b));
    }

    private static void print(ListNode n) {
        out.println(Stream.iterate(n, Objects::nonNull, t -> t.next)
                          .map(String::valueOf)
                          .collect(joining("->")));
    }

}
