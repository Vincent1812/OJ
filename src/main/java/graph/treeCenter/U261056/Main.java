package graph.treeCenter.U261056;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static int[] d1;
    static int[] d2;
    static int[] up;
    static int[] p1;
    static int n;
    static List<Integer>[] neighbor;

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
            neighbor[a].add(b);
            neighbor[b].add(a);
        }

        dfs_d(0, 1);
        dfs_u(0, 1);
        List<Integer> resp = new ArrayList<>();
        int min = Integer.MAX_VALUE;
        int cnt = 0;
        for (int i = 1; i < n; ++i) {
            int d = Math.max(d1[i], up[i]);
            if (min > d) {
                min = d;
                cnt = 1;
            } else if (min == d) {
                cnt += 1;
            }
        }
        for (int i = 1; i < n; ++i) {
            if (min == Math.max(d1[i], up[i])) {
                System.out.print(i);
                if (cnt > 1) {
                    System.out.print(' ');
                }
                cnt--;
            }
        }
        System.out.println();
        sc.close();
    }

    private static void dfs_u(int parent, int u) {
        int uu = parent == 0 ? 0 : up[u];
        for (int nb : neighbor[u]) {
            if (nb == parent) {
                continue;
            }
            if (nb == p1[u]) {
                up[nb] = Math.max(d2[u], uu) + 1;
            } else {
                up[nb] = Math.max(d1[u], uu) + 1;
            }
            dfs_u(u, nb);
        }
    }

    private static int dfs_d(int parent, int u) {
        for (int nb : neighbor[u]) {
            if (nb == parent) {
                continue;
            }
            int d = dfs_d(u, nb) + 1;
            if (d >= d1[u]) {
                d2[u] = d1[u];
                d1[u] = d;
                p1[u] = nb;
            } else if (d > d2[u]) {
                d2[u] = d;
            }
        }
        return d1[u];
    }
}