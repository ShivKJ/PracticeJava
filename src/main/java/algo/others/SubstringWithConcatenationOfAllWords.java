package algo.others;

import static java.util.Collections.emptyList;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SubstringWithConcatenationOfAllWords {

    public static List<Integer> findSubstring(String s, String[] words) {
        if (s.isEmpty() || words.length == 0)
            return emptyList();

        int charCount = words[0].length() * words.length;

        if (s.length() < charCount)
            return emptyList();

        Map<String, Integer> wordSets = createMap(words);

        List<Integer> out = new LinkedList<>();

        for (int i = 0; i <= s.length() - charCount; i++)
            if (contains(s.substring(i, i + charCount), wordSets, words[0].length()))
                out.add(i);

        return out;
    }

    private static boolean contains(String s, Map<String, Integer> words, int n) {
        Map<String, Integer> subWords = new HashMap<>();

        for (int i = 0; i < s.length(); i += n) {
            String w = s.substring(i, i + n);
            Integer freq = words.get(w);

            if (freq == null)
                return false;

            if (freq < subWords.merge(w, 1, Integer::sum))
                return false;
        }

        return words.equals(subWords);
    }

    private static Map<String, Integer> createMap(String... strs) {
        Map<String, Integer> words = new HashMap<>();

        for (String string : strs)
            words.merge(string, 1, Integer::sum);

        return words;
    }

}
