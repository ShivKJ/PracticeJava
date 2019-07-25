package algo.others;

import static java.lang.Math.abs;
import static java.util.Collections.asLifoQueue;

import java.util.LinkedList;
import java.util.Queue;

public class ValidParanthesis {
    private static boolean isClose(char c) {
        return (c & 3) == 1;
    }

    public static boolean isValid(String s) {
        Queue<Character> stack = asLifoQueue(new LinkedList<>());
        int n = s.length();

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);

            if (isClose(c)) {
                if (stack.isEmpty())
                    return false;

                int sc = stack.peek();

                if (abs(c - sc) > 2)
                    return false;

                stack.poll();

            } else
                stack.add(c);
        }

        return stack.isEmpty();
    }

    public static void main(String[] args) {
        System.out.println(isValid("()"));
        System.out.println(isValid("()[]{}"));
        System.out.println(isValid("{[]}"));
        System.out.println(isValid("(]"));
        System.out.println(isValid("([)]"));
        System.out.println("===============");
        System.out.println(isValid("{{)}"));
    }
}
