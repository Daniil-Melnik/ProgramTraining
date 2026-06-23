package testing.from11to20;

import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExpressionComputingStack {

    private final static Set<String> operators = Set.of("+", "-", "*", "/", "sqrt", "pow");
    private final static Set<String> unaryOperators = Set.of("+", "-");
    private final static Set<String> unaryOperatorsGramma = Set.of("u+", "u-");

    public static void main(String ... args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();
        reader.close();

        //System.out.println(Arrays.toString(toReversePolishNotation(stringToTokens(input))));

        System.out.println(compExpression(toReversePolishNotation(stringToTokens(input))));
    }

    private static String[] toReversePolishNotation(String [] tokens){

        Stack<String> operStack = new Stack<>();

        List<String> reverseNotation = new ArrayList<>(tokens.length);
        for (int i = 0; i < tokens.length; i++){
            String t = tokens[i];
            String nT = "";

            if (i < tokens.length - 1) nT = tokens[i + 1];

            if (isNumber(t)){
                reverseNotation.add(t);
            }

            // Если встретили откр. скобку - положить её в стек операторов
            else if (t.equals("(")) {
                operStack.add(t);
                if (unaryOperators.contains(nT)){
                    if (nT.equals("-")) operStack.add("u-");
                    else if (nT.equals("+")) operStack.add("u+");
                    i++;
                }
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

    private static double compExpression(String [] reversedPolishNotation){
        Stack<Double> calcStack = new Stack<>();
        for (String t : reversedPolishNotation){
            if (isNumber(t)){
                calcStack.add(Double.parseDouble(t));
            } else if (operators.contains(t)){
                if (t.equals("sqrt")){
                    double right = calcStack.pop();
                    calcStack.add(Math.sqrt(right));
                } else if (t.equals("pow")) {
                    double exponent = calcStack.pop();
                    double basis = calcStack.pop();
                    calcStack.add(Math.pow(basis, exponent));
                }
                else {
                    double right = calcStack.pop();
                    double left = calcStack.pop();

                    switch (t) {
                        case "+": { left += right; break; }
                        case "-": { left -= right; break; }
                        case "*": { left *= right; break; }
                        case "/": { left /= right; break; }
                    }
                    calcStack.add(left);
                }
            } else if (unaryOperatorsGramma.contains(t)){
                double right = calcStack.pop();
                switch (t){
                    case "u+":
                        right = right;
                        break;

                    case "u-":
                        right = -right;
                        break;
                }
                calcStack.add(right);
            }
        }
        return calcStack.pop();
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
            case "-", "+" -> 1;
            case "*", "/" -> 2;
            case "(" -> 0;
            case "u+", "pow", "u-", "sqrt" -> 3;
            default -> throw new IllegalStateException("Ижмдался знак операции");
        };
    }

    private static String[] stringToTokens(String pS){
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
                        listOfTokens.add("pow");

                        int initPos = pos + 4;

                        if (pS.charAt(initPos) == '('){
                            listOfTokens.add("(");
                            initPos++;
                            while (pS.charAt(initPos) != ')'){
                                StringBuilder bb = new StringBuilder();
                                while (Character.isDigit(pS.charAt(initPos))){
                                    bb.append(pS.charAt(initPos));
                                    initPos++;
                                }

                                listOfTokens.add(bb.toString());

                                if (operators.contains(pS.charAt(initPos))){
                                    listOfTokens.add(String.valueOf(pS.charAt(initPos)));
                                    initPos++;
                                }
                            }
                            listOfTokens.add(")");
                            initPos++;
                        } else {
                            StringBuilder builder = new StringBuilder();
                            while (Character.isDigit(pS.charAt(initPos))){
                                builder.append(pS.charAt(initPos));
                                initPos++;
                            }
                            listOfTokens.add(String.valueOf(builder.toString()));
                        }

                        if (pS.charAt(initPos) == ',') initPos++;

                        if (pS.charAt(initPos) == '('){
                            listOfTokens.add("(");
                            initPos++;
                            while (pS.charAt(initPos) != ')'){
                                StringBuilder bb = new StringBuilder();
                                while (Character.isDigit(pS.charAt(initPos))){
                                    bb.append(pS.charAt(initPos));
                                    initPos++;
                                }

                                listOfTokens.add(bb.toString());

                                if (operators.contains(pS.charAt(initPos))){
                                    listOfTokens.add(String.valueOf(pS.charAt(initPos)));
                                    initPos++;
                                }
                            }
                            listOfTokens.add(")");
                            initPos++;
                        } else {
                            StringBuilder builder = new StringBuilder();
                            while (Character.isDigit(pS.charAt(initPos))){
                                builder.append(pS.charAt(initPos));
                                initPos++;
                            }
                            listOfTokens.add(builder.toString());
                        }

                        pos = initPos+1;
                    }

                }
            } else {
                throw new IllegalStateException("Некорректный символ - " + currCh);
            }
        }
        return listOfTokens.toArray(new String[0]);
    }
}
