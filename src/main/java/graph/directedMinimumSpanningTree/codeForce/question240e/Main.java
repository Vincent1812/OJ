package graph.directedMinimumSpanningTree.codeForce.question240e;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int m, n;
    static List<Node>[] inDegree;
    static int a, b, c;

    public static void main(String[] args) {
        FastScanner sc = new FastScanner();
        n = sc.nextInt();
        m = sc.nextInt();
        inDegree = new List[n + 1];
        for (int i = 1; i < n + 1; i++) {
            inDegree[i] = new ArrayList<>();
        }
        for (int i = 0; i < m; i++) {
            a = sc.nextInt();
            b = sc.nextInt();
            c = sc.nextInt();
            inDegree[b].add(new Node(a, b, c));
        }
        boolean[] visited = new boolean[n + 1];
        Map<Integer, List<Integer>> fromTo;
        Set<Node> selected;
        while (true) {
            selected = new HashSet<>();
            fromTo = new HashMap<>();
            for (int i = 2; i <= n; i++) {
                Node node = inDegree[i].stream().min((a, b) -> a.value - b.value).get();
                if (fromTo.get(node.from) == null) {
                    fromTo.put(node.from, new ArrayList<>());
                }
                fromTo.get(node.from).add(node.to);
                selected.add(node);
            }
            for (int i = 1; i <= n; i++) {
                if (fromTo.get(i) == null) {
                    continue;
                }
                Stack<Integer> stack = new Stack<>();
                stack.push(i);
                visited[i] = true;
                boolean find = false;
                while (stack.size() > 0 && !find) {
                    Integer pop = stack.pop();
                    for (Integer next : fromTo.get(pop)) {
                        if (visited[next]) {
                            find = true;
                            break;
                        }
                    }
                }
            }
        }

    }

    static class Node {
        public int to;
        public int from;
        public int value;
        public int originValue;

        public Node(int from, int to, int value) {
            this.from = from;
            this.value = value;
            this.originValue = value;
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
