package dp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Salesman {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[][] a = new int[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                a[i][j] = scanner.nextInt();
            }
        }

        long[][] dist = new long[1 << n][n];
        short[][] parent = new short[1 << n][n];
        for (int i = 0; i < 1 << n; ++i) {
            for (int j = 0; j < n; ++j) {
                dist[i][j] = Long.MAX_VALUE / 2;
            }
        }
        for (int i = 0; i < n; ++i) {
            dist[1 << i][i] = 0;
        }
        for (int i = 0; i < 1 << n; ++i) {
            for (short j = 0; j < n; ++j) {
                if ((i >> j & 1) == 1) {
                    for (int k = 0; k < n; ++k) {
                        if ((i >> k & 1) == 0) {
                            if (dist[i | 1 << k][k] > dist[i][j] + a[j][k]) {
                                dist[i | 1 << k][k] = dist[i][j] + a[j][k];
                                parent[i | 1 << k][k] = j;
                            }
                        }
                    }
                }
            }
        }

        int mask = (1 << n) - 1;
        short end = Short.MAX_VALUE;
        long value = Long.MAX_VALUE;
        for (short i = 0; i < n; ++i) {
            if (dist[mask][i] < value) {
                value = dist[mask][i];
                end = i;
            }
        }
        System.out.println(dist[mask][end]);
        List<Short> path = new ArrayList<>();
        while (mask > 0) {
            path.add(end);
            short temp = parent[mask][end];
            mask ^= 1 << end;
            end = temp;
        }
        Collections.reverse(path);
        for (int num : path) {
            System.out.print((num + 1) + " ");
        }
    }

}
