package testing.from11to20;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Set;

public class ValueOfAnArithmeticExpression_18 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        String s = reader.readLine().replaceAll(" ", "");
        System.out.println(s);

        ExpressionParser parser = new ExpressionParser(s);

        System.out.println(Arrays.toString(stringToTokens(s)));

        reader.close();
        writer.close();
    }

    private static String[] stringToTokens(String s){
        int pos = 0;
        LinkedList<String> listOfTokens = new LinkedList<>();

        String pS = s.replaceAll(" ", "");

        Set<Character> brackets = Set.of('(', ')');
        Set<Character> operators = Set.of('-', '+', '*', '/');

        while (pos != s.length()){
            System.out.println(pos + "/" + s.length());
            char currCh = s.charAt(pos);
            if (brackets.contains(currCh)){
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
                    currCh = pS.charAt(pos);
                }
                listOfTokens.add(numberBuilder.toString());
            }
        }

        return listOfTokens.toArray(new String[0]);
    }

    private static class ExpressionParser{
        private String [] tokens; // пересмотреть на строку или пересмотреть токенизацию
        private int pos = 0;

        public ExpressionParser(String s){
            tokens = s.split("");
        }

        public Double parse(){
            return 0D;
        }

        public Double expression(){
            return 0D;
        }

        public Double term(){
            return 0D;
        }

        public Double factor(){
            return 0D;
        }
    }
}
