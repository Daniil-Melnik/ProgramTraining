package testing.from11to20;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;
import java.util.stream.Collectors;

public class CycleSearch_11 {


    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        List<List<Integer>> adjList = new ArrayList<>();
        ArrayList<Boolean> visited = new ArrayList<>();

        int n = Integer.parseInt(reader.readLine());
        for (int i = 0; i < n; i++) adjList.add(new ArrayList<>());

        int [][] s = new int [n][n];

        for (int i = 0; i < n; i++){
            String line = reader.readLine();
            s[i] = Arrays.stream(line.split(" ")).mapToInt(Integer::valueOf).toArray();
            for (int j = i; j < n; j++) {
                if (s[i][j] == 1) {
                    adjList.get(i).add(j);
                    adjList.get(j).add(i);
                }
            }
            visited.add(false);
        }
        //System.out.println(adjList);
        List<Integer> cycle = new ArrayList<>();
        boolean [] hasCycle = new boolean[]{false};
        for (int i = 0; i < n; i++){
            if (visited.get(i)) continue;
            List<Integer> path = new ArrayList<>();
            if (!hasCycle[0])
                dfs(i, -1, adjList, visited, path, cycle, hasCycle);
        }

        if ( hasCycle[0]){
            writer.write("YES\n");
            writer.write(cycle.size() + "\n");
            writer.write(cycle.stream().map(v -> String.valueOf(v + 1)).collect(Collectors.joining(" ")));
        } else {
            writer.write("NO");
        }

        reader.close();
        writer.close();
    }

    private static boolean dfs(int node, int parent, List<List<Integer>> adjList, List<Boolean> visited, List<Integer> path, List<Integer> cycle, boolean [] hasCycle){

        visited.set(node, true);
        for (int v : adjList.get(node)){
            if (hasCycle[0]) break;
            if (!visited.get(v)){

                List<Integer> newPath = new ArrayList<>(path);
                newPath.add(node);
                dfs(v, node, adjList, visited, newPath, cycle, hasCycle);

            } else if (v != parent){
                //System.out.println();
                hasCycle[0] = true;
                path.add(node);
                //System.out.println(path + " " + parent + " " + node + " " + v);
                int el = path.size() - 1;

                while (path.get(el) != v){
                    cycle.add(path.get(el));
                    el--;
                }
                cycle.add(v);
            }
        }
        return false;
    }
}
