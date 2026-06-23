package testing.from11to20;

import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExpressionComputingStack {

    public static void main(String ... args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();
        reader.close();

        System.out.println(Arrays.toString(toReversePolishNotation(stringToTokens(input))));
    }

    private static String[] toReversePolishNotation(String [] tokens){
        Set<String> operators = Set.of("+", "-", "*", "/");

        Stack<String> operStack = new Stack<>();

        List<String> reverseNotation = new ArrayList<>(tokens.length);
        for (String t : tokens){


            if (isNumber(t)){
                reverseNotation.add(t);
            }

            // Если встретили бинарный оператор - переложить из стека (до упора в более слабый)
            // все операторы с большей значимостью в результат.
            // Потом положить текущий оператор в стек
            else if (operators.contains(t)) {
                String mOp = "";
                while (!operStack.isEmpty() && (getPriority(operStack.peek()) >= getPriority(t))){
                    reverseNotation.add(operStack.pop());
                }
                operStack.add(t);
            }

            // Если встретили откр. скобку - положить её в стек операторов
            else if (t.equals("(")) {
                operStack.add(t);
            }

            // Если закрывающая скобка => положить все операции вплоть до открывающей скобки в стеке в результат,
            // закр. скобку из стека удалить
            else if (t.equals(")")){
                while (!operStack.isEmpty() && !operStack.peek().equals("(")){
                    reverseNotation.add(operStack.pop());
                }
                operStack.pop();
            }
            else throw new IllegalStateException("Какая-то проблема с токеном");
        }

        while (!operStack.isEmpty()) reverseNotation.add(operStack.pop());

        return reverseNotation.toArray(new String[0]);
    }

    private static boolean isNumber(String numStr){
        boolean result = true;
        try {
            Double.parseDouble(numStr);
        } catch (NumberFormatException e) {
            result = false;
        }
        return result;
    }

    private static int getPriority(String op){
        return switch (op) {
            case "-" -> 1;
            case "+" -> 1;
            case "*" -> 2;
            case "/" -> 2;
            case "(" -> 0;
            default -> throw new IllegalStateException("Ижмдался знак операции");
        };
    }

    private static String [] stringToTokens(String pS){
        int pos = 0;
        LinkedList<String> listOfTokens = new LinkedList<>();

        Set<Character> brackets = Set.of('(', ')');
        Set<Character> operators = Set.of('-', '+', '*', '/');

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
}
