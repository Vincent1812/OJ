package graph.shortestPath.spfa.luogu.p3385;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static long[] dis;
    static int m, n, t;
    static int[] cnt;
    static Map<Integer, Integer>[] forward;
    static Queue<Integer> queue;
    static boolean[] inQueue;
    static FastScanner sc;

    public static void main(String[] args) {
        sc = new FastScanner();
        t = sc.nextInt();
        for (int i = 0; i < t; i++) {
            if (judge()) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
    }

    private static boolean judge() {
        n = sc.nextInt();
        m = sc.nextInt();

        cnt = new int[n + 1];
        dis = new long[n + 1];
        for (int j = 2; j <= n; j++) {
            dis[j] = Long.MAX_VALUE;
        }
        forward = new Map[n + 1];
        for (int j = 1; j <= n; j++) {
            forward[j] = new HashMap<>();
        }
        queue = new LinkedList<>();
        inQueue = new boolean[n + 1];
        for (int j = 0; j < m; j++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int w = sc.nextInt();
            if (u > n || v > n || u < 1 || v < 1) {
                continue;
            }
            if (w >= 0) {
                addEdge(u, v, w);
                addEdge(v, u, w);
            } else {
                addEdge(u, v, w);
            }
        }
        queue.offer(1);
        while (!queue.isEmpty()) {
            Integer poll = queue.poll();
            inQueue[poll] = false;
            for (Map.Entry<Integer, Integer> entry : forward[poll].entrySet()) {
                int to = entry.getKey();
                int w = entry.getValue();
                if (dis[poll] + w >= dis[to]) {
                    continue;
                }
                dis[to] = dis[poll] + w;
                cnt[to] = cnt[poll] + 1;
                if (cnt[to] >= n) {
                    return true;
                }
                if (!inQueue[to]) {
                    inQueue[to] = true;
                    queue.offer(to);
                }
            }
        }
        return false;
    }

    private static void addEdge(int u, int v, int w) {
        if (forward[u].get(v) == null || forward[u].get(v) > w) {
            forward[u].put(v, w);
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
