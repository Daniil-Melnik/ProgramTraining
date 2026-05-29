package testing;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class TopologicalSort_10 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int [] NMData = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::valueOf).toArray();
        int N = NMData[0];
        int M = NMData[1];
        HashMap<Integer, Integer> degrees = new HashMap<>(N);

        List<List<Integer>> adjList = new ArrayList<>(N);
        Set<Integer> vertexes = new HashSet<>(N);

        for (int i = 0; i < N; i++){
            adjList.add(new ArrayList<>());
            degrees.put(i, 0);
            vertexes.add(i);
        }

        for (int i = 0; i < M; i++){
            int [] edge = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::valueOf).toArray();
            adjList.get(edge[0] - 1).add(edge[1] - 1);
        }
        reader.close();


        for (int i = 0; i < N; i++){
            for (int j : adjList.get(i)){
                degrees.merge(j, 1, Integer::sum);
            }
        }

        Queue<Integer> queue = new ArrayDeque<>();
        ArrayList<Integer> result = new ArrayList<>();

        for (Map.Entry<Integer, Integer> e : degrees.entrySet()){
            if (e.getValue() == 0) {
                queue.add(e.getKey());
            }
        }

        while (!queue.isEmpty()){
            int currentEl = queue.poll();
            vertexes.remove(currentEl);
            result.add(currentEl);
            adjList.get(currentEl).forEach(e -> {
                degrees.compute(e, (k, v) -> v-=1);
                if (degrees.get(e) == 0) queue.add(e);
            });
        }
        if (!vertexes.isEmpty()) writer.write(String.valueOf(-1));

        else {
            String resultStr = result.stream().map(v -> String.valueOf(v + 1)).collect(Collectors.joining(" "));
            writer.write(resultStr);
        }

        writer.close();
    }
}
