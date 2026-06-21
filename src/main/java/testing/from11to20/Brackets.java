package testing.from11to20;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Set;

// внутри круглых могут быть квадратные, круглые
// внутри квадратных - круглые, фигурные
// внутри фигурных - фигурные, квадратные

public class Brackets {

    public static void main (String ... args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        try {
            input = reader.readLine();
            String [] tokens = stringToTokens(input);
            Parser parser = new Parser(tokens);
            System.out.println(parser.parse());
        } catch (IllegalStateException e) {
            System.out.println("ОШИБКА: " + e.getMessage());
        }
        reader.close();
    }

    private static class Parser{
        String [] tokens;
        int pos = 0;

        public Parser(String [] t){
            tokens = t;
        }

        public boolean parse() {
            String openBracket = tokens[pos];
            boolean result = false;

            switch (openBracket){
                case "(":
                    result = round();
                    break;

                case "[":
                    result = square();
                    break;
            }
            if (pos != tokens.length)
                throw new IllegalStateException("Не только последовательность (round)");
            return result;
        }

        public boolean round() {
            String openBracket = tokens[pos];
            pos++;
            while (pos < tokens.length){
                if (tokens[pos].equals("(")){
                    round();
                } else if (tokens[pos].equals("[")) {
                    square();
                } else if (tokens[pos].equals("{")) {
                    throw new IllegalStateException("Встречена запрещенная '{' внутри '('");
                } else break;
            }

            //String closingBracket = tokens[pos];
            if (pos < tokens.length && tokens[pos].equals(")")){
                pos++;
                return true;
            } else throw new IllegalStateException("Ожидалась ')'");
        }

        public boolean square(){
            String openBracket = tokens[pos];
            pos++;
            while (pos < tokens.length){
                if (tokens[pos].equals("[")){
                    throw new IllegalStateException("Встречена запрещенная '[' внутри '['");
                } else if (tokens[pos].equals("(")){
                    round();
                } else if (tokens[pos].equals("{")) {
                    curly();
                } else break;
            }

            if (pos < tokens.length && tokens[pos].equals("]")){
                pos++;
                return true;
            } else throw new IllegalStateException("Ожидалось ']'");
        }

        public boolean curly(){
            String openBracket = tokens[pos];
            pos++;

            while (pos < tokens.length){
                if (tokens[pos].equals("[")) square();
                else if (tokens[pos].equals("{")) curly();
                else if (tokens[pos].equals("(")) throw new IllegalStateException("Встречена запрещенная '(' внутри '{'");
                else break;
            }

            if (pos < tokens.length && tokens[pos].equals("}")) {
                pos++;
                return true;
            }
            else throw new IllegalStateException("Ожидалось '}'");
        }
    }

    private static String [] stringToTokens(String s){
        int pos = 0;
        ArrayList<String> list = new ArrayList<>();

        Set<Character> brackets = Set.of('(', '[', '{', '}', ']', ')');

        while (pos < s.length()){
            if (s.charAt(pos) == ' ') pos++;
            else if (brackets.contains(s.charAt(pos))) {
                list.add( String.valueOf(s.charAt(pos)));
                pos++;
            } else {
                throw new IllegalStateException("В строке есть посторонние символы");
            }
        }
        return list.toArray(new String[0]);
    }
}
