package testing.from21to30;

import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class CorruptedXml_21 {

    public static void main(String ... args) throws IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        String input = reader.readLine();

        //writer.write(Arrays.toString(stringToTokens(input)));

        Parser parser = new Parser();
        System.out.println(parser.compile(input));

        reader.close();
        writer.close();
    }


    private static class Parser{
        private static final Set<Character> enabledChars = Set.of(
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                'u', 'v', 'w', 'x', 'y', 'z', '<', '>', '/'
        );
        public String compile(String input) {
            for (int i = 0; i < input.length(); i++) {
                StringBuilder builder = new StringBuilder(input);
                for (char s : enabledChars) {
                    builder.setCharAt(i, s);
                    try {
                        String[] tokens = stringToTokens(builder.toString());
                        if (parseXmlTags(tokens)) {
                            return builder.toString();
                        }
                    } catch (Exception e) {
                        // игнорируем любые ошибки парсинга
                    }
                }
            }
            return "";
        }

        private static boolean parseXmlTags(String [] tokens){
            Stack<String> tagNames = new Stack<>();

            for (String curr : tokens) {
                if (curr.charAt(1) != '/') {
                    tagNames.add(curr.substring(1, curr.length() - 1));
                    //System.out.println(tagNames);
                } else {
                    if (!tagNames.isEmpty()) {
                        String closeName = curr.substring(2, curr.length() - 1);
                        String openName = tagNames.pop();
                        if (!closeName.equals(openName))
                            throw new IllegalStateException("parseXmlTags - Имена открывающего тега и закрывающего тега не совпадают");
                    } else
                        throw new IllegalStateException("parseXmlTags - Встречен закрывающий тег, а открывающего нет");
                }
            }
            if (!tagNames.isEmpty()) throw new IllegalStateException("parseXmlTags - Стек не пуст");
            return true;
        }

    }

    private static String [] stringToTokens(String s) throws IllegalStateException{
        List<String> tokwnsList = new ArrayList<>();

        String pS = s;
        //System.out.println(pS);

        for (int pos = 0; pos < pS.length(); pos++){
            if (pS.charAt(pos) == '<'){
                StringBuilder token = new StringBuilder("<");
                pos++;
                if (pS.charAt(pos) == '/'){
                    token.append("/");
                    pos++;
                }
                while(pos < pS.length() && pS.charAt(pos) != '<' && pS.charAt(pos) != '/' && pS.charAt(pos) != '>'){
                    token.append(pS.charAt(pos));
                    pos++;
                }
                //System.out.println(token);
                if (pos >= pS.length()) throw new IllegalStateException("stringToTokens - Ожидался '>' но вышли за строку");
                else {
                    if (pS.charAt(pos) == '<') throw new IllegalStateException("stringToTokens - Получили '<' внутри описания тега");
                    if (pS.charAt(pos) == '/') throw new IllegalStateException("stringToTokens - Получили '/' внутри описания тега");
                    token.append('>');
                    tokwnsList.add(token.toString());
                }
            } else throw new IllegalStateException("stringToTokens - Ожидалось '<'");
        }
        return tokwnsList.toArray(new String[0]);
    }
}
