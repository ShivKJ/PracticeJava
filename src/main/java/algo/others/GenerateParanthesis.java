package algo.others;

import static java.lang.String.copyValueOf;

import java.util.LinkedList;
import java.util.List;

public class GenerateParanthesis {
    private static final char OPEN = '(', CLOSE = ')';

    public static List<String> generateParenthesis(int n) {
        List<String> out = new LinkedList<>();
        char[] holder = new char[2 * n];
        generate(holder, 0, 0, 0, n, out);
        return out;
    }

    private static void generate(char[] holder, int o, int c, int pos, int n, List<String> output) {
        if (pos == holder.length)
            output.add(copyValueOf(holder));
        else {
            if (o < n) {
                holder[pos] = OPEN;
                generate(holder, o + 1, c, pos + 1, n, output);
            }

            if (o > c) {
                holder[pos] = CLOSE;
                generate(holder, o, c + 1, pos + 1, n, output);
            }
        }
    }

    public static void main(String[] args) {
        generateParenthesis(3).forEach(System.out::println);
    }
}
