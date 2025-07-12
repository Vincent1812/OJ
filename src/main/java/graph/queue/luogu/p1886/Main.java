package graph.queue.luogu.p1886;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 问题：滑动窗口最值
 * 解决方案：单调队列
 * https://www.luogu.com.cn/problem/P1886
 */
public class Main {
    static int n, k;
    static Deque<Node> minQueue, maxQueue;

    public static void main(String[] args) {
        FastScanner sc = new FastScanner();
        n = sc.nextInt();
        k = sc.nextInt();
        minQueue = new LinkedList<>();
        maxQueue = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int value = sc.nextInt();
            while (!minQueue.isEmpty() && value < minQueue.peekLast().value) {
                minQueue.pollLast();
            }
            minQueue.offer(new Node(i, value));
            if (i - minQueue.peek().idx >= k) {
                minQueue.poll();
            }
            while (!maxQueue.isEmpty() && value > maxQueue.peekLast().value) {
                maxQueue.pollLast();
            }
            maxQueue.offer(new Node(i, value));
            if (i - maxQueue.peek().idx >= k) {
                maxQueue.poll();
            }
            if (i >= k - 1) {
                sb.append(minQueue.peek().value).append(" ");
                sb2.append(maxQueue.peek().value).append(" ");
            }
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        if (sb2.length() > 0) {
            sb2.setLength(sb2.length() - 1);
        }
        System.out.println(sb);
        System.out.println(sb2);
    }

    static class Node {
        public int idx;
        public int value;

        public Node(int idx, int value) {
            this.idx = idx;
            this.value = value;
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
