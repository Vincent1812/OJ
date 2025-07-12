package graph.cut.luoguo.p3388;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 割点：tarjan算法
 * https://www.luogu.com.cn/problem/P3388
 */
public class Main {
    static int m, n, ord, root;
    static int[] order, low;
    static boolean[] cut;
    static Set<Integer>[] edge;

    public static void main(String[] args) {
        FastScanner sc = new FastScanner();
        n = sc.nextInt();
        m = sc.nextInt();
        order = new int[n + 1];
        low = new int[n + 1];
        cut = new boolean[n + 1];
        edge = new Set[n + 1];
        for (int i = 1; i <= n; i++) {
            edge[i] = new HashSet<>();
        }
        for (int i = 0; i < m; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            edge[a].add(b);
            edge[b].add(a);
        }
        ord = 0;
        for (int i = 1; i <= n; i++) {
            if (order[i] == 0) {
                root = i;
                dfs(i, i);
            }
        }
        int ans = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            if (cut[i]) {
                ans++;
                sb.append(i + " ");
            }
        }
        System.out.println(ans);
        if (ans > 0) {
            sb.setLength(sb.length() - 1);
        }
        System.out.println(sb);
    }

    private static void dfs(int u, int fa) {
        low[u] = order[u] = ++ord;
        System.out.println("u=" + u + ",fa=" + fa + ",ord = " + ord);
        int child = 0;
        for (Integer eg : edge[u]) {
            if (eg == fa) {
                continue;
            }
            if (order[eg] == 0) {
                dfs(eg, u);
                low[u] = Math.min(low[u], low[eg]);
                if (low[eg] >= order[u]) {
                    child++;
                    if (u != root || child >= 2) {
                        cut[u] = true;
                    }
                }
            } else {
                low[u] = Math.min(low[u], low[eg]);
            }
        }
    }

    static class FastScanner {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer("");

        public String next() {
            while (!st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return st.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }
    }
}
