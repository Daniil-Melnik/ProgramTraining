package testing.from11to20;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class HistogramAndRectangle_20 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        String input = reader.readLine();
        input += " 0";
        long [] h = Arrays.stream(input.split("\\s+")).mapToLong(Long::parseLong).toArray();

        ArrayList<Long> list = new ArrayList<>();
        Arrays.stream(h).forEach(list::add);

        long [] hS = list.subList(1, list.size()).stream().mapToLong(Long::valueOf).toArray();

        Stack<Integer> stack = new Stack<>();
        long maxS = 0;

        for (int i = 0; i < hS.length; i++){
            while (!stack.isEmpty() && hS[stack.peek()] > hS[i]){
                long top = hS[stack.pop()];
                long right = i;
                long left = stack.isEmpty() ? -1 : stack.peek();
                long width = right - left - 1;

                long newS = width * top;
                maxS = Math.max(maxS, newS);
            }
            stack.add(i);
        }

        writer.write(String.valueOf(maxS));

        reader.close();
        writer.close();
    }
}
