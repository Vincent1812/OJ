package dataStructure.stack.luogu.p5788;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * 单调栈
 * https://www.luogu.com.cn/problem/P5788
 */
public class Main {
    static int n;
    static int[] ans;
    static Stack<Node> stack;

    public static void main(String[] args) {
        FastScanner sc = new FastScanner();
        n = sc.nextInt();
        ans = new int[n + 1];
        stack = new Stack<>();
        for (int i = 1; i <= n; i++) {
            int data = sc.nextInt();
            while (!stack.isEmpty() && stack.peek().value < data) {
                ans[stack.pop().idx] = i;
            }
            stack.push(new Node(i, data));
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            sb.append(ans[i]).append(" ");
        }
        sb.setLength(sb.length() - 1);
        System.out.println(sb);
    }

    static class Node {
        public int value;
        public int idx;

        public Node(int idx, int value) {
            this.value = value;
            this.idx = idx;
        }
    }

    static class FastScanner {
        StringTokenizer st = new StringTokenizer("");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

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
