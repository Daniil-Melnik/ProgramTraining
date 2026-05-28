package testing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Cafe_5 {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int INF = Integer.MAX_VALUE;

        int k = Integer.parseInt(reader.readLine());

        int [] costs = new int[k];

        for (int i = 0; i < k; i++){
            costs[i] = Integer.parseInt(reader.readLine());
        }

        int [][] s = new int[k][k];

        for (int i = 0; i < k; i++){
            Arrays.fill(s[i], INF);
        }

        s[0][0] = 0;

        for (int i = 1; i < k; i++){
            for (int j = 0; j < k; j++){
                
            }
        }

        reader.close();
        writer.close();
    }
}
