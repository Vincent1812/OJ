package graph.heap.luogu.p3378;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 堆排序
 * 堆实现
 * https://www.luogu.com.cn/problem/P3378
 */
public class Main {
    static int op, n, cnt;
    static int[] arr;

    public static void main(String[] args) {
        FastScanner sc = new FastScanner();
        n = sc.nextInt();
        arr = new int[n + 1];
        cnt = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            op = sc.nextInt();
            if (op == 1) {
                push(sc.nextInt());
            } else if (op == 2) {
                sb.append(arr[1]).append("\r\n");
            } else if (op == 3) {
                pop();
            }
        }
        System.out.println(sb);
    }

    private static void pop() {
        arr[1] = arr[cnt--];
        down(1);
    }

    private static void down(int i) {
        int midx = i;
        if (i * 2 <= cnt && arr[i * 2] < arr[midx]) {
            midx = i * 2;
        }
        if (i * 2 + 1 <= cnt && arr[i * 2 + 1] < arr[midx]) {
            midx = i * 2 + 1;
        }
        if (i != midx) {
            int tmp = arr[midx];
            arr[midx] = arr[i];
            arr[i] = tmp;
            down(midx);
        }
    }

    private static void push(int value) {
        arr[++cnt] = value;
        up(cnt);
    }

    private static void up(int i) {
        if (i / 2 > 0 && arr[i / 2] > arr[i]) {
            int tmp = arr[i / 2];
            arr[i / 2] = arr[i];
            arr[i] = tmp;
            up(i / 2);
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
