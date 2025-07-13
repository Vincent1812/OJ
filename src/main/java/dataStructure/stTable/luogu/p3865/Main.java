package dataStructure.stTable.luogu.p3865;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * RMQ（区间最大/最小值查询）问题
 * ST表
 * https://www.luogu.com.cn/problem/P3865
 */
public class Main {
    static int[][] st;
    static int n, m, l, r, k, logn, len;

    public static void main(String[] args) {
        FastScanner sc = new FastScanner();
        n = sc.nextInt();
        m = sc.nextInt();
        st = new int[n + 1][];
        logn = (int) Math.sqrt(n);
        for (int i = 1; i <= n; i++) {
            st[i] = new int[logn + 1];
            st[i][0] = sc.nextInt();
        }
        for (int j = 1; j <= logn; ++j) {
            len = 1 << j;
            for (int i = 1; i + len - 1 <= n; i++) {
                st[i][j] = Math.max(st[i][j - 1], st[i + len / 2][j - 1]);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            l = sc.nextInt();
            r = sc.nextInt();
            k = (int) Math.sqrt(r - l + 1);
            len = 1 << k;
            sb.append(Math.max(st[l][k], st[r - len + 1][k])).append("\n");
        }
        sb.setLength(sb.length() - 1);
        System.out.println(sb);
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
