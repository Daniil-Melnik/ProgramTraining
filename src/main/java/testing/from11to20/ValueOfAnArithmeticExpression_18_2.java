package testing.from11to20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Set;

// доработать парсинг выражения из строки в токены

public class ValueOfAnArithmeticExpression_18_2 {

    public static void main(String ... args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String input = reader.readLine();
        Parser parser = new Parser(stringToTokens(input));

        reader.close();

        System.out.println(parser.parse());
        //System.out.println(Arrays.toString(stringToTokens(input)));
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
            } else if (currCh == 's'){
                if (pos + 3 < pS.length()){
                    if (pS.charAt(pos+1) == 'q' &&
                    pS.charAt(pos+2) == 'r' &&
                    pS.charAt(pos + 3) == 't') {
                        listOfTokens.add("sqrt");
                        pos += 3;
                        pos++;
                    }
                }
            } else if (currCh == 'p') {
                if(pos + 6 < pS.length()){
                    if (pS.charAt(pos+1) == 'o'&&
                    pS.charAt(pos+2) == 'w' &&
                    pS.charAt(pos+3) == '(' ){
                        int initPos = pos + 4;
                        StringBuilder argL = new StringBuilder();
                        StringBuilder argR = new StringBuilder();
                        while (Character.isDigit(pS.charAt(initPos))){
                            argL.append(pS.charAt(initPos));
                            initPos++;
                        }

                        if (pS.charAt(initPos) == ',') initPos++;
                        else throw new IllegalStateException("Ожидалась запятая");

                        while (Character.isDigit(pS.charAt(initPos))){
                            argR.append(pS.charAt(initPos));
                            initPos++;
                        }

                        if (pS.charAt(initPos) == ')') initPos++;
                        else throw new IllegalStateException("Ожидалась закрывающая скобка");

                        if (!argL.isEmpty() && !argR.isEmpty()){
                            listOfTokens.add("pow");
                            listOfTokens.add(argL.toString());
                            listOfTokens.add(argR.toString());

                            pos = initPos;
                        }
                        /*pos += 2;
                        pos++;*/
                    }

                }
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
            double left = term();

            while (pos < tokens.length) {
                String op = tokens[pos];
                if (!op.equals("+") && !op.equals("-")) break;
                else pos++;

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
                if (!op.equals("*") && !op.equals("/")) break;
                else pos++;

                double right = factor();

                if (op.equals("*")) left *= right;
                else left /= right;
            }

            return left;
        }

        public double factor(){
            String next = tokens[pos];

            if (next.equals("sqrt")){
                pos++;
                return Math.sqrt(factor());
            }

            if (next.equals("pow")){
                pos++;
                double basis = factor();
                double exponent = factor();

                return Math.pow(basis, exponent);
            }

            if (next.equals("(")) {
                pos++;

                if (tokens[pos].equals("-")){
                    pos++;
                    return -factor();
                }

                if (tokens[pos].equals("+")){
                    pos++;
                    return factor();
                }

                double result = expression();
                if (pos >= tokens.length) throw new IllegalStateException("Неожиданное окончание выражения");
                if (tokens[pos].equals(")")) pos++;
                else throw new IllegalStateException("Ожидалась закрывающая скобка");
                return result;
            }
            else if (isNumber(next)) {
                pos++;
                return Double.parseDouble(next);
            }
            else throw new IllegalStateException("Встречено что-то непонятное: " + next);
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
