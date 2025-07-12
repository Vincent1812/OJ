package graph.minCycle.luogu.p6175;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int m, n, ans;
    static int[][] dis, w;
    static int INF = 1000000000;

    public static void main(String[] args) {
        FastScanner sc = new FastScanner();
        n = sc.nextInt();
        m = sc.nextInt();
        dis = new int[n + 1][];
        w = new int[n + 1][];
        for (int i = 1; i <= n; i++) {
            dis[i] = new int[n + 1];
            for (int j = 1; j <= n; j++) {
                dis[i][j] = INF;
            }
            w[i] = new int[n + 1];
            for (int j = 1; j <= n; j++) {
                w[i][j] = INF;
            }
        }
        for (int i = 0; i < m; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            dis[a][b] = dis[b][a] = w[a][b] = w[b][a] = sc.nextInt();
        }
        ans = Integer.MAX_VALUE;
        for (int k = 1; k <= n; k++) {
            for (int i = 1; i < k; i++) {
                for (int j = i + 1; j < k; ++j) {
                    if (notInf(dis[i][j]) && notInf(w[j][k]) && notInf(w[i][k]))
                        ans = Math.min(ans, dis[i][j] + w[j][k] + w[i][k]);
                }
            }
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    if (notInf(dis[i][k]) && notInf(dis[j][k]))
                        dis[i][j] = Math.min(dis[i][j], dis[i][k] + dis[j][k]);
                }
            }
        }
        if (ans == Integer.MAX_VALUE) {
            System.out.println("No solution.");
        } else {
            System.out.println(ans);
        }
    }

    private static boolean notInf(int i) {
        return i != INF;
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

