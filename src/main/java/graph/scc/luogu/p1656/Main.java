package graph.scc.luogu.p1656;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 强连同分量：tarjan算法
 * https://www.luogu.com.cn/problem/P1656
 */
public class Main {
    static int m, n, ord;
    static Map<Integer, List<Integer>> forward;
    static int[] order, first;
    static boolean[] inStack;
    static Stack<Integer> stack;
    static PriorityQueue<int[]> queue;
    static int[] fa;

    public static void main(String[] args) {
        FastScanner sc = new FastScanner();
        n = sc.nextInt();
        m = sc.nextInt();
        ord = 0;
        inStack = new boolean[n + 1];
        order = new int[n + 1];
        first = new int[n + 1];
        queue = new PriorityQueue<>((a, b) -> {
            if (a[0] != b[0]) {
                return a[0] - b[0];
            }
            return a[1] - b[1];
        });
        stack = new Stack<>();
        forward = new HashMap<>();
        for (int i = 0; i < m; i++) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            forward.putIfAbsent(from, new ArrayList<>());
            forward.putIfAbsent(to, new ArrayList<>());
            forward.get(from).add(to);
            forward.get(to).add(from);
        }
        dfs(1, 0);
        while (!queue.isEmpty()) {
            int[] poll = queue.poll();
            System.out.println(poll[0] + " " + poll[1]);
        }
    }

    private static void dfs(int i, int f) {
        first[i] = order[i] = ++ord;
        stack.push(i);
        inStack[i] = true;
        for (Integer to : forward.get(i)) {
            if (to == f) {
                continue;
            }
            if (order[to] == 0) {
                dfs(to, i);
                if (first[i] > first[to]) {
                    first[i] = first[to];
                }
            } else if (inStack[to]) {
                if (first[i] > first[to]) {
                    first[i] = first[to];
                }
            }
        }
        if (order[i] == first[i]) {
//            System.out.println("i=" + i + ",stack=" + stack);
            while (stack.peek() != i) {
                inStack[stack.pop()] = false;
            }
            inStack[stack.pop()] = false;
            if (!stack.isEmpty()) {
                if (i > f) {
                    int tmp = i;
                    i = f;
                    f = tmp;
                }
                queue.offer(new int[]{i, f});
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
