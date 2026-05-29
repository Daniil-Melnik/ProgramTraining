package testing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;
import java.util.stream.Collectors;


public class DepthFirstSearch_7_3 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int [] NMData = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::valueOf).toArray();
        int N = NMData[0];
        int M = NMData[1];

        Set<Integer> visited = new TreeSet<>();
        List<List<Integer>> adjList = new ArrayList<>(N);

        for (int i = 0; i < N; i++){
            adjList.add(new ArrayList<>());
        }

        for (int i = 0; i < M; i++){
            int [] edge = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::valueOf).toArray();
            adjList.get(edge[0] - 1).add(edge[1] - 1);
            adjList.get(edge[1] - 1).add(edge[0] - 1);
        }
        reader.close();

        DFS(visited, 0, adjList);

        String result = visited.stream().map(v -> String.valueOf(v + 1)).collect(Collectors.joining(" "));
        writer.write(String.format("%s\n%s", visited.size(), result));
        writer.close();
    }

    private static void DFS(Set<Integer> visited, int currentVertex, List<List<Integer>> adjList){
        visited.add(currentVertex);
        for (int v : adjList.get(currentVertex)){
            if (!visited.contains(v)){
                DFS(visited, v, adjList);
            }
        }
    }
}
