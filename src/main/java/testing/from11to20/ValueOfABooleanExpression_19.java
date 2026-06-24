package testing.from11to20;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ValueOfABooleanExpression_19 {

    public static void main(String[] args) throws IOException {
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


    private static class Parser{
        String [] tokens;
        int pos = 0;

        public Parser(String [] t){
            tokens = t;
        }

        public boolean parse(){
            return expression();
        }

        private boolean expression(){
            boolean left = term();

            while (pos < tokens.length){
                String op = tokens[pos];
                if (!(op.equals("|") || op.equals("^"))) break;

                pos++;
                boolean right = term();
                if (op.equals("|")) left |= right;
                else left ^= right;
            }
            return left;
        }

        private boolean term(){
            boolean left = factor();

            while (pos < tokens.length){
                String op = tokens[pos];
                if (!op.equals("&")) break;

                pos++;
                boolean right = factor();
                left &= right;
            }
            return left;
        }

        private boolean factor(){
            String next = tokens[pos];
            if (next.equals("!")){
                pos++;
                return !factor();
            }
            if (next.equals("(")){
                pos++;
                boolean result = expression();
                String afterExpr = tokens[pos];
                if (afterExpr.equals(")")) {
                    pos++;
                    return result;
                } else throw new IllegalStateException("Ожидалась закрывающая скобка");
            } else {
                if ((next.equals("1")) || (next.equals("0"))){
                    pos++;
                    return switch (next) {
                        case "1" -> true;
                        case "0" -> false;
                        default -> throw new IllegalStateException("Прочая ошибка");
                    };
                } else throw new IllegalStateException("Ожидался операнд-значение");
            }
        }
    }
}
