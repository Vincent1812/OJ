package graph.virtualTree.codeforces.q613D;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] depth, dfsOrder;
    static int[] top;
    static List<Integer>[] neighbor;
    static int[] parent;
    static int[] son;
    static int n, qsize;
    static Integer[] query;
    static List<Integer>[] vneighbor;
    static int resp;
    static Set<Integer> querySet;


    public static void main(String[] args) {
        FastScanner sc = new FastScanner();
        n = sc.nextInt();
        if (n <= 0) {
            System.out.println(-1);
            return;
        }
        depth = new int[n + 1];
        top = new int[n + 1];
        neighbor = new List[n + 1];
        parent = new int[n + 1];
        son = new int[n + 1];
        dfsOrder = new int[n + 1];
        for (int i = 1; i < n + 1; i++) {
            neighbor[i] = new ArrayList<>();
        }
        for (int i = 1; i < n; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            neighbor[a].add(b);
            neighbor[b].add(a);
        }
        dfslca1(1, 0);
        dfslca2(1, 1);
        dfsTreeOrder(1, 0, 0);
//        System.out.println("son = " + Arrays.toString(son));
//        System.out.println("depth = " + Arrays.toString(depth));
//        System.out.println("top = " + Arrays.toString(top));
//        System.out.println("dfsOrder:" + Arrays.toString(dfsOrder));
        int times = sc.nextInt();
        for (int j = 0; j < times; ++j) {
            vneighbor = new List[n + 1];
            qsize = sc.nextInt();
            query = new Integer[qsize];
            querySet = new HashSet<>();
            for (int i = 0; i < qsize; i++) {
                query[i] = sc.nextInt();
                querySet.add(query[i]);
            }
            buildVirtualTree();
            resp = 0;
            try {
                dfsAnsr(1, 0);
                System.out.println(resp);
            } catch (Exception e) {
                System.out.println(-1);
            }
        }
    }

    private static boolean dfsAnsr(int u, int p) throws Exception {
        if (querySet.contains(p) && querySet.contains(u) && depth[u] - depth[p] == 1) {
            throw new Exception();
        }
        if (querySet.contains(u)) {
            for (Integer child : vneighbor[u]) {
                if (child == p) {
                    continue;
                }
                if (dfsAnsr(child, u)) {
                    resp++;
                }
            }
            return true;
        } else {
            int subsize = 0;
            for (Integer child : vneighbor[u]) {
                if (child == p) {
                    continue;
                }
                if (dfsAnsr(child, u)) {
                    subsize++;
                }
            }
            if (subsize > 1) {
                //如果大于1个子儿子是查询节点，就必须删除当前点了，并且告知父节点，我没有查询节点需要考虑了
                resp++;
                return false;
            } else if (subsize == 1) {
                //如果刚好1个子儿子是查询节点，就不删除当前点了，并且告知父节点，我有查询节点需要考虑
                return true;
            } else {
                //如果没有1个子儿子是查询节点，就不删除当前点了，并且告知父节点，我没有查询节点需要考虑了
                return false;
            }
        }

    }

    private static void buildVirtualTree() {
        Arrays.sort(query, Comparator.comparingInt(a -> dfsOrder[a]));
//        System.out.println("sorted query:" + Arrays.toString(query));
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        if (query[0] != 1) {
            stack.push(query[0]);
        }
        for (int i = 1; i < qsize; i++) {
            int lca = getLca(stack.peek(), query[i]);
            if (stack.peek() == lca) {
                stack.push(query[i]);
                continue;
            }
            while (stack.size() > 1 && depth[stack.get(stack.size() - 2)] > depth[lca]) {
                addEdge(stack.pop(), stack.peek());
            }
            if (stack.get(stack.size() - 2) == lca) {
                addEdge(stack.pop(), stack.peek());
            } else {
                int pop = stack.pop();
                stack.push(lca);
                addEdge(pop, lca);
            }
            stack.push(query[i]);
        }
        while (stack.size() > 1) {
            addEdge(stack.pop(), stack.peek());
        }
    }

    private static void addEdge(int a, int b) {
        if (vneighbor[a] == null) {
            vneighbor[a] = new ArrayList<>();
        }
        vneighbor[a].add(b);
        if (vneighbor[b] == null) {
            vneighbor[b] = new ArrayList<>();
        }
        vneighbor[b].add(a);
    }

    private static int dfsTreeOrder(int u, int p, int lastIdx) {
        dfsOrder[u] = ++lastIdx;
        for (Integer c : neighbor[u]) {
            if (c == p) {
                continue;
            }
            lastIdx = dfsTreeOrder(c, u, lastIdx);
        }
        return lastIdx;
    }

    private static int dfslca1(int u, int p) {
        depth[u] = depth[p] + 1;
        parent[u] = p;
        int total = 1;
        int max = 0;
        for (Integer c : neighbor[u]) {
            if (c == p) {
                continue;
            }
            int sub = dfslca1(c, u);
            if (sub > max) {
                max = sub;
                son[u] = c;
            }
            total += sub;
        }
        return total;
    }

    private static void dfslca2(int u, int t) {
        top[u] = t;

        for (Integer c : neighbor[u]) {
            //不能使用t来判断是否父，如果参数是父节点，后面的重儿子就不好传参数了
            if (c == parent[u]) {
                continue;
            }
            if (c == son[u]) {
                dfslca2(c, t);
            } else {
                dfslca2(c, c);
            }

        }
    }

    static int getLca(int a, int b) {
        while (top[a] != top[b]) {
            if (depth[top[a]] < depth[top[b]]) {
                int tmp = a;
                a = b;
                b = tmp;
            }
            a = parent[top[a]];
        }
        return depth[a] < depth[b] ? a : b;
    }

    static class FastScanner {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer("");

        public String next() {
            while (!st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(bf.readLine());
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