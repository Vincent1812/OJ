package graph.sat2.luogu.p4782;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int n, m, ord, sccCnt;
    static Set<Integer>[] forward;
    static int[] order, low, scc;
    static Stack<Integer> stack;

    public static void main(String[] args) {
        FastScanner sc = new FastScanner();
        n = sc.nextInt();
        m = sc.nextInt();
        forward = new Set[2 * n + 1];
        for (int i = 1; i < forward.length; i++) {
            forward[i] = new HashSet<>();
        }
        low = new int[2 * n + 1];
        order = new int[2 * n + 1];
        scc = new int[2 * n + 1];
        sccCnt = 0;
        stack = new Stack<>();
        for (int k = 0; k < m; k++) {
            int i = sc.nextInt();
            int a = sc.nextInt();
            int j = sc.nextInt();
            int b = sc.nextInt();
            addEdge(notEqual(i, a), equal(j, b));
            addEdge(notEqual(j, b), equal(i, a));
        }
        for (int i = 1; i < forward.length; i++) {
            if (order[i] == 0) {
                tarjan(i);
            }
        }
        for (int i = 1; i <= n; i++) {
            if (scc[i] == scc[i + n]) {
                System.out.println("IMPOSSIBLE");
                return;
            }
        }
        System.out.println("POSSIBLE");
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            sb.append((scc[i] > scc[i + n] ? 1 : 0) + " ");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        System.out.println(sb);
    }

    private static void tarjan(int i) {
        order[i] = low[i] = ++ord;
        stack.push(i);
        for (Integer nx : forward[i]) {
            if (order[nx] == 0) {
                tarjan(nx);
                low[i] = Math.min(low[i], low[nx]);
            } else if (scc[nx] == 0) {
                low[i] = Math.min(low[i], low[nx]);
            }
        }
        if (low[i] == order[i]) {
            sccCnt++;
            while (low[stack.peek()] != order[stack.peek()]) {
                scc[stack.pop()] = sccCnt;
            }
            scc[stack.pop()] = sccCnt;
        }
    }

    private static void addEdge(int from, int to) {
        forward[from].add(to);
    }

    public static int equal(int i, int a) {
        return i + a * n;
    }

    public static int notEqual(int i, int a) {
        return i + (1 - a) * n;
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
