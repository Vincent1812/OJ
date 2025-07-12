package graph.dynamicTreeDivide.p6329;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static class FastScanner {
        StringTokenizer st = new StringTokenizer("");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

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

    static class Lca {
        static List<Integer>[] edge;
        static int[] depth;
        static int[] fa;
        static int[] top;
        static int[] son;

        public static void init(int n, int root) {
            depth = new int[n + 1];
            fa = new int[n + 1];
            top = new int[n + 1];
            son = new int[n + 1];
            dfs1(root, 0);
            dfs2(root, root);
        }

        private static void dfs2(int u, int t) {
            top[u] = t;
            for (Integer child : edge[u]) {
                if (child == t || child == son[u]) {
                    continue;
                }
                dfs2(child, child);
            }
            dfs2(son[u], t);
        }

        private static int dfs1(int u, int p) {
            depth[u] = depth[p] + 1;
            fa[u] = p;
            int max = 0;
            int sum = 1;
            for (Integer child : edge[u]) {
                if (child == p) {
                    continue;
                }
                int tmp = dfs1(child, u);
                if (tmp > max) {
                    son[u] = child;
                    max = tmp;
                }
                sum += tmp;
            }
            return sum;
        }

        public static int getLca(int x, int y) {
            while (top[x] != top[y]) {
                if (depth[x] < depth[y]) {
                    int tmp = x;
                    x = y;
                    y = tmp;
                }
                x = fa[top[x]];
            }
            return depth[x] > depth[y] ? y : x;
        }

        public static int getDis(int x, int y) {
            int l = getLca(x, y);
            if (l == x || l == y) {
                return Math.abs(depth[x] - depth[y]);
            }
            return depth[x] - depth[l] + depth[y] - depth[l];
        }
    }

    static class PointTree {
        static List<Integer>[] edge;
        static int root, max = 0;
        static boolean[] del;
        static int treeN;

        static void init(int n) {
            treeN = n;
            edge = new List[n + 1];
            del = new boolean[n + 1];
            root = findwc(1, 0);
        }

        private static int findwc(int u, int fa) {
            treeN = dfsCount(u, fa);
            max = 0;
            dfs(u, fa);
            return -1;
        }

        private static int dfsCount(int u, int fa) {
            int sum = 0;
            for (Integer child : Lca.edge[u]) {
                if (child == fa || del[child]) {
                    continue;
                }
                sum += dfsCount(child, u);
            }
            return sum;
        }

        private static int dfs(int u, int fa) {
            int subMax = 0;
            int sum = 1;
            for (Integer child : Lca.edge[u]) {
                if (del[child] || child == fa) {
                    continue;
                }
                int tmp = dfs(child, u);
                sum += tmp;
                if (tmp > subMax) {
                    subMax = tmp;
                }
            }
            subMax = Math.max(subMax, treeN - sum);
            if (subMax > max) {
                root = u;
            }
            return sum;
        }
    }

    static int[] value;
    static int n, m;

    public static void main(String[] args) {
        FastScanner sc = new FastScanner();
        n = sc.nextInt();
        m = sc.nextInt();
        value = new int[n + 1];
        Lca.edge = new List[n];
        for (int i = 1; i <= n; i++) {
            value[i] = sc.nextInt();
        }
        for (int i = 0; i < n - 1; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            if (Lca.edge[a] == null) {
                Lca.edge[a] = new ArrayList<>();
            }
            if (Lca.edge[b] == null) {
                Lca.edge[b] = new ArrayList<>();
            }
            Lca.edge[a].add(b);
            Lca.edge[b].add(a);
        }
        for (int i = 0; i < m; i++) {

        }
    }
}
