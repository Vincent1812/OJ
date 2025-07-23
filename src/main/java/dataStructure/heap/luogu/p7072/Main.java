package dataStructure.heap.luogu.p7072;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 *   对顶堆
 *   https://www.luogu.com.cn/problem/P7072
 */
public class Main {
    static int n, w, score, k;
    static PriorityQueue<Integer> minQueue, maxQueue;

    public static void main(String[] args) {
        minQueue = new PriorityQueue<>((a, b) -> a - b);
        maxQueue = new PriorityQueue<>((a, b) -> b - a);
        FastScanner sc = new FastScanner();
        n = sc.nextInt();
        w = sc.nextInt();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            score = sc.nextInt();
            if (minQueue.isEmpty() || score >= minQueue.peek()) {
                minQueue.offer(score);
            } else {
                maxQueue.offer(score);
            }
            k = Math.max(1, (i + 1) * w / 100);
            while (minQueue.size() > k) {
                maxQueue.offer(minQueue.poll());
            }
            while (!maxQueue.isEmpty() && minQueue.size() < k) {
                minQueue.offer(maxQueue.poll());
            }
            sb.append(minQueue.peek()).append(" ");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
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
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }
    }
}
