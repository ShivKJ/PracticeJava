package algo.others;

import static java.lang.System.out;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.joining;

import java.util.Objects;
import java.util.stream.Stream;

public final class RemoveNthElementFromList {

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode nTh = head, out = head;

        for (int i = 0; i < n - 1; i++)
            nTh = nTh.next;

        ListNode a = null;

        while (nonNull(nTh.next)) {
            a = head;

            head = a.next;
            nTh = nTh.next;
        }

        if (isNull(a))
            return head.next;
        else
            a.next = head.next;

        return out;
    }

    public final static class ListNode {
        private final int val;
        private ListNode  next;

        public ListNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            return "" + val;
        }
    }

    private static void print(ListNode n) {
        out.println(Stream.iterate(n, Objects::nonNull, t -> t.next)
                          .map(String::valueOf)
                          .collect(joining("->")));
    }

    public static void main(String[] args) {
        ListNode n = new ListNode(0);

        n.next = new ListNode(1);
        n.next.next = new ListNode(2);
        n.next.next.next = new ListNode(3);

        print(n);

        print(removeNthFromEnd(n, 2));
    }

}
