package testing;

import java.util.ArrayList;
import java.util.List;
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
        List<String> lines = new ArrayList<>();
        renderNode(root, lines, 0, new ArrayList<>(), new ArrayList<>());
        return lines;
    }

    private void renderNode(Node node,
                            List<String> accum,
                            int depth,
                            List<Boolean> hasBrother,
                            List<Integer> parenPrefixLensSize
    ){
        StringBuilder prefix = new StringBuilder();
        int nChildren = node.children.size();


        //prefix.repeat(' ', depth * 3);
        System.out.println(parenPrefixLensSize);

        for (int i = 0; i < depth; i++){
            if (i == 0){
                prefix.append(" ".repeat(3));
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

        if (nChildren > 0) prefix.append("--+");

        accum.add(prefix.toString());

        for (int i = 0; i < nChildren; i++){
            List<Boolean> newHasBrother = new ArrayList<>(hasBrother);
            newHasBrother.add(i != nChildren - 1);

            List<Integer> newParenPrefixLensSize = new ArrayList<>(parenPrefixLensSize);
            newParenPrefixLensSize.add(3);

            Node child = node.children.get(i);

            renderNode(child, accum, depth + 1, newHasBrother, newParenPrefixLensSize);
        }
    }

    public static void main(String ... args){
        String input = "(1 (2 (4 5 6 (7) 8 (9)) 3))";
        int [] pos = {0};
        GraphGraph g = new GraphGraph();
        Node root = g.parseNode(input, pos);
        //printTree(root);
        //System.out.println(g.renderTree(root));
        g.renderTree(root).forEach(System.out::println);
    }
}
