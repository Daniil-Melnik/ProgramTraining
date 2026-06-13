package testing.from11to20;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;
import java.io.*;
import java.util.stream.Collectors;

public class Metro2_16_3 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int m = Integer.parseInt(br.readLine());

        List<Set<Integer>> lineMap = new ArrayList<>(n);
        for (int i = 0; i < n; i++) lineMap.add(new HashSet<>());

        List<List<Integer>> lines = new ArrayList<>(m);

        for (int i = 0; i < m; i++) {
            String[] parts = br.readLine().split(" ");
            int pi = Integer.parseInt(parts[0]);
            List<Integer> stations = new ArrayList<>();
            for (int j = 1; j <= pi; j++) {
                int st = Integer.parseInt(parts[j]) - 1;
                stations.add(st);
                lineMap.get(st).add(i);
            }
            lines.add(stations);
        }

        String[] startGoal = br.readLine().split(" ");
        int start = Integer.parseInt(startGoal[0]) - 1;
        int goal = Integer.parseInt(startGoal[1]) - 1;

        int[] dist = new int[m];
        Arrays.fill(dist, -1);

        Queue<Integer> queue = new ArrayDeque<>();

        for (int line : lineMap.get(start)) {
            if (dist[line] == -1) {
                dist[line] = 0;
                queue.add(line);
            }
        }

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            for (int station : lines.get(curr)) {
                for (int v : lineMap.get(station)) {
                    if (dist[v] == -1) {
                        dist[v] = dist[curr] + 1;
                        queue.add(v);
                    }
                }
            }
        }

        int minPath = Integer.MAX_VALUE;
        for (int line : lineMap.get(goal)) {
            if (dist[line] != -1) {
                minPath = Math.min(minPath, dist[line]);
            }
        }

        System.out.println(minPath == Integer.MAX_VALUE ? -1 : minPath);
        br.close();
    }
}
