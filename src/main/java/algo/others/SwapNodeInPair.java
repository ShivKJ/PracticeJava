package algo.others;

public class SwapNodeInPair {
    public static ListNode swapPairs(ListNode a) {
        if (a == null || a.next == null)
            return a;

        ListNode out = a.next, prev = new ListNode(-1);

        while (a != null && a.next != null) {
            ListNode b = a.next;
            
            a.next = b.next;
            b.next = a;

            prev.next = b;
            prev = a;
            
            a = a.next;
        }

        return out;
    }

    public static String get(ListNode n) {
        return "" + (n != null ? n.val : n);
    }

    public static void main(String[] args) {
        ListNode a = new ListNode(1);
//        a.next = new ListNode(2);
//        a.next.next = new ListNode(3);
//        a.next.next.next = new ListNode(4);

        System.out.println(a);
        System.out.println(swapPairs(a));

    }

    public static class ListNode {
        int      val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            return val + (next != null ? " -> " + next : "");
        }

    }
}
