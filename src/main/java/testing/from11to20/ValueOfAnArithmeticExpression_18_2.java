package testing.from11to20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Set;

public class ValueOfAnArithmeticExpression_18_2 {

    public static void main(String ... args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        ArithmeticParser parser = new ArithmeticParser(stringToTokens(reader.readLine()));

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

    public static class ArithmeticParser{
        private String [] tokens;
        private int pos = 0;

        public ArithmeticParser(String [] t){
            tokens = t;
        }

        public double parse(){
            double result = expression();
            return result;
        }

        public double expression(){
            double first = term();

            while (pos < tokens.length){
                String op = tokens[pos];
                if (!op.equals("+") && !op.equals("-")){
                    break;
                } else pos++;

                double second = term();

                if (op.equals("+")) first += second;
                else first -= second;
            }

            return first;
        }

        public double term(){
            double first = factor();

            while (pos < tokens.length){
                String op = tokens[pos];
                if (!op.equals("*") && !op.equals("/")){
                    break;
                } else  pos++;

                double second = factor();

                if (op.equals("*")) first *= second;
                else first /= second;
            }

            return first;
        }

        public double factor(){
            String next = tokens[pos];
            if (isNumber(next)){
                pos++;
                return Double.parseDouble(next);
            } else if (next.equals("(")) {
                pos++;
                double result = expression();
                if (tokens[pos].equals(")")) {
                    pos++;
                    return result;
                }
                else throw new IllegalStateException("Ожидалась ')'");
            } else throw new IllegalStateException("Ожидалось число, а встречено " + next);
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
