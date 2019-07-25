package algo.others;

import static java.lang.System.out;
import static java.util.stream.Collectors.joining;

import java.util.Objects;
import java.util.stream.Stream;

public class AddingTwoNumbers {

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int sum = l1.val + l2.val;

        ListNode n = new ListNode(sum % 10), out = n;
        int carry = sum / 10;

        l1 = l1.next;
        l2 = l2.next;

        while (l1 != null && l2 != null) {
            sum = l1.val + l2.val + carry;
            carry = sum / 10;

            n.next = new ListNode(sum % 10);

            n = n.next;
            l1 = l1.next;
            l2 = l2.next;
        }

        if (l1 == null)
            addRest(n, l2, carry);
        else
            addRest(n, l1, carry);

        return out;
    }

    private static void addRest(ListNode n, ListNode other, int carry) {
        while (other != null) {
            int sum = carry + other.val;

            n.next = new ListNode(sum % 10);

            n = n.next;
            other = other.next;

            carry = sum / 10;

            if (carry == 0) {
                n.next = other;
                break;
            }
        }

        if (carry != 0)
            n.next = new ListNode(carry);

    }

    public static void main(String[] args) {
        ListNode n1 = create(new int[] { 1 });
        ListNode n2 = create(new int[] { 9 });
        print(addTwoNumbers(n1, n2));
    }

    private static ListNode create(int[] arr) {
        ListNode n = new ListNode(arr[0]), out = n;

        for (int i = 1; i < arr.length; i++, n = n.next)
            n.next = new ListNode(arr[i]);

        return out;
    }

    private static void print(ListNode n) {
        out.println(Stream.iterate(n, Objects::nonNull, t -> t.next)
                          .map(String::valueOf)
                          .collect(joining("->")));
    }

    public static class ListNode {
        int      val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            return "" + val;
        }
    }
}
