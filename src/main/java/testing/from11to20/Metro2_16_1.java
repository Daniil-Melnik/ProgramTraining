package testing.from11to20;

// НЕ ЗАЧЕТ, так как ищет путь с минимальным числом перегонов (рёбер). Нужен пусть с минимальным числом пересадок

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;
import java.util.stream.Collectors;

public class Metro2_16_1 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(reader.readLine());
        int m = Integer.parseInt(reader.readLine());

        List<Set<Integer>> adjList = new ArrayList<>(n);
        Map<Integer, Set<Integer>> lineMap = new HashMap<>(n);
        Map<String, Integer> tunnelsMap = new HashMap<>();

        for (int i = 0; i < n; i++) {
            adjList.add(new HashSet<>());
            lineMap.put(i, new HashSet<>());
        }

        for (int i = 0; i < m; i++){
            ArrayList<Integer> arrI = Arrays.stream(reader.readLine().split(" ")).map(Integer::valueOf).collect(Collectors.toCollection(ArrayList::new));
            if (arrI.getFirst() != 0) {
                for (int j = 1; j < arrI.size(); j++) {
                    if (j >= 2) {
                        adjList.get(arrI.get(j) - 1).add(arrI.get(j - 1) - 1);
                        adjList.get(arrI.get(j - 1) - 1).add(arrI.get(j) - 1);

                        tunnelsMap.put(String.format("%s-%s", arrI.get(j) - 1, arrI.get(j - 1) - 1), i);
                        tunnelsMap.put(String.format("%s-%s", arrI.get(j - 1) - 1, arrI.get(j) - 1), i);
                    }
                    lineMap.get(arrI.get(j) - 1).add(i);
                }
            }
        }

        int [] startGoal = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::valueOf).toArray();
        int start = startGoal[0] - 1;
        int goal = startGoal[1] - 1;

        Queue<Integer> queue = new ArrayDeque<>();
        Set<Integer> visited = new HashSet<>(n);
        Map<Integer, Integer> parents = new HashMap<>(n);

        queue.add(start);
        while (!queue.isEmpty()){
            int current = queue.poll();
            visited.add(current);
            for (int el : adjList.get(current)){
                if (!visited.contains(Integer.valueOf(el)) && !queue.contains(el)){
                    queue.add(el);
                    parents.put(el, current);
                }
            }
        }

        int currentStation = goal;
        LinkedList<Integer> path = new LinkedList<>();

            int cnt = 0;
            while ((currentStation != start) && (parents.get(currentStation) != null)) {
                path.add(currentStation);
                currentStation = parents.get(currentStation);
            }

            if (currentStation == start) {
                path.add(start);

                path = path.reversed();

                int transferCnt = 0;

                int currentLine = 0;
                int previousLine = 0;

                if (path.size() > 1) {
                    for (int i = 1; i < path.size(); i++) {
                        previousLine = currentLine;
                        currentLine = tunnelsMap.get(String.format("%s-%s", path.get(i), path.get(i - 1)));
                        if ((currentLine != previousLine) && (i > 1)) transferCnt++;
                    }
                    writer.write(String.valueOf(transferCnt));
                } else {
                    writer.write(String.valueOf(0));
                }
            } else writer.write(String.valueOf(-1));

        reader.close();
        writer.close();

    }
}
