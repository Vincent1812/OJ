package graph.treeDivide.luogu.p3806;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static List<Node>[] edge;//边信息
    static int[] ask;//查询数组
    static boolean[] ans;//答案数组
    static int root;//树的重心
    static boolean[] del;//是否已经计算过，经过该顶点的线
    static int min;//计算重心
    static int m, n;
    static Set<Integer> judge;//其他子树是否包含这个长度
    static Set<Integer> dis;//当前子树包含的长度

    public static void main(String[] args) {
        FastScanner sc = new FastScanner();
        n = sc.nextInt();
        m = sc.nextInt();
        edge = new List[n + 1];
        del = new boolean[n + 1];
        ask = new int[m];
        ans = new boolean[m];
        judge = new HashSet<>();
        dis = new HashSet<>();
        for (int i = 0; i < n - 1; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int w = sc.nextInt();
            if (edge[u] == null) {
                edge[u] = new ArrayList<>();
            }
            if (edge[v] == null) {
                edge[v] = new ArrayList<>();
            }
            edge[u].add(new Node(v, w));
            edge[v].add(new Node(u, w));
        }
        for (int i = 0; i < m; i++) {
            ask[i] = sc.nextInt();
        }
        min = Integer.MAX_VALUE;
        getRoot(1, 1);
//        System.out.println("getRoot, u = " + 1 + ",pa = " + 1 + ",root = " + root);
        divide(root, 1);
        for (boolean an : ans) {
            if (an) {
                System.out.println("AYE");
            } else {
                System.out.println("NAY");
            }
        }
    }

    private static int getRoot(int u, int pa) {
        int sum = 1;
        int max = 0;
        for (Node node : edge[u]) {
            if (node.v == pa || del[node.v]) {
                continue;
            }
            int sub = getRoot(node.v, u);
            if (sub > max) {
                max = sub;
            }
            sum += sub;
        }
        max = Math.max(max, n - sum);
        if (min >= max) {
            min = max;
            root = u;
        }
        return sum;
    }

    private static void divide(int u, int pa) {
        calc(u, pa);
        for (Node node : edge[u]) {
            if (node.v == pa || del[node.v]) {
                continue;
            }
            min = Integer.MAX_VALUE;
            getRoot(node.v, u);
//            System.out.println("getRoot, u = " + node.v + ",pa = " + u + ",root = " + root);
            divide(root, u);
        }
    }

    private static void calc(int u, int pa) {
        del[u] = true;
        for (Node node : edge[u]) {
            if (node.v == pa || del[node.v]) {
                continue;
            }
            dis.add(0);
            dfsDis(node.v, u, node.dis);
            for (Integer dt : dis) {
                for (int i = 0; i < ask.length; i++) {
                    if (ask[i] >= dt && !ans[i]) {
                        ans[i] = judge.contains(ask[i] - dt) | ask[i] == dt;
                    }
                }
            }
            judge.addAll(dis);
            dis.clear();
        }
//        System.out.println("judge = " + judge);
        judge.clear();
    }

    private static void dfsDis(int u, int pa, int dt) {
        if (dt > 10000000) {
            return;
        }
        dis.add(dt);
        for (Node node : edge[u]) {
            if (node.v == pa || del[node.v]) {
                continue;
            }
            dfsDis(node.v, u, dt + node.dis);
        }
    }

    static class Node {
        public Node(int v, int dis) {
            this.v = v;
            this.dis = dis;
        }

        public int v;
        public int dis;
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
