package graph.bigraphWeightMatch.p6577;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 最大权匹配
 * KM算法
 * https://www.luogu.com.cn/problem/P6577
 */
public class Main {
    static int m, n;
    static long ans;
    static Map<Integer, Integer>[] edge;
    static boolean[] li, ri;
    static long[] delta;
    static long[] lv, rv;
    static int[] match;

    public static void main(String[] args) {
        FastScanner sc = new FastScanner();
        n = sc.nextInt();
        m = sc.nextInt();
        li = new boolean[n + 1];
        ri = new boolean[n + 1];
        lv = new long[n + 1];
        rv = new long[n + 1];
        delta = new long[n + 1];
        edge = new Map[n + 1];
        match = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            edge[i] = new HashMap<>();
            lv[i] = Long.MIN_VALUE;
        }
        for (int i = 0; i < m; i++) {
            int l = sc.nextInt();
            int r = sc.nextInt();
            int w = sc.nextInt();
            edge[l].put(r, w);
        }
        for (int i = 1; i <= n; i++) {
            for (Integer r : edge[i].values()) {
                lv[i] = Math.max(lv[i], r);
            }
        }
        for (int i = 1; i <= n; i++) {
            while (true) {
                Arrays.fill(li, false);
                Arrays.fill(ri, false);
                Arrays.fill(delta, Long.MAX_VALUE);
                if (dfs(i)) {
                    break;
                }
                long min = Long.MAX_VALUE;
                for (int j = 1; j <= n; j++) {
                    if (!ri[j]) {
                        min = Math.min(min, delta[j]);
                    }
                }
                for (int j = 1; j <= n; j++) {
                    if (li[j]) {
                        lv[j] -= min;
                    }
                    if (ri[j]) {
                        rv[j] += min;
                    }
                }
            }
        }
        ans = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            ans += edge[match[i]].get(i);
            sb.append(match[i]).append(" ");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        System.out.println(ans);
        System.out.println(sb);
    }

    private static boolean dfs(int l) {
        li[l] = true;
        for (Map.Entry<Integer, Integer> entry : edge[l].entrySet()) {
            int r = entry.getKey();
            int w = entry.getValue();
            if (ri[r]) {
                continue;
            }
            if (lv[l] + rv[r] == w) {
                ri[r] = true;
                if (match[r] == 0 || dfs(match[r])) {
                    match[r] = l;
                    return true;
                }
            } else {
                delta[r] = Math.min(lv[l] + rv[r] - w, delta[r]);
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
