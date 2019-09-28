package algo.others;

import static java.util.stream.IntStream.range;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;

class MergeAccounts {

    public static List<List<String>> accountsMerge(List<List<String>> accounts) {

        UnionFind uf = new UnionFind(accounts.size());
        Map<String, Integer> emailToIdx = new HashMap<>();

        for (int i = 0; i < accounts.size(); i++) {

            Iterator<String> itr = accounts.get(i).iterator();
            itr.next();

            Function<String, Integer> initializer = initializeParent(i);

            while (itr.hasNext()) {
                int parent = emailToIdx.computeIfAbsent(itr.next(), initializer);

                if (parent != i)
                    uf.connect(i, parent);

            }
        }

        Map<Integer, SortedSet<String>> groups = new HashMap<>();

        for (int i = 0; i < uf.parent.length; i++) {
            List<String> data = accounts.get(i);
            groups.computeIfAbsent(uf.root(i), e -> new TreeSet<>()).addAll(data.subList(1, data.size()));
        }

        List<List<String>> output = new ArrayList<List<String>>(groups.size());

        for (Entry<Integer, SortedSet<String>> e : groups.entrySet()) {
            List<String> data = new ArrayList<>(1 + e.getValue().size());

            data.add(accounts.get(e.getKey()).get(0));
            data.addAll(e.getValue());

            output.add(data);

        }

        return output;
    }

    private static Function<String, Integer> initializeParent(int i) {
        return e -> i;
    }

    private static final class UnionFind {
        /**
         * Storing parent of i^th node.
         */
        final int[] parent;

        UnionFind(int n) {
            this.parent = range(0, n).toArray();
        }

        /**
         * connects the two node a and b.
         * 
         * It makes parent of root node of b to parent of a
         * 
         * @param a
         * @param b
         */
        void connect(int a, int b) {
            parent[root(b)] = parent[a];
        }

        /**
         * finds root of node a. A node is root if it is parent of itself.
         * 
         * @param a
         * @return
         */
        int root(int a) {
            while (a != parent[a]) {
                parent[a] = parent[parent[a]];
                a = parent[a];
            }

            return a;
        }

    }

}
