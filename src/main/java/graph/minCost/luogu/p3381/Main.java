package graph.minCost.luogu.p3381;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 最大流最小费用
 * EK算法+spfa
 * https://www.luogu.com.cn/problem/P3381
 */
public class Main {
    static int m, n, s, t, flow, cost;
    static int[] pre, mf;
    static long[] d;
    static Map<Integer, Node>[] forward;
    static boolean[] visited;

    public static void main(String[] args) {
        FastScanner sc = new FastScanner();
        n = sc.nextInt();
        m = sc.nextInt();
        s = sc.nextInt();
        t = sc.nextInt();
        flow = 0;
        cost = 0;
        pre = new int[n + 1];
        d = new long[n + 1];
        mf = new int[n + 1];
        forward = new Map[n + 1];
        visited = new boolean[n + 1];
        for (int i = 1; i <= n; i++) {
            forward[i] = new HashMap<>();
        }
        int u, v, d, c;
        for (int i = 0; i < m; i++) {
            u = sc.nextInt();
            v = sc.nextInt();
            c = sc.nextInt();
            d = sc.nextInt();
            forward[u].put(v, new Node(c, d));
        }
        while (spfa()) {
//            System.out.println("mf[t]=" + mf[t]);
            int fr = pre[t];
            int to = t;
            flow += mf[t];
//            System.out.println("pre = " + Arrays.toString(pre));
            while (true) {
//                System.out.println("fr = " + fr + ",to=" + to);
                cost += mf[t] * forward[fr].get(to).d;
                forward[fr].get(to).cap -= mf[t];
                if (forward[to].get(fr) != null) {
                    forward[to].get(fr).cap += mf[t];
                } else {
                    forward[to].put(fr, new Node(mf[t], -forward[fr].get(to).d));
                }
                if (fr == s) {
                    break;
                }
                to = fr;
                fr = pre[fr];
            }
        }
        System.out.println(flow + " " + cost);
    }

    private static boolean spfa() {
        Arrays.fill(mf, 0);
        Arrays.fill(visited, false);
        Arrays.fill(d, Long.MAX_VALUE);
        Arrays.fill(pre, 0);
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(s);
        visited[s] = true;
        d[s] = 0;
        mf[s] = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            Integer poll = queue.poll();
            visited[poll] = false;
            for (Integer to : forward[poll].keySet()) {
                if (visited[to]) {
                    continue;
                }
                Node node = forward[poll].get(to);
                if (node.cap > 0 && d[to] > node.d + d[poll]) {
                    d[to] = node.d + d[poll];
                    mf[to] = Math.min(node.cap, mf[poll]);
                    pre[to] = poll;
                    if (to != t) {
                        visited[to] = true;
                        queue.offer(to);
                    }
                }
            }
        }
        return mf[t] > 0;
    }

    static class Node {
        public int cap;
        public long d;

        public Node(int cap, long d) {
            this.cap = cap;
            this.d = d;
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
