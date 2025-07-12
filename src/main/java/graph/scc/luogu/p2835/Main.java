package graph.scc.luogu.p2835;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 强连同分量：tarjan算法
 * https://www.luogu.com.cn/problem/P2835
 */
public class Main {
    static int n, ord, ans;
    static List<Integer>[] forward;
    static List<Integer>[] backward;
    static Stack<Integer> stack;
    static int[] order, low;
    static boolean[] inStack;

    public static void main(String[] args) {
        FastScanner sc = new FastScanner();
        n = sc.nextInt();
        forward = new List[n + 1];
        backward = new List[n + 1];
        order = new int[n + 1];
        low = new int[n + 1];
        inStack = new boolean[n + 1];
        ord = 0;
        stack = new Stack<>();

        for (int i = 0; i < n; i++) {
            List<Integer> next = new ArrayList<>();
            int nx;
            while ((nx = sc.nextInt()) != 0) {
                next.add(nx);
                if (backward[nx] == null) {
                    backward[nx] = new ArrayList<>();
                }
                backward[nx].add(i + 1);
            }
            forward[i + 1] = next;
        }
        ans = 0;
        for (int i = 1; i <= n; i++) {
            if (order[i] == 0) {
                dfs(i);
            }
        }
        System.out.println(ans);
    }

    private static void dfs(int u) {
        stack.push(u);
        inStack[u] = true;
        low[u] = order[u] = ++ord;
        for (Integer fw : forward[u]) {
            if (order[fw] == 0) {
                dfs(fw);
                low[u] = Math.min(low[u], low[fw]);
            } else if (inStack[fw]) {
                low[u] = Math.min(low[u], low[fw]);
            }
        }
        if (low[u] == order[u]) {
            Set<Integer> set = new HashSet<>();
            while (low[stack.peek()] != order[stack.peek()]) {
                int pop = stack.pop();
                set.add(pop);
                inStack[pop] = false;
            }
            if (!stack.isEmpty()) {
                int pop = stack.pop();
                set.add(pop);
                inStack[pop] = false;
            }
            boolean find = false;
            for (Integer to : set) {
                if (backward[to] == null) {
                    continue;
                }
                for (Integer from : backward[to]) {
                    if (!set.contains(from)) {
                        find = true;
                        break;
                    }
                }
                if (find) {
                    break;
                }
            }
            if (!find) {
                ans++;
            }
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
