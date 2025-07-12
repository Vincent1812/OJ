package graph.leftistTree.luogu.p3377;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static int m, n;
    static int[] lchild, rchild, fa, value, dis;

    public static void main(String[] args) {
        FastScanner sc = new FastScanner();
        n = sc.nextInt();
        m = sc.nextInt();
        lchild = new int[n + 1];
        rchild = new int[n + 1];
        fa = new int[n + 1];
        value = new int[n + 1];
        dis = new int[n + 1];
        dis[0] = -1;
        for (int i = 1; i <= n; i++) {
            value[i] = sc.nextInt();
            fa[i] = i;
        }
        for (int i = 0; i < m; i++) {
            if (1 == sc.nextInt()) {
                int a = sc.nextInt();
                int b = sc.nextInt();
                if (a > n || b > n || value[a] == -1 || value[b] == -1 || (a = getParent(a)) == (b = getParent(b))) {
                    continue;
                }
                /*if (a > b) {
                    int temp = a;
                    a = b;
                    b = temp;
                }*/
                fa[a] = fa[b] = merge(a, b);
            } else {
                int a = sc.nextInt();
                if (a > n) {
                    continue;
                }
                if (value[a] == -1) {
                    System.out.println(-1);
                } else {
                    a = getParent(a);
                    System.out.println(value[a]);
                    value[a] = -1;
                    fa[a] = fa[lchild[a]] = fa[rchild[a]] = merge(lchild[a], rchild[a]);
                }
            }
        }
    }

    static int merge(int x, int y) {
        if (x == 0 || y == 0) {
            return x + y;
        }
        if (value[x] > value[y]) {
            int temp = x;
            x = y;
            y = temp;
        }
        fa[y] = x;
        rchild[x] = merge(rchild[x], y);
        if (dis[rchild[x]] > dis[lchild[x]]) {
            int temp = rchild[x];
            rchild[x] = lchild[x];
            lchild[x] = temp;
        }
        dis[x] = dis[rchild[x]] + 1;
        return x;
    }

    static int getParent(int x) {
        if (x != fa[x]) {
            fa[x] = getParent(fa[x]);
        }
        return fa[x];
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