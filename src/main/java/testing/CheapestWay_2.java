package testing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class CheapestWay_2 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        String hwStr = reader.readLine();

        int h = Integer.parseInt(hwStr.split(" ")[0]);
        int w = Integer.parseInt(hwStr.split(" ")[1]);

        int [][] cells = new int[h][w];
        int [][] dps = new int[h][w];

        for (int i = 0; i < h; i++){
            cells[i] = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        for (int i = 0; i < h; i++){
            for (int j = 0; j < w; j++){
                if ((i > 0) && (j > 0)){
                    dps[i][j] = cells[i][j] + Math.min(dps[i-1][j], dps[i][j-1]);
                }
                else if ((i == 0) && (j == 0)) { dps[i][j] = cells[i][j];}
                else if ((i == 0) && (j > 0)){ dps[i][j] = cells[i][j] + dps[i][j-1];}
                else if ((i > 0) && (j == 0)){ dps[i][j] = cells[i][j] + dps[i-1][j];}
                else {}
            }
        }

        writer.write(String.valueOf(dps[h-1][w-1]));
        reader.close();
        writer.close();
    }
}
