package testing.from11to20;

// с однолинейными станциями ОК

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Metro2_16_2 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(reader.readLine());
        int m = Integer.parseInt(reader.readLine());

        List<Set<Integer>> adjLineList = new ArrayList<>(n);
        Map<Integer, Set<Integer>> lineMap = new HashMap<>(n);

        for (int i = 0; i < n; i++){
            lineMap.put(i, new HashSet<Integer>());
        }
        for (int i = 0; i < m; i++){
            adjLineList.add(new HashSet<>());
        }

        for (int i = 0; i < m; i++){
            ArrayList<Integer> lineArr = Arrays.stream(reader.readLine().split(" ")).map(Integer::valueOf).collect(Collectors.toCollection(ArrayList::new));
            for (int j = 1; j < lineArr.size(); j++){
                lineMap.get(lineArr.get(j) - 1).add(i);
            }
        }

        int [] startGoal = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::valueOf).toArray();
        int start = startGoal[0] - 1;
        int goal = startGoal[1] - 1;

        for (Set<Integer> adgLines : lineMap.values()){
            for (int el1 : adgLines)
                for (int el2 : adgLines)
                    if (el1 != el2){
                        adjLineList.get(el1).add(el2);
                        adjLineList.get(el2).add(el1);
                    }
        }

        Set<Integer> goalLines = lineMap.get(goal);
        Set<Integer> startLines = lineMap.get(start);

        System.out.println(startLines);
        System.out.println(goalLines);

        Set<Integer> probe = new HashSet<>(Set.copyOf(lineMap.get(goal)));
        probe.retainAll(startLines);

        if (!probe.isEmpty()) writer.write("FOUND - 0");
        else {
            Iterator<Integer> startIterator = startLines.iterator();
            Iterator<Integer> goalIterator = goalLines.iterator();

            System.out.println(startIterator.hasNext() + " " + goalIterator.hasNext());
            while (startIterator.hasNext()){
                int startLine = startIterator.next();
                while (goalIterator.hasNext()){
                    int goalLine = goalIterator.next();

                    System.out.println(startLine + " " + goalLine);

                    Queue<Integer> queue = new ArrayDeque<>();
                    Set<Integer> visited = new HashSet<>();
                    queue.add(startLine);
                    Map<Integer, Integer> parents = new HashMap<>();
                    int current = -1;
                    while (!queue.isEmpty() && current != goalLine){
                        current = queue.poll();
                        visited.add(current);
                        for (int el : adjLineList.get(current)) {
                            if (!visited.contains(el)){
                                queue.add(el);
                                parents.put(el, current);
                            }
                        }
                    }
                    if (current == goalLine){
                        int cnt = 0;
                        System.out.println("OK");
                        while (current != startLine){
                            current = parents.get(current);
                            cnt++;
                        }
                        writer.write(String.valueOf(cnt));
                    }
                }
            }
        }

        System.out.println(adjLineList);

        reader.close();
        writer.close();

    }
}
