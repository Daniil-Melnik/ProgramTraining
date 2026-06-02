package testing.from1to10;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;
import java.util.stream.Collectors;

public class DepthFirstSearch_7_2 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int [] NMData = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::valueOf).toArray();
        int N = NMData[0];
        int M = NMData[1];

        Set<Integer> visited = new TreeSet<>();
        Set<Integer> vertexes = new HashSet<>();
        Stack<Integer> wayStack = new Stack<>();

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

        visited.add(0);
        wayStack.addAll(adjList.get(0).stream().filter(v -> !wayStack.contains(v)).collect(Collectors.toCollection(ArrayList::new)));
        while (!wayStack.isEmpty()){
            int currVertex = wayStack.pop();
            wayStack.addAll(adjList.get(currVertex).stream().filter(v ->
                    !wayStack.contains(v) && !visited.contains(v))
                    .collect(Collectors.toCollection(ArrayList::new)));
            visited.add(currVertex);

        }

        writer.write(String.format("%s\n%s", visited.size(), visited.stream().map(v -> String.valueOf(v + 1)).collect(Collectors.joining(" "))));
        writer.close();
    }
}
