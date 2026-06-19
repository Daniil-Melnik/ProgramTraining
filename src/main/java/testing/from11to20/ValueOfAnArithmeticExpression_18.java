package testing.from11to20;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Set;

public class ValueOfAnArithmeticExpression_18 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        String s = reader.readLine();
        //System.out.println(Arrays.toString(stringToTokens(s)));

        try {
            ExpressionParser parser = new ExpressionParser(stringToTokens(s));
            writer.write(String.valueOf((int) parser.parse()));
        } catch (IllegalStateException e){
            writer.write("WRONG");
        }

        reader.close();
        writer.close();
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

    private static class ExpressionParser{
        private String [] tokens;
        private int pos = 0;

        public ExpressionParser(String [] t){
            tokens = t;
        }

        public double parse() throws IllegalStateException{
            if (tokens.length != 0) {
                double result = expression();
                if (pos != tokens.length) throw new IllegalStateException("Использованы не все токены!");
                return result;
            } else throw new IllegalStateException("Пустая строка на входе");
        }

        public double expression(){
            double left = term();

            while (pos < tokens.length){
                String op = tokens[pos];
                if (!op.equals("+") && !op.equals("-")){
                    break;
                } else {
                    pos++;
                }

                double right = term();

                if (op.equals("+")) left += right;
                else left -= right;
            }

            return left;
        }

        public double term(){
            double left = factor();

            while (pos < tokens.length){
                String op = tokens[pos];
                if (!((op.equals("*") || op.equals("/")))){
                    break;
                } else pos++;

                double right = factor();

                if (op.equals("*")) left*= right;
                else {
                    throw new IllegalStateException("Деление");
                    /*if (right != 0){
                        left /= right;
                    } else throw new IllegalStateException("Деление на 0");*/

                }
            }
            return left;
        }

        public double factor() throws IllegalStateException{
            if (pos >= tokens.length) throw new IllegalStateException("Неожиданный конец выражения");

            String next = tokens[pos];
            double result;

            if (next.equals("-")) {
                pos++;
                double val = factor();
                return -val;
            }

            if (next.equals("+")) {
                pos++;
                return factor();   // унарный плюс ничего не меняет
            }

            if (next.equals("(")){
                pos++;
                result = expression();
                String endBracket = "";
                if (pos < tokens.length && (endBracket = tokens[pos]).equals(")")){
                    pos++;
                    return result;
                } else {
                    throw new IllegalStateException("Ожидалась закрывающая скобка, а не " + endBracket);
                }
            } else if (!isNumber(tokens[pos]))
                throw new IllegalStateException("Ожидалось число, а получено " + tokens[pos]);
            else {
                pos++;
                return Double.parseDouble(next);
            }
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
