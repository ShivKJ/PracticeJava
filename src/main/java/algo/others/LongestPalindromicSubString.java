package algo.others;

import static java.lang.Integer.compare;
import static java.util.Arrays.copyOfRange;

import java.util.stream.IntStream;

public final class LongestPalindromicSubString {
    /**
     * To find largest palendromic in a String
     * in string "abcdcdz", largest palendromic string is "cdc"
     * and in "abcddc" it is "cddc".
     * 
     * @param string
     * @return
     */
    public static String longestPalindrome(String string) {
        return string.isEmpty() ? string : search(string.toCharArray());
    }

    private static String search(char[] arr) {
        return IntStream.range(0, arr.length)
                        .mapToObj(i -> largestPelindromeAround(arr, i))
                        .max(Range::compareTo)
                        .map(r -> copyOfRange(arr, r.startIdx, r.endIdx + 1))
                        .map(String::copyValueOf)
                        .get();

    }

    private static class Range implements Comparable<Range> {
        private final int startIdx, endIdx;

        Range(int startIdx, int endIdx) {
            this.startIdx = startIdx;
            this.endIdx = endIdx;
        }

        @Override
        public int compareTo(Range o) {
            return compare(endIdx - startIdx, o.endIdx - o.startIdx);
        }

    }

    /**
     * Finding largest palindromic string having its middle
     * on index idx.
     * 
     * Usually palindromic string is of odd length
     * but in the case of even length, two middle element
     * will be same.
     * 
     * @param str
     * @param idx
     * @return
     */
    private static Range largestPelindromeAround(char[] str, int idx) {
        Range a = pelindromeExpansion(str, idx, idx);

        if (idx < str.length - 1 && str[idx] == str[idx + 1]) {
            Range b = pelindromeExpansion(str, idx, idx + 1);

            if (b.compareTo(a) > 0)
                a = b;

        }

        return a;
    }

    /**
     * Finding left and right index of palindromic string having 
     * middle index "left" and "right". 
     * 
     * @param arr
     * @param left
     * @param right
     * @return
     */
    private static Range pelindromeExpansion(char[] arr, int left, int right) {
        int n = arr.length;

        while (left > 0 && right < n - 1 && arr[left - 1] == arr[right + 1]) {
            left--;
            right++;
        }

        return new Range(left, right);
    }

    public static void main(String[] args) {
        System.out.println(longestPalindrome("babad"));
        System.out.println(longestPalindrome("aa"));
        System.out.println(longestPalindrome("a"));
        System.out.println(longestPalindrome("abcddcsz"));
    }
}
