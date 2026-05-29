package testing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class BipartiteGraph_9 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int [] NMData = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::valueOf).toArray();
        int N = NMData[0];
        int M = NMData[1];

        if (N == 1 && M == 0){
            System.out.println("YES");
            return;
        }

        if (N == 1 && M != 0){
            System.out.println("NO");
            return;
        }

        List<List<Integer>> adjList = new ArrayList<>(N);
        Stack<Integer> wayStack = new Stack<>();
        HashMap<Integer, Boolean> colors = new HashMap<>(N);
        ArrayList<Integer> vertexes = new ArrayList<>(N);
        Set<Integer> visited = new HashSet<>();

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

        while (!vertexes.isEmpty()) {
            boolean currentColor = true;
            int currentHead = vertexes.getFirst();
            colors.put(currentHead, currentColor);
            wayStack.add(currentHead);
            while (!wayStack.isEmpty()) {
                int currentVertex = wayStack.pop();
                if (visited.contains(currentVertex)) continue;
                visited.add(currentVertex);
                vertexes.remove(Integer.valueOf(currentVertex));
                currentColor = colors.get(currentVertex);
                wayStack.addAll(adjList.get(currentVertex).stream().filter(v -> !visited.contains(v)).toList());

                boolean nextCurrentColor = !currentColor;

                for (int v : adjList.get(currentVertex)) {
                    if (colors.get(v) == null) colors.put(v, nextCurrentColor);
                    else if (colors.get(v) != nextCurrentColor) {
                        System.out.println("NO");
                        return;
                    }
                }
            }
            vertexes.remove(Integer.valueOf(currentHead));
        }

        System.out.println("YES");

        writer.close();
    }
}
