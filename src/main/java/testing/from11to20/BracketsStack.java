package testing.from11to20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.Stack;

public class BracketsStack {

    public static void main(String ... args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();
        Parser.isValid(input);
    }

    private static class Parser{
        private static final Set<Character> openBrackets = Set.of('(', '{', '[');
        private static final Set<Character> closeBrackets = Set.of(')', '}', ']');

        public static boolean isValid(String bracketString){
            Stack<Character> bracketStack = new Stack<>();
            boolean res = true;
            for (int pos = 0; pos < bracketString.length(); pos++){
                if (openBrackets.contains(bracketString.charAt(pos))){
                    bracketStack.add(bracketString.charAt(pos));
                } else if (closeBrackets.contains(bracketString.charAt(pos))){
                    if (!bracketStack.isEmpty()){
                        char cB = bracketString.charAt(pos);
                        char oB = bracketStack.pop();
                        if (
                                cB == ')' && oB != '('
                                || cB == ']' && oB != '['
                                || cB == '}' && oB != '{'){
                            res = false;
                            System.out.println("Ошибка скобки - " + oB + " " + cB);
                            break;
                        }

                    } else {
                        res= false;
                        System.out.println("Стек пуст, а скобка закрывающая - " + bracketString.charAt(pos));
                        break;
                    }
                } else {
                    res = false;
                    System.out.println("Посторонний символ - " + bracketString.charAt(pos));
                    break;
                }
            }

            if (!bracketStack.isEmpty()) {
                res = false;
                System.out.println("Стек не пуст");
            }
            System.out.println(res);
            return res;
        }
    }
}
