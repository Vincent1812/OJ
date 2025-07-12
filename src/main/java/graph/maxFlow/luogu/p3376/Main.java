package graph.maxFlow.luogu.p3376;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 最大流
 * Dinic算法
 * https://www.luogu.com.cn/problem/P3376
 */
public class Main {
    static long ans;
    static int n, m, s, t;
    static Map<Integer, Long>[] forward;
    static int[] d;

    public static void main(String[] args) {
        FastScanner sc = new FastScanner();
        n = sc.nextInt();
        forward = new Map[n + 1];
        for (int i = 1; i <= n; i++) {
            forward[i] = new HashMap<>();
        }
        d = new int[n + 1];
        m = sc.nextInt();
        s = sc.nextInt();
        t = sc.nextInt();
        for (int i = 0; i < m; i++) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            long cap = sc.nextInt();
            if (forward[from].get(to) == null) {
                forward[from].put(to, cap);
            } else {
                forward[from].put(to, cap + forward[from].get(to));
            }
        }
        ans = 0L;
        while (bfs()) {
            ans += dfs(s, Long.MAX_VALUE);
        }
        System.out.println(ans);
    }

    private static long dfs(int u, long mf) {
        if (u == t) {
            return mf;
        }
        long sum = 0;
        Map<Integer, Long> substract = new HashMap<>();
        for (Map.Entry<Integer, Long> entry : forward[u].entrySet()) {
            int to = entry.getKey();
            long cap = entry.getValue();
            if (d[to] != d[u] + 1 || cap == 0) {
                continue;
            }
            long sub = dfs(to, Math.min(cap, mf));
            sum += sub;
            mf -= sub;
            substract.put(to, sub);
            if (forward[to].get(u) == null) {
                forward[to].put(u, sub);
            } else {
                forward[to].put(u, forward[to].get(u) + sub);
            }
            if (mf == 0) {
                break;
            }
        }
        for (Map.Entry<Integer, Long> entry : substract.entrySet()) {
            forward[u].put(entry.getKey(), forward[u].get(entry.getKey()) - entry.getValue());
        }
        if (sum == 0) {
            d[u] = 0;
        }
        return sum;
    }

    private static boolean bfs() {
        Arrays.fill(d, 0);
        d[s] = 1;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(s);
        while (!queue.isEmpty()) {
            Integer poll = queue.poll();
            for (Integer nx : forward[poll].keySet()) {
                if (d[nx] == 0 && forward[poll].get(nx) != 0) {
                    d[nx] = d[poll] + 1;
                    if (nx == t) {
                        return true;
                    }
                    queue.offer(nx);
                }
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
