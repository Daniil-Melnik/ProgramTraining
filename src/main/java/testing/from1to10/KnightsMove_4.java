package testing.from1to10;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class KnightsMove_4 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        String hwStr = reader.readLine();

        int h = Integer.parseInt(hwStr.split(" ")[0]);
        int w = Integer.parseInt(hwStr.split(" ")[1]);

        int [][] cells = new int[h][w];
        int [][] costs = new int[h][w];

        costs[0][0] = 1;

        for (int i = 0; i < h; i++){
            for (int j = 0; j < w; j++){
                if (i - 2 >= 0 && j - 1 >= 0){
                    costs[i][j] += costs[i-2][j-1];
                }
                if (i - 1 >= 0 && j - 2 >= 0){
                    costs[i][j] += costs[i-1][j-2];
                }
            }
        }

        writer.write(String.valueOf(costs[h-1][w-1]));

        reader.close();
        writer.close();
    }
}
