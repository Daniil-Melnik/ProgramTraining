package testing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GraphGraph {

    private class Node{
        private String name;
        private List<Node> children = new ArrayList<>();

        public Node(String n){
            name = n;
        }

        @Override
        public String toString() {
            return String.format("%s = %s", name, children.stream().map(e -> e.name).collect(Collectors.joining(" ")));
        }
    }

    private void skipSpace(String s, int[] pos){
        while (pos[0] < s.length() && Character.isWhitespace(s.charAt(pos[0]))){
            pos[0]++;
        };
    }

    private String parseNum(String s, int[] pos){
        int start = pos[0];
        while (pos[0] < s.length() && Character.isDigit(s.charAt(pos[0]))){
            //System.out.println(s.charAt(pos[0]));
            pos[0]++;
        }
        if (start == pos[0]){
            throw new RuntimeException("num expected");
        }
        return s.substring(start, pos[0]);
    }

    private Node parseNode(String s, int[] pos){
        skipSpace(s, pos);
        boolean hasOuterParent = false;

        if (s.charAt(pos[0]) == '(') {
            hasOuterParent = true;
            pos[0]++;
        }

        String newNodeName = parseNum(s, pos);
        Node newNode = new Node(newNodeName);
        skipSpace(s, pos);
        if(pos[0] < s.length() && s.charAt(pos[0]) == '('){
            pos[0]++;
            skipSpace(s, pos);
            while(pos[0] < s.length() && s.charAt(pos[0]) != ')'){
                Node child = parseNode(s, pos);
                newNode.children.add(child);
                skipSpace(s, pos);
            }
            if (pos[0] >= s.length() || s.charAt(pos[0]) != ')'){
                throw new RuntimeException("Expected ')'");
            }
            pos[0]++;
            skipSpace(s, pos);
        }
        if (hasOuterParent){
            if (pos[0] >= s.length() && s.charAt(pos[0]) != ')') throw new RuntimeException("Expected ')'");
            pos[0]++;
            skipSpace(s, pos);
        }
        return newNode;
    }

    private static void printTree(Node node){
        System.out.println(node);
        node.children.forEach(GraphGraph::printTree);
    }

    private List<String> renderTree(Node root){
        Map<Integer, Integer> maxLensByDepth = new HashMap<>();
        List<String> lines = new ArrayList<>();
        getMaxLensByDepth(root, maxLensByDepth, 0);
        System.out.println(maxLensByDepth);
        renderNode(root, lines, 0, new ArrayList<>(), new ArrayList<>(), maxLensByDepth);
        return lines;
    }

    private void getMaxLensByDepth(Node root, Map<Integer, Integer> map, int depth){
        int nodeNameLen = root.name.length();
        map.put(depth, Math.max(map.getOrDefault(depth, 0), nodeNameLen));
        for (int i = 0; i < root.children.size(); i++){
            getMaxLensByDepth(root.children.get(i), map, depth + 1);
        }
    }

    private void renderNode(Node node,
                            List<String> accum,
                            int depth,
                            List<Boolean> hasBrother,
                            List<Integer> parenPrefixLensSize,
                            Map<Integer, Integer> maxLensByDepth
    ){
        StringBuilder prefix = new StringBuilder();
        int nChildren = node.children.size();


        //prefix.repeat(' ', depth * 3);
        //System.out.println(parenPrefixLensSize);

        for (int i = 0; i < depth; i++){
            if (i == 0){
                prefix.append(" ".repeat(parenPrefixLensSize.get(i)));
            } else {
                if (hasBrother.get(i - 1)){

                    prefix.append('|'); // не хвтает условия на дорисовку (неотрисовку для последнего)
                    prefix.append(" ".repeat(parenPrefixLensSize.get(i) - 1));

                } else {
                    prefix.append(" ".repeat(parenPrefixLensSize.get(i)));
                }
            }

        }

        prefix.append(node.name);

        int nDashes = maxLensByDepth.get(depth) + 4 - node.name.length();

        if (nChildren > 0) prefix.append("-".repeat(nDashes - 1)).append("+");

        accum.add(prefix.toString());

        for (int i = 0; i < nChildren; i++){
            List<Boolean> newHasBrother = new ArrayList<>(hasBrother);
            newHasBrother.add(i != nChildren - 1);

            List<Integer> newParenPrefixLensSize = new ArrayList<>(parenPrefixLensSize);
            newParenPrefixLensSize.add(maxLensByDepth.get(depth) + 3);

            Node child = node.children.get(i);

            renderNode(child, accum, depth + 1, newHasBrother, newParenPrefixLensSize, maxLensByDepth);
        }
    }

    public static void main(String ... args){
        String input = "(13 (20 (4 5 60 (70) 8 (9)) 3 (63 63 78) 56))";
        int [] pos = {0};
        GraphGraph g = new GraphGraph();
        Node root = g.parseNode(input, pos);
        g.renderTree(root).forEach(System.out::println);
    }
}
