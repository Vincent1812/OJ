package graph.treeCenter.U309503;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    static int[] d1;
    static int[] d2;
    static int[] up;
    static int[] p1;
    static int n;
    static List<int[]>[] neighbor;


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt() + 1;
        neighbor = new List[n];
        for (int i = 1; i < n; ++i) {
            neighbor[i] = new ArrayList<>();
        }
        d1 = new int[n];
        d2 = new int[n];
        up = new int[n];
        p1 = new int[n];
        for (int i = 1; i < n - 1; ++i) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int c = sc.nextInt();
            neighbor[a].add(new int[]{b, c});
            neighbor[b].add(new int[]{a, c});
        }

        dfs_d(0, 1);
        dfs_u(0, 1);
        int min = Integer.MAX_VALUE;
        for (int i = 1; i < n; ++i) {
            int d = Math.max(d1[i], up[i]);
            if (min > d) {
                min = d;
            }
        }
        System.out.println(min);
        sc.close();
    }

    private static void dfs_u(int parent, int u) {
        int uu = parent == 0 ? 0 : up[u];
        for (int[] nb : neighbor[u]) {
            if (nb[0] == parent) {
                continue;
            }
            if (nb[0] == p1[u]) {
                up[nb[0]] = Math.max(d2[u], uu) + nb[1];
            } else {
                up[nb[0]] = Math.max(d1[u], uu) + nb[1];
            }
            dfs_u(u, nb[0]);
        }
    }

    private static int dfs_d(int parent, int u) {
        for (int[] nb : neighbor[u]) {
            if (nb[0] == parent) {
                continue;
            }
            int d = dfs_d(u, nb[0]) + nb[1];
            if (d >= d1[u]) {
                d2[u] = d1[u];
                d1[u] = d;
                p1[u] = nb[0];
            } else if (d > d2[u]) {
                d2[u] = d;
            }
        }
        return d1[u];
    }
}