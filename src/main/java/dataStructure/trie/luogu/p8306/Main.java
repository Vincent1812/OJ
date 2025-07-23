package dataStructure.trie.luogu.p8306;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int t, n, q, idx;
    static String line;
    static int[][] word;
    static int[] cnt;
    static boolean[] used;

    public static void main(String[] args) {
        FastScanner sc = new FastScanner();
        t = sc.nextInt();
        word = new int[3000001][];
        used = new boolean[3000001];
        cnt = new int[3000001];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < t; i++) {
            idx = 0;
            Arrays.fill(used, false);
            Arrays.fill(cnt, 0);
            n = sc.nextInt();
            q = sc.nextInt();
            for (int j = 0; j < n; j++) {
                line = sc.next();
                int p = 0;
                for (int k = 0; k < line.length(); k++) {
                    int nx = getIdx(line.charAt(k));
                    if (!used[p]) {
                        used[p] = true;
                        if (word[p] == null) {
                            word[p] = new int[62];
                        } else {
                            Arrays.fill(word[p], 0);
                        }
                    }
                    if (word[p][nx] == 0) {
                        word[p][nx] = ++idx;
                    }
                    p = word[p][nx];
                    cnt[p]++;
                }
            }
            for (int j = 0; j < q; j++) {
                query(sc.next(), sb);
            }
        }
        sb.setLength(sb.length() - 1);
        System.out.println(sb);
        System.out.println(getIdx('r'));
        System.out.println("r="+word[0][getIdx('r')]);
        System.out.println(cnt[1180]);
    }

    private static void query(String line, StringBuilder sb) {
        int p = 0;
        for (int k = 0; k < line.length(); k++) {
            int nx = getIdx(line.charAt(k));
            if (!used[p]) {
                sb.append("0\n");
                return;
            }
            p = word[p][nx];
        }
        sb.append(cnt[p]).append("\n");
    }

    private static int getIdx(char ch) {
        if (ch >= '0' && ch <= '9') {
            return ch - '0';
        }
        if (ch >= 'A' && ch <= 'Z') {
            return ch - 'A' + 10;
        }
        return ch - 'a' + 36;
    }

    static class FastScanner {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer("");

        public String next() {
            while (!st.hasMoreTokens()) {
                try {
                    String line = br.readLine();
                    st = new StringTokenizer(line);
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
