package algo.others;

import static java.lang.Boolean.TRUE;
import static java.util.Objects.isNull;

import java.util.Arrays;

public class IsBipartite {

    public static boolean isBipartite(int[][] graph) {

        Boolean[] color = new Boolean[graph.length];

        for (int u = 0; u < color.length; u++)
            if (isNull(color[u])) {
                color[u] = TRUE;

                if (!dfs(color, graph, u))
                    return false;
            }

        return true;
    }

    private static boolean dfs(Boolean[] color, int[][] graph, int u) {
        for (int v : graph[u])
            if (isNull(color[v])) {
                color[v] = !color[u];
                
                if (!dfs(color, graph, v))
                    return false;
            } else if (color[v] == color[u])
                return false;

        return true;
    }

    public static void main(String[] args) {
        int[][] graph = { { 3 }, { 2, 4 }, { 1 }, { 0, 4 }, { 1, 3 } };
        for (int i = 0; i < graph.length; i++)
            System.out.println(i + " : " + Arrays.toString(graph[i]));
        System.out.println(isBipartite(graph));
    }
}
