package graph.minCut.luogu.p1344;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 最小流
 * Dinic算法
 * https://www.luogu.com.cn/problem/P1344
 */
public class Main {
    static long ans1, ans2;
    static int[] d;
    static int m, n;

    public static void main(String[] args) {
        FastScanner sc = new FastScanner();
        n = sc.nextInt();
        m = sc.nextInt();
        Map<Integer, Long>[] forward = new Map[n + 1];
        Map<Integer, Long>[] forward2 = new Map[n + 1];
        for (int i = 1; i <= n; i++) {
            forward[i] = new HashMap<>();
            forward2[i] = new HashMap<>();
        }
        d = new int[n + 1];
        for (int i = 0; i < m; i++) {
            int fr = sc.nextInt();
            int to = sc.nextInt();
            long cap = sc.nextInt();
            if (forward[fr].get(to) == null) {
                forward[fr].put(to, cap);
                forward2[fr].put(to, 1L);
            } else {
                forward[fr].put(to, forward[fr].get(to) + cap);
                forward2[fr].put(to, forward2[fr].get(to) + 1);
            }
        }
        ans1 = 0;
        while (bfs(forward)) {
            ans1 += dfs(1, Long.MAX_VALUE, forward);
        }
        for (int i = 1; i <= n; i++) {
            for (Map.Entry<Integer, Long> entry : forward[i].entrySet()) {
                int to = entry.getKey();
                long cap = entry.getValue();
                if (forward[i].get(to) == null) {
                    continue;
                }
                if (cap > 0) {
                    forward2[i].put(to, Long.MAX_VALUE);
                }
            }

        }
        ans2 = 0;
        while (bfs(forward2)) {
            ans2 += dfs(1, Long.MAX_VALUE, forward2);
        }
        System.out.println(ans1 + " " + ans2);
    }

    private static long dfs(int u, long mf, Map<Integer, Long>[] forward) {
        if (u == n) {
            return mf;
        }
        int sum = 0;
        Map<Integer, Long> substract = new HashMap<>();
        for (Integer to : forward[u].keySet()) {
            if (d[to] != d[u] + 1 || forward[u].get(to) == 0) {
                continue;
            }
            long sub = dfs(to, Math.min(forward[u].get(to), mf), forward);
            mf -= sub;
            substract.put(to, sub);
            sum += sub;
            if (mf == 0) {
                break;
            }
        }
        for (Map.Entry<Integer, Long> entry : substract.entrySet()) {
            int to = entry.getKey();
            long sub = entry.getValue();
            forward[u].put(to, forward[u].get(to) - sub);
        }

        if (sum == 0) {
            d[u] = 0;
        }
        return sum;
    }

    private static boolean bfs(Map<Integer, Long>[] forward) {
        Arrays.fill(d, 0);
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);
        d[1] = 1;
        while (!queue.isEmpty()) {
            Integer poll = queue.poll();
            for (Integer to : forward[poll].keySet()) {
                if (d[to] != 0 || forward[poll].get(to) == 0) {
                    continue;
                }
                d[to] = d[poll] + 1;
                if (to == n) {
                    return true;
                }
                queue.offer(to);
            }
        }
        return false;
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
