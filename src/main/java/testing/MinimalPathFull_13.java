package testing;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class MinimalPathFull_13 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(reader.readLine());

        List<List<Integer>> adjList = new ArrayList<>();
        for (int i = 0; i < n; i++){
            adjList.add(new ArrayList<>(n));
            String [] sArr = reader.readLine().split(" ");
            for (int j = 0; j < n; j++){
                if (sArr[j].equals("1")) adjList.get(i).add(j);
            }
        }

        int [] startGoal = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::valueOf).toArray();

        int start = startGoal[0] - 1;
        int goal = startGoal[1] - 1;

        Set<Integer> visited = new HashSet<>(n);
        Map<Integer, Integer> parents = new HashMap<>(n);

        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(start);

        while (!queue.isEmpty()){
            int curr = queue.poll();
            visited.add(curr);
            for (int k : adjList.get(curr)){
                if (!visited.contains(k)){
                    queue.add(k);
                    parents.putIfAbsent(k, curr);
                }
            }
        }

        for (int i = 0; i < n; i++){
            parents.putIfAbsent(i, -1);
        }

        int len = -1;
        int curr = goal;

        ArrayList<Integer> result = new ArrayList<>();
        while (curr != start && curr != -1){
            result.add(curr);

            curr = parents.get(curr);
            len++;
        }

        if (curr == -1) {
            len = -2;
        } else if (len != -1) result.add(start);

        writer.write(String.valueOf(len + 1));
        if (len != -2) writer.write("\n" + result.reversed().stream().map(v -> String.valueOf(v + 1)).collect(Collectors.joining(" ")));

        reader.close();
        writer.close();
    }
}
