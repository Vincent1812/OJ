package dataStructure.heap.luogu.p1631;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
    static int n;
    static int[] a, b;
    static Set<String> visited;
    static PriorityQueue<Node> minQueue;

    public static void main(String[] args) {
        FastScanner sc = new FastScanner();
        n = sc.nextInt();
        a = new int[n + 1];
        b = new int[n + 1];
        visited = new HashSet<>();
        minQueue = new PriorityQueue<>((a, b) -> a.sum - b.sum);
        for (int i = 1; i <= n; i++) {
            a[i] = sc.nextInt();
        }
        for (int i = 1; i <= n; i++) {
            b[i] = sc.nextInt();
        }
        StringBuilder sb = new StringBuilder();
        minQueue.offer(new Node(1, 1));
        for (int i = 0; i < n; i++) {
            Node poll = minQueue.poll();
            sb.append(poll.sum).append(" ");
            String key = (poll.i + 1) + "," + poll.j;
            if (poll.i < n && !visited.contains(key)) {
                minQueue.offer(new Node(poll.i + 1, poll.j));
                visited.add(key);
            }
            key = poll.i + "," + (poll.j + 1);
            if (poll.j < n && !visited.contains(key)) {
                minQueue.offer(new Node(poll.i, poll.j + 1));
                visited.add(key);
            }
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        System.out.println(sb);
    }

    static class Node {
        public int i;
        public int j;
        public int sum;

        public Node(int i, int j) {
            this.i = i;
            this.j = j;
            this.sum = a[i] + b[j];
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
