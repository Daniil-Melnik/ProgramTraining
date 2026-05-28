package testing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

public class DepthFirstSearch_7 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        String nVertexesEdges = reader.readLine();
        int nVertexes = Integer.parseInt(nVertexesEdges.split(" ")[0]);
        int nEdges = Integer.parseInt(nVertexesEdges.split(" ")[1]);

        HashMap<String, Vertex> vertexes = new HashMap<>(nVertexes);
        ArrayList<Edge> edges = new ArrayList<>(nEdges);

        for (int i = 0; i < nEdges; i++){
            String edgeString = reader.readLine();
            String s = edgeString.split(" ")[0];
            String e = edgeString.split(" ")[1];
            edges.add(new Edge(s, e));

            vertexes.putIfAbsent(s, new Vertex(s));
            vertexes.putIfAbsent(e, new Vertex(e));
        }
        System.out.println(vertexes);
        System.out.println(edges);

        for (Edge edge : edges){
            vertexes.get(edge.getStart()).addNeibor(vertexes.get(edge.getEnd()));
            vertexes.get(edge.getEnd()).addNeibor(vertexes.get(edge.getStart()));
        }

        for (Vertex vertex : vertexes.values()){
            System.out.println(vertex.toString());
        }

        reader.close();
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
