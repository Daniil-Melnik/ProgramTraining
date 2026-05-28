package testing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;

public class NOP_6 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int nA = Integer.parseInt(reader.readLine());
        String sA = reader.readLine();

        int nB = Integer.parseInt(reader.readLine());
        String sB = reader.readLine();

        int[] originalA = Arrays.stream(sA.split(" ")).mapToInt(Integer::parseInt).toArray();
        int[] orB = Arrays.stream(sB.split(" ")).mapToInt(Integer::parseInt).toArray();

        int[] arrA = new int[originalA.length + 1];
        int[] arrB = new int[orB.length + 1];

        arrA[0] = 0;
        arrB[0] = 0;

        System.arraycopy(originalA, 0, arrA, 1, originalA.length);
        System.arraycopy(orB, 0, arrB, 1, orB.length);

        int [][] s = new int[nA+1][nB+1];

        for (int i = 1; i <= nA; i++){
            for (int j = 1; j <= nB; j++){
                s[i][j] = arrA[i] == arrB[j] ? s[i-1][j-1] + 1 : Math.max(s[i-1][j], s[i][j-1]);
            }
        }

        int currJ = nB;
        int currI = nA;

        ArrayList<Integer> resultList = new ArrayList<>();

        while (currI != 0 && currJ != 0){
            if (arrA[currI] == arrB[currJ]){
                resultList.add(arrA[currI]);
                currI -= 1;
                currJ -= 1;
            } else if (s[currI-1][currJ] > s[currI][currJ - 1]){
                currI -= 1;
            } else {
                currJ -= 1;
            }
        }

        Collections.reverse(resultList);

        String result = String.join(" ", resultList.stream().map(String::valueOf).toArray(String[]::new));

        writer.write(result);

        reader.close();
        writer.close();
    }
}
