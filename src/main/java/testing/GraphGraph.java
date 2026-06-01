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
        renderNode(root, lines, 0);
        return lines;
    }

    private void renderNode(Node node, List<String> accum, int depth){
        StringBuilder prefix = new StringBuilder();
        prefix.repeat(' ', depth*3).append(node.name);
        int nChildren = node.children.size();
        //System.out.println(nChildren);
        //if (nChildren > 0) prefix.append("---+");
        if (nChildren > 0) prefix.append("--+");
        accum.add(prefix.toString());
        for (int i = 0; i < nChildren; i++){
            renderNode(node.children.get(i), accum, depth + 1);
        }
    }

    public static void main(String ... args){
        String input = "(1 (2 (3 4 5 (6) 8) 9))";
        int [] pos = {0};
        GraphGraph g = new GraphGraph();
        Node root = g.parseNode(input, pos);
        //printTree(root);
        //System.out.println(g.renderTree(root));
        g.renderTree(root).forEach(System.out::println);
    }
}
