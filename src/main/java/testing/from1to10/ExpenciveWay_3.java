package testing.from1to10;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class ExpenciveWay_3 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        String hwStr = reader.readLine();

        int h = Integer.parseInt(hwStr.split(" ")[0]);
        int w = Integer.parseInt(hwStr.split(" ")[1]);

        int [][] cells = new int[h][w];
        int [][] costs = new int[h][w];

        String [][] ways = new String[h][w];

        for (int i = 0; i < h; i++){
            Arrays.fill(ways[i], "");
        }

        for (int i = 0; i < h; i++){
            cells[i] = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        for (int i = 0; i < h; i++){
            for (int j = 0; j < w; j++){
                if ((i > 0) && (j > 0)) {
                    costs[i][j] = cells[i][j] + Math.max(costs[i][j-1], costs[i-1][j]);
                    ways[i][j] = costs[i-1][j] > costs[i][j-1] ? ways[i-1][j] + "D" : ways[i][j-1] + "R";
                } else if((i == 0) && (j > 0)) {
                    costs[i][j] =  cells[i][j] + costs[i][j-1];
                    ways[i][j] += ways[i][j-1] + "R";
                } else if((i > 0) && (j == 0)) {
                    costs[i][j] = cells[i][j] + costs[i - 1][j];
                    ways[i][j] = ways[i - 1][j] + "D";
                } else if ((i == 0) && (j == 0)) {costs[i][j] = cells[i][j];}
            }
        }

        writer.write(String.valueOf(costs[h-1][w-1]) + "\n");
        writer.write(String.join(" ", ways[h-1][w-1].split("")));
        reader.close();
        writer.close();
    }
}
