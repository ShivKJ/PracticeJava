package algo.tree;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Tries {
    private static final TriNode SENTINAL = new TriNode();

    private final TriNode root;

    private Tries() {
        this.root = new TriNode();
    }

    public static Tries create(Collection<? extends CharSequence> strs) {
        Tries tries = new Tries();

        strs.forEach(s -> update(tries.root, s));

        return tries;
    }

    public boolean hasPrefix(CharSequence cs) {
        return lastOne(cs) != SENTINAL;
    }

    public boolean constains(CharSequence cs) {
        return lastOne(cs).endOfWord;
    }

    private TriNode lastOne(CharSequence cs) {
        TriNode n = root;

        for (int i = 0; i < cs.length(); i++)
            if ((n = n.orGetSentinal(cs.charAt(i))) == SENTINAL)
                break;

        return n;

    }

    private static void update(TriNode n, CharSequence s) {
        for (int i = 0; i < s.length(); i++)
            n = n.ifNotCreate(s.charAt(i));

        n.endOfWord = true;
    }

    public boolean delete(TriNode p, CharSequence s, int index) {
        //TODO
        Character c = s.charAt(index);
        TriNode n = p.orGetSentinal(c);
        if (n != SENTINAL) {
            if (s.length() == index + 1) {
                if (n.noChild())
                    p.mapping.remove(c);
                p.endOfWord = false;
            } else
                delete(n, s, index + 1);
        }

        return false;

    }

    public static final class TriNode {
        private final Map<Character, TriNode> mapping;
        private boolean                       endOfWord;

        public TriNode() {
            this.mapping = new HashMap<>();
            this.endOfWord = false;
        }

        public boolean hasChild(Character c) {
            return this.mapping.containsKey(c);
        }

        public boolean noChild() {
            return mapping.isEmpty();
        }

        public TriNode createChild(Character c) {
            TriNode n = new TriNode();
            this.mapping.put(c, n);

            return n;
        }

        public TriNode ifNotCreate(Character c) {
            return mapping.computeIfAbsent(c, e -> new TriNode());
        }

        public TriNode orGetSentinal(Character c) {
            return mapping.getOrDefault(c, SENTINAL);
        }

        @Override
        public String toString() {
            return "<endOfWord=" + endOfWord + " mapping=" + mapping + ">";
        }
    }
}
