package algo.others;

public class LongestCommonPrefix {
    public static String longestCommonPrefix(String[] strs) {
        if (strs.length == 0)
            return "";

        String s = minLengthString(strs);

        for (int i = 0; i < s.length(); i++) {
            char c = strs[0].charAt(i);

            for (int j = 1; j < strs.length; j++)
                if (strs[j].charAt(i) != c)
                    return strs[0].substring(0, i);
        }

        return strs[0].substring(0, s.length());
    }

    private static String minLengthString(String[] strs) {
        String s = strs[0];

        for (String string : strs) {
            if (string.length() < s.length())
                s = string;
        }
        return s;
    }

    public static void main(String[] args) {
        String[] strs = { "abc", "ad", "ae", "aee" };

        System.out.println(longestCommonPrefix(strs));
    }
}
