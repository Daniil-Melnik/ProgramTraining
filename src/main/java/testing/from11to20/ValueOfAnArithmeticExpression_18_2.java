package testing.from11to20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Set;

public class ValueOfAnArithmeticExpression_18_2 {

    public static void main(String ... args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Parser parser = new Parser(stringToTokens(reader.readLine()));

        reader.close();

        System.out.println(parser.parse());
    }

    private static String[] stringToTokens(String pS){
        int pos = 0;
        LinkedList<String> listOfTokens = new LinkedList<>();

        Set<Character> brackets = Set.of('(', ')');
        Set<Character> operators = Set.of('-', '+', '*');

        while (pos < pS.length()){
            char currCh = pS.charAt(pos);
            if (currCh == ' ') pos++;
            else if (brackets.contains(currCh)){
                listOfTokens.add(String.valueOf(currCh));
                pos++;
            } else if (operators.contains(currCh)) {
                listOfTokens.add(String.valueOf(currCh));
                pos++;
            } else if (Character.isDigit(currCh)) {
                StringBuilder numberBuilder = new StringBuilder();
                while (Character.isDigit(currCh)){
                    numberBuilder.append(currCh);
                    pos++;

                    if (pos >= pS.length()) break;

                    currCh = pS.charAt(pos);
                }
                listOfTokens.add(numberBuilder.toString());
            } else {
                throw new IllegalStateException("Некорректный символ - " + currCh);
            }
        }
        return listOfTokens.toArray(new String[0]);
    }

    public static class Parser{
        private String [] tokens;
        private int pos = 0;

        public Parser(String [] t){
            tokens = t;
        }

        public double parse(){
            return expression();
        }

        public double expression(){
            boolean isBrackets = false;

            if (tokens[pos].equals("(")) { pos++; isBrackets = true;}

            if (!isNumber(tokens[pos])) throw new IllegalStateException("Ожидалось число");

            double left = Double.parseDouble(tokens[pos]);
            pos++;

            while (pos < tokens.length) {
                String op = tokens[pos];
                if (!op.equals("+") && !op.equals("-")) break;
                else pos++;

                double right;

                if (tokens[pos].equals("(")) {
                    right = expression();
                } else {
                    if (!isNumber(tokens[pos])) throw new IllegalStateException("Ожидалось число");

                    right = Double.parseDouble(tokens[pos]);
                    pos++;
                }

                if (op.equals("+")) left += right;
                else left -= right;
            }

            if (!(pos < tokens.length && isBrackets && tokens[pos].equals(")"))) throw new IllegalStateException("Ожидалась закрывающая скобка");
            else pos++;

            return left;
        }

        private boolean isNumber(String s){
            try {
                Integer.parseInt(s);
                return true;
            } catch (NumberFormatException e){
                return false;
            }
        }
    }
}
