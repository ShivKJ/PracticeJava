package algo.others;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class ReverseWords {
    public static String reverseWords(String s) {
        int chars = 0;

        LinkedList<String> strings = new LinkedList<>();

        try (Scanner scanner = new Scanner(s)) {
            while (scanner.hasNext()) {
                String out = scanner.next();
                chars += out.length() + 1;
                strings.add(out);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }

        StringBuilder builder = new StringBuilder(chars);
        Iterator<String> itr = strings.descendingIterator();

        while (itr.hasNext()) {
            builder.append(itr.next());
            if (itr.hasNext())
                builder.append(' ');
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        System.out.println(reverseWords("   A B!   C ").length());
        System.out.println(reverseWords("   A B!   C ").trim().length());
    }
}
