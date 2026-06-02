package testing.from1to10;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Cafe_5 {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int INF = 1000000000;

        int n = Integer.parseInt(reader.readLine());
        int[] costs = new int[n + 1];  // 1-indexed

        for (int i = 1; i <= n; i++) {
            costs[i] = Integer.parseInt(reader.readLine());
        }
        
        int[][] dp = new int[n + 1][n + 2];
        
        int[][][] wayInfo = new int[n + 1][n + 2][3];

        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], INF);
        }

        dp[0][0] = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= n; j++) {

                int newK = costs[i] > 100 ? j + 1 : j;

                if (dp[i][newK] > dp[i - 1][j] + costs[i]) {
                    dp[i][newK] = dp[i - 1][j] + costs[i];
                    wayInfo[i][newK][0] = i - 1;
                    wayInfo[i][newK][1] = j;
                    wayInfo[i][newK][2] = 0;
                }
                
                if (j >= 1) {
                    if (dp[i][j - 1] > dp[i - 1][j]) {
                        dp[i][j - 1] = dp[i - 1][j];
                        wayInfo[i][j - 1][0] = i - 1;
                        wayInfo[i][j - 1][1] = j;
                        wayInfo[i][j - 1][2] = 1;
                    }
                }
            }
        }
        
        int minWay = INF;
        int mink = -1;

        for (int j = 0; j <= n; j++) {
            if (dp[n][j] < minWay) {
                minWay = dp[n][j];
                mink = j;
            } else if (dp[n][j] == minWay && j > mink) {
                mink = j;
            }
        }
        
        List<Integer> couponDays = new ArrayList<>();
        int i = n, j = mink;

        while (i > 0) {
            int pI = wayInfo[i][j][0];
            int pJ = wayInfo[i][j][1];
            int action = wayInfo[i][j][2];

            if (action == 1) { // использовали купон
                couponDays.add(i);
            }

            i = pI;
            j = pJ;
        }

        Collections.sort(couponDays);
        
        int cuponsCount = couponDays.size();

        writer.write(minWay + "\n");
        writer.write(mink + " " + cuponsCount + "\n");
        for (int day : couponDays) {
            writer.write(day + "\n");
        }

        reader.close();
        writer.close();
    }
}
