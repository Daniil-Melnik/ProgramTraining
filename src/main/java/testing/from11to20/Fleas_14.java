package testing.from11to20;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

// хранить не родительские узлы, а длину пути в рёбрах, потом просто сложить
public class Fleas_14 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int [] mA = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::valueOf).toArray();
        int N = mA[0];
        int M = mA[1];
        int S = mA[2];
        int T = mA[3];
        int Q = mA[4];

        Map<Node, Integer> parents = new HashMap<>();

        Set<Node> fleaNodes = new HashSet<>(Q);
        Set<Node> visited = new HashSet<>(Q);

        for (int i = 0; i < Q; i++){
            int [] fleaCoord = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::valueOf).toArray();

            int x = fleaCoord[0];
            int y = fleaCoord[1];

            fleaNodes.add(new Node(x, y));
        }

        ArrayDeque<Node> queue = new ArrayDeque<>();
        int sum = -1;

        Node startNode = new Node(S, T);
        queue.add(startNode);
        parents.putIfAbsent(startNode, 0);
        while (!queue.isEmpty()){
            //System.out.println(parents);
            Node curr = queue.poll();
            visited.add(curr);
            //System.out.println(curr);

            int currX = curr.x;
            int currY = curr.y;

            updateQueue(queue, currX, currY, visited, N, M, parents, curr);
        }
        /*for (int i = 0; i < 5; i++){
            //System.out.println(queue);
            System.out.println(visited);
            Node curr = queue.poll();
            visited.add(curr);
            //System.out.println(curr);

            int currX = curr.x;
            int currY = curr.y;

            updateQueue(queue, currX, currY, visited, N, M, parents, curr);
        }*/

        int fleaCnt = 0;

        for (Node f : fleaNodes){
            if (parents.containsKey(f)) fleaCnt++;
            sum += parents.getOrDefault(f, 0);
        }

        writer.write(String.valueOf((sum != -1 && fleaCnt == fleaNodes.size()) ? sum + 1 : -1));

        reader.close();
        writer.close();
    }

    private static void updateQueue(ArrayDeque<Node> queue, int currX, int currY, Set<Node> visited, int N, int M, Map<Node, Integer> parents, Node current){
        if (currX + 1 <= N && currY + 2 <= M){
            Node n = current.getUpdatedNode(1, 2);
            if (!visited.contains(n) && !queue.contains(n)){
                queue.add(n);
                parents.put(n, parents.get(current) + 1);
            }
        }

        if (currX - 1 >= 1 && currY + 2 <= M){
            Node n = current.getUpdatedNode(-1, 2);
            if (!visited.contains(n) && !queue.contains(n)){
                queue.add(n);
                parents.put(n, parents.get(current) + 1);
            }
        }

        if (currX + 1 <= N && currY - 2 >= 1){
            Node n = current.getUpdatedNode(1, -2);
            if (!visited.contains(n) && !queue.contains(n)){
                queue.add(n);
                parents.put(n, parents.get(current) + 1);
            }
        }

        if (currX - 1 >= 1 && currY - 2 >= 1){
            Node n = current.getUpdatedNode(-1, -2);
            if (!visited.contains(n) && !queue.contains(n)){
                queue.add(n);
                parents.put(n, parents.get(current) + 1);
            }
        }

        if (currX - 2 >= 1 && currY + 1 <= M){
            Node n = current.getUpdatedNode(-2, 1);
            if (!visited.contains(n) && !queue.contains(n)){
                queue.add(n);
                parents.put(n, parents.get(current) + 1);
            }
        }

        if (currX + 2 <= N && currY + 1 <= M){
            Node n = current.getUpdatedNode(2, 1);
            if (!visited.contains(n) && !queue.contains(n)){
                queue.add(n);
                parents.put(n, parents.get(current) + 1);
            }
        }

        if (currX - 2 >= 1 && currY - 1 >= 1){
            Node n = current.getUpdatedNode(-2, -1);
            if (!visited.contains(n) && !queue.contains(n)){
                queue.add(n);
                parents.put(n, parents.get(current) + 1);
            }
        }

        if (currX + 2 <= N && currY - 1 >= 1){
            Node n = current.getUpdatedNode(2, -1);
            if (!visited.contains(n) && !queue.contains(n)){
                queue.add(n);
                parents.put(n, parents.get(current) + 1);
            }
        }
    }

    private static class Node{
        private int x;
        private int y;

        public Node(int aX, int aY){
            x = aX;
            y = aY;
        }

        public Node getUpdatedNode(int mX, int mY){
            return new Node(this.getX() + mX, this.getY() + mY);
        }

        public int getX(){return x;}
        public int getY(){return y;}

        @Override
        public boolean equals(Object obj) {
            Node n = (Node) obj;
            return this.x == n.getX() && this.y == n.getY();
        }

        @Override
        public String toString() {
            return String.format("<%s-%s>", x, y);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }


}
