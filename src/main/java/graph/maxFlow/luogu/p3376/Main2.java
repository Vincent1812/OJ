package graph.maxFlow.luogu.p3376;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 最大流
 * EK算法
 * https://www.luogu.com.cn/problem/P3376
 */
public class Main2 {
    static long ans;
    static int n, m, s, t;
    static Map<Integer, Long>[] forward;
    static int[] pre;
    static long[] mf;

    public static void main(String[] args) {
        FastScanner sc = new FastScanner();
        n = sc.nextInt();
        forward = new Map[n + 1];
        for (int i = 1; i <= n; i++) {
            forward[i] = new HashMap<>();
        }
        pre = new int[n + 1];
        mf = new long[n + 1];
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
            ans += mf[t];
            int fr = pre[t];
            int to = t;
            while (to != s) {
                forward[fr].put(to, forward[fr].get(to) - mf[t]);
                if (forward[to].get(fr) == null) {
                    forward[to].put(fr, mf[t]);
                } else {
                    forward[to].put(fr, forward[to].get(fr) + mf[t]);
                }
                to = fr;
                fr = pre[fr];
            }
        }
        System.out.println(ans);
    }

    private static boolean bfs() {
        Arrays.fill(mf, 0);
        Arrays.fill(pre, 0);
        mf[s] = Integer.MAX_VALUE;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(s);
        while (!queue.isEmpty()) {
            Integer from = queue.poll();
            for (Map.Entry<Integer, Long> entry : forward[from].entrySet()) {
                int to = entry.getKey();
                long cap = entry.getValue();
                if (mf[to] != 0 || cap == 0) {
                    continue;
                }
                mf[to] = Math.min(mf[from], cap);
                pre[to] = from;
                if (to == t) {
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
