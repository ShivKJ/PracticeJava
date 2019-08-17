package algo.others;

import static java.util.Comparator.comparingInt;
import static java.util.Objects.nonNull;

import java.util.PriorityQueue;

public class MergeKSortedList {
    public class ListNode {
        private final int val;
        private ListNode  next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0)
            return null;

        PriorityQueue<ListNode> pq = new PriorityQueue<>(lists.length, comparingInt(n -> n.val));

        for (ListNode listNode : lists)
            if (nonNull(listNode))
                pq.add(listNode);

        ListNode ptr = new ListNode(0), out = ptr;

        while (!pq.isEmpty()) {
            ListNode n = pq.poll();

            ptr.next = n;
            ptr = ptr.next;

            if (nonNull(n.next))
                pq.add(n.next);
        }

        return out.next;
    }

}
