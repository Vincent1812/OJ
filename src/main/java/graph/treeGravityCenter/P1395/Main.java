package graph.treeGravityCenter.P1395;

import java.util.*;

public class Main {
    static int n;
    static Map<Long, List<Long>> neighbor;
    static long resp;
    static long min;
    static long step;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        if (n <= 0) {
            System.out.println("0 0");
            return;
        }
        if (n == 1) {
            System.out.println("1 0");
            return;
        }
        neighbor = new HashMap<>();
        for (int i = 1; i < n; ++i) {
            long a = sc.nextLong();
            long b = sc.nextLong();
            if(neighbor.get(a) == null){
                neighbor.put(a, new ArrayList<>());
            }
            if(neighbor.get(b) == null){
                neighbor.put(b, new ArrayList<>());
            }
            neighbor.get(a).add(b);
            neighbor.get(b).add(a);
        }
        resp = Long.MAX_VALUE;
        min = Long.MAX_VALUE;
        dfs(null, 1L);
        step = 0;
        dfs_step(null, resp, 0);
        System.out.println(resp + " " + step);
        sc.close();
    }

    private static void dfs_step(Long parent, Long u, long level) {
        step += level;
        if (neighbor.get(u) == null) {
            return;
        }
        for (Long child : neighbor.get(u)) {
            if (child.equals(parent)) {
                continue;
            }
            dfs_step(u, child, level + 1);
        }
    }

    private static long dfs(Long parent, Long u) {
        long max = 0;
        long sum = 0;
        if (neighbor.get(u) == null) {
            return sum + 1;
        }
        for (Long child : neighbor.get(u)) {
            if (child.equals(parent)) {
                continue;
            }
            long d = dfs(u, child);
            sum += d;
            if (max < d) {
                max = d;
            }
        }
        long tempMax = Math.max(n - sum - 1, max);
        if (min > tempMax) {
            min = tempMax;
            resp = u;
        } else if (min == tempMax && u < resp) {
            resp = u;
        }
        return sum + 1;
    }

}