package testing.from21to30;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class СommercialСalculator_25 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int len = Integer.parseInt(reader.readLine());

        PriorityQueue<Integer> heap = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        });

        double tax = 0;
        int [] input = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::valueOf).toArray();

        for (int i = 0; i < len; i++){
            heap.add(input[i]);
        }

        int cnt = len;
        while (cnt > 1 && !heap.isEmpty()){
            int first = heap.poll();
            int second = heap.poll();
            heap.add(first + second);
            tax += (first + second) * 0.05;
            cnt--;
        }

        writer.write(String.format("%.2f", tax));
        reader.close();
        writer.close();
    }
}
