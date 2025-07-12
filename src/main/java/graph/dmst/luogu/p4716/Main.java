package graph.dmst.luogu.p4716;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 最小树形图 https://www.luogu.com.cn/problem/P4716
 */
public class Main {
    static int n, m, r, ans;
    static Node[] leftistTree;
    static int[] min, pre;


    public static void main(String[] args) {
        FastScanner sc = new FastScanner();
        n = sc.nextInt();
        m = sc.nextInt();
        r = sc.nextInt();
        leftistTree = new Node[n + 1];
        min = new int[n + 1];
        pre = new int[n + 1];
        for (int i = 0; i < m; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int w = sc.nextInt();
            leftistTree[v] = merge(leftistTree[v], new Node(u, v, w));
        }
        for (int i = 1; i <= n; i++) {
            if (i != r && leftistTree[i] != null) {
                System.out.println(-1);
                return;
            }
        }
        ans = 0;
        while (true) {
            ans = 0;
            Map<Integer, int[]> next = new HashMap<>();
            for (int i = 1; i <= n; i++) {
                if (leftistTree[i] != null) {
                    pushDown(leftistTree[i]);
                    next.put(leftistTree[i].from, new int[]{i, leftistTree[i].w});
                }
            }
            Stack<Integer> stack = new Stack<>();
            stack.push(r);
            while (!stack.isEmpty()) {

            }
        }
//        System.out.println(ans);
    }


    static Node merge(Node u, Node v) {
        if (u == null) {
            return v;
        }
        if (v == null) {
            return u;
        }
        if (u.tag != 0) {
            pushDown(u);
        }
        if (v.tag != 0) {
            pushDown(v);
        }
        if (u.w > v.w) {
            Node tmp = u;
            u = v;
            v = tmp;
        }
        u.right = merge(u.right, v);
        if (u.left == null || u.right != null && u.left.dist < u.right.dist) {
            Node tmp = u.left;
            u.left = u.right;
            u.right = tmp;
        }
        u.dist = u.right == null ? 1 : u.right.dist + 1;
        return u;
    }

    private static void pushDown(Node u) {
        if (u == null) {
            return;
        }
        u.w -= u.tag;
        if (u.left != null) {
            u.left.tag += u.tag;
        }
        if (u.right != null) {
            u.right.tag += u.tag;
        }
        u.tag = 0;
    }

    static class Node {
        public int from;
        public int to;
        public int tag;
        public int w;
        Node left, right;
        public int dist;

        public Node(int from, int to, int w) {
            this.to = to;
            this.from = from;
            this.tag = 0;
            this.w = w;
            this.dist = 0;
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
