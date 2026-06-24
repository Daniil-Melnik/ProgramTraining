package testing.from11to20;

import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ValueOfABooleanExpression_19_2 {

    public static void main(String ... args) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        String input = reader.readLine();
        reader.close();

        Parser parser = new Parser(stringToTokens(input));
        writer.write(String.valueOf(parser.parse() ? 1 : 0));
        //writer.write(Arrays.toString(stringToTokens(input)));

        reader.close();
        writer.close();
    }

    private static class Parser{
        private static final Set<String> operators = Set.of("|", "&", "!", "^");

        private static String [] tokens;

        public Parser(String [] t){
            tokens = t;
        }

        private static String [] toReversedPolishNotation(){
            Stack<String> calcStack = new Stack<>();
            ArrayList<String> result = new ArrayList<>(tokens.length);

            for (String t : tokens){
                if (t.equals("1") || t.equals("0")) result.add(t);

                else if (operators.contains(t)){
                    while (!calcStack.isEmpty() && (getPriority(calcStack.peek()) >= getPriority(t))){
                        result.add(calcStack.pop());
                    }
                    calcStack.add(t);
                }

                else if (t.equals("(")) calcStack.add("(");
                else if (t.equals(")")){
                    while (!calcStack.isEmpty() && !calcStack.peek().equals("(")){
                        result.add(calcStack.pop());
                    }
                    if (!calcStack.isEmpty()) calcStack.pop();
                }
                else throw new IllegalStateException("Что-то не то в токенах - " + t);
            }

            while (!calcStack.isEmpty()) result.add(calcStack.pop());

            return result.toArray(new String[0]);
        }

        private boolean calcExpression(){
            Stack<Boolean> stack = new Stack<>();
            String [] polishNote = toReversedPolishNotation();
            //System.out.println(Arrays.toString(polishNote));

            for (String s : polishNote){
                if (s.equals("1") || s.equals("0")){
                    stack.add(s.equals("1"));
                } else if(s.equals("&") || s.equals("|") || s.equals("^")){
                    boolean right = stack.pop();
                    boolean left = stack.pop();

                    stack.add(s.equals("&") ? right && left : s.equals("|") ? right || left : right ^ left);
                } else if (s.equals("!")){
                    boolean num = stack.pop();
                    stack.add(!num);
                }

                //System.out.println(stack + " " + s);
            }

            return stack.pop();
        }

        public boolean parse(){
            return calcExpression();
        }

        private static int getPriority(String op){
            return switch (op){
                case "^", "|" -> 1;
                case "&" -> 2;
                case "!" -> 3;
                case "(" -> 0;
                default -> throw new IllegalStateException("Ожидалась оперция из " + operators + " а получено - " + op);
            };
        }
    }

    private static String [] stringToTokens(String s){
        Set<Character> operators = Set.of('|', '&', '!', '^');
        Set<Character> brackets = Set.of('(', ')');
        Set<Character> operands = Set.of('1', '0');
        int pos = 0;

        String pS = s.replaceAll(" ", "");
        List<String> listOfTokens = new ArrayList<>(pS.length());

        while (pos < pS.length()){
            char ch = pS.charAt(pos);
            if (operators.contains(ch) || brackets.contains(ch) || operands.contains(ch)){
                listOfTokens.add(String.valueOf(ch));
            } else throw new IllegalStateException("В выражении посторонний символ");
            pos++;
        }
        return listOfTokens.toArray(new String [0]);
    }
}
