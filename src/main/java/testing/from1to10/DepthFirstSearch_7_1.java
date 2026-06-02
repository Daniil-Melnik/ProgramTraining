package testing.from1to10;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Stack;
import java.util.HashSet;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class DepthFirstSearch_7_1 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        Stack<Vertex> wayStack = new Stack<>();
        HashSet<Vertex> visetedVertexes = new HashSet<>();

        String nVertexesEdges = reader.readLine();
        int nVertexes = Integer.parseInt(nVertexesEdges.split(" ")[0]);
        int nEdges = Integer.parseInt(nVertexesEdges.split(" ")[1]);

        HashMap<String, Vertex> vertexes = new HashMap<>(nVertexes);

        for (int i = 0; i < nEdges; i++){
            String edgeString = reader.readLine();
            String s = edgeString.split(" ")[0];
            String e = edgeString.split(" ")[1];

            vertexes.putIfAbsent(s, new Vertex(s));
            vertexes.putIfAbsent(e, new Vertex(e));

            vertexes.get(s).addNeibor(vertexes.get(e));
            vertexes.get(e).addNeibor(vertexes.get(s));
        }

        reader.close();

        if (vertexes.get("1") == null){ // вершина ни с кем не соединена и не фигурирует в списке рёбер => компонент-одиночка
            writer.write("1\n1");
            writer.close();
            return;
        }

        visetedVertexes.add(vertexes.get("1"));

        vertexes.get("1").getNeibors().stream()
                .filter(v -> !visetedVertexes.contains(v))
                .forEach(wayStack::add);

        while (!wayStack.isEmpty()) {
            Vertex currVertex = wayStack.pop();
            if (visetedVertexes.contains(currVertex)) continue;
            visetedVertexes.add(currVertex);
            currVertex.getNeibors().stream().filter(v -> !visetedVertexes.contains(v)).forEach(wayStack::add);
        }

        //StringBuilder result = new StringBuilder();

        TreeSet<Integer> resultSet = visetedVertexes.stream()
                .map(v -> Integer.parseInt(v.getName()))
                .collect(Collectors.toCollection(TreeSet::new));

        String result = resultSet.stream().map(String::valueOf).collect(Collectors.joining(" "));

        writer.write(String.format("%d\n%s", visetedVertexes.size(), result));
        writer.close();
    }

    private static class Vertex{
        private HashSet<Vertex> neibors = new HashSet<>();
        private String name;

        public Vertex(String n){
            name = n;
        }

        public void addNeibor(Vertex n){
            neibors.add(n);
        }

        public HashSet<Vertex> getNeibors(){
            return this.neibors;
        }

        public String getName(){return name;}

        @Override
        public String toString() {
            return String.format("%s - %s", name, String.join(" ", neibors.stream().map(Vertex::getName).collect(Collectors.joining(" "))));
        }
    }

    private static class Edge{
        private String start;
        private String end;

        public Edge(String s, String e){
            start = s;
            end = e;
        }

        public String getStart(){return start;}
        public String getEnd(){return end;}

        @Override
        public String toString() {
            return String.format("%s - %s", start, end);
        }
    }
}
