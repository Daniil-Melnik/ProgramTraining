package testing.from11to20;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ValueOfAnArithmeticExpression_18 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        String s = reader.readLine().replaceAll(" ", "");
        System.out.println(s);

        ExpressionParser parser = new ExpressionParser(s);

        reader.close();
        writer.close();
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
