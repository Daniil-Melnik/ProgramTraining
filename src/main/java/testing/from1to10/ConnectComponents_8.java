package testing.from1to10;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;
import java.util.stream.Collectors;

public class ConnectComponents_8 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int [] NMData = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::valueOf).toArray();
        int N = NMData[0];
        int M = NMData[1];

        ArrayList<TreeSet<Integer>> visited = new ArrayList<>();
        List<List<Integer>> adjList = new ArrayList<>(N);
        List<Integer> vertexes = new ArrayList<>();
        Stack<Integer> wayStack = new Stack<>();
        int nComponent = -1;

        for (int i = 0; i < N; i++){
            adjList.add(new ArrayList<>());
            vertexes.add(i);
        }

        for (int i = 0; i < M; i++){
            int [] edge = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::valueOf).toArray();
            adjList.get(edge[0] - 1).add(edge[1] - 1);
            adjList.get(edge[1] - 1).add(edge[0] - 1);
        }
        reader.close();

        while (!vertexes.isEmpty()){
            int currentHead = vertexes.getFirst();
            visited.add(new TreeSet<>());
            nComponent += 1;
            visited.get(nComponent).add(currentHead);
            int finalNComponent = nComponent;

            wayStack.addAll(adjList.get(currentHead).stream().filter(v -> !visited.get(finalNComponent).contains(v)).collect(Collectors.toList()));

            while (!wayStack.isEmpty()){
                int currentVertex = wayStack.pop();
                if (visited.get(finalNComponent).contains(currentVertex)) continue;
                wayStack.addAll(adjList.get(currentVertex).stream().filter(v -> !visited.get(finalNComponent).contains(v)).collect(Collectors.toList()));
                visited.get(finalNComponent).add(currentVertex);
                vertexes.remove(Integer.valueOf(currentVertex));
            }
            vertexes.remove(Integer.valueOf(currentHead));
        }


        String result = visited.stream()
                        .map(v -> {
                            String elements = v.stream().map(k -> String.valueOf(k + 1))
                                    .collect(Collectors.joining(" "));
                            return v.size() + "\n" + elements;
                        })
                                .collect(Collectors.joining("\n"));

        writer.write(String.format("%s\n%s", visited.size(), result));
        writer.close();
    }
}
