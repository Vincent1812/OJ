package graph.treeHeuristicCombine.q600e;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static class FastScanner {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer("");

        // 读取字符串
        String next() {
            while (!st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        // 读取输入的数字
        int nextInt() {
            return Integer.parseInt(next());
        }
    }

    static int n;
    static Set<Integer> treeColorSet = new HashSet<>();
    static int[] son;
    static Map<Integer, List<Integer>> edge;
    static int[] cnt;
    static long[] ans;
    static int[] color;
    static int max = 0;

    public static void main(String[] args) throws IOException {
        FastScanner sc = new FastScanner();
        n = sc.nextInt();
        son = new int[n + 1];
        cnt = new int[n + 1];
        ans = new long[n + 1];
        color = new int[n + 1];
        edge = new HashMap<>();
        for (int i = 1; i < n + 1; ++i) {
            color[i] = sc.nextInt();
        }
        for (int i = 1; i < n; ++i) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            edge.computeIfAbsent(a, k -> new ArrayList<>());
            edge.computeIfAbsent(b, k -> new ArrayList<>());
            edge.get(a).add(b);
            edge.get(b).add(a);
        }
        dfs1(1, 0);
        dfs2(1, 0, false);
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < n; ++i) {
            sb.append(ans[i] + " ");
        }
        sb.append(ans[n]);
        System.out.println(sb);
    }

    private static void dfs2(int u, int parent, boolean sub) {
        for (Integer neighbor : edge.get(u)) {
            if (neighbor == parent || neighbor == son[u]) {
                continue;
            }
            dfs2(neighbor, u, true);
        }
        if (son[u] != 0) {
            dfs2(son[u], u, false);
        }
        add(u, parent, son[u]);
        for (Integer cl : treeColorSet) {
            if (cnt[cl] == max) {
                ans[u] += cl;
            }
        }
        if (sub) {
            sub(u, parent);
        }
    }

    private static void sub(int u, int parent) {
        max = 0;
        for (Integer cl : treeColorSet) {
            cnt[cl] = 0;
        }
        treeColorSet.clear();
    }

    private static void add(int u, int parent, int son) {
        System.out.println("add u=" + u + ",parent=" + parent + ",son=" + son);
        ++cnt[color[u]];
        if (cnt[color[u]] > max) {
            max = cnt[color[u]];
        }
        treeColorSet.add(color[u]);
        for (Integer neighbor : edge.get(u)) {
            if (neighbor == parent || neighbor == son) {
                continue;
            }
            add(neighbor, u, 0);
        }
    }

    private static int dfs1(int u, int parent) {
        int max = 0;
        int resp = 1;
        for (Integer neighbor : edge.get(u)) {
            if (neighbor == parent) {
                continue;
            }
            int subTotal = dfs1(neighbor, u);
            if (max < subTotal) {
                son[u] = neighbor;
                max = subTotal;
            }
            resp += subTotal;
        }
        return resp;
    }
}