package testing.from21to30;

import java.io.*;
import java.util.Set;
import java.util.Stack;

public class CorruptedXml_21_2 {
    public static void main(String ... args) throws IOException {
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
                        if (parseXmlTags(builder.toString())) {
                            return builder.toString();
                        }
                    } catch (Exception e) {
                        // игнорируем любые ошибки парсинга
                    }
                }
            }
            return "";
        }

        private static boolean parseXmlTags(String s){
            Stack<String> tagNames = new Stack<>();
            boolean res = true;

            for (int pos = 0; pos < s.length(); pos++){
                boolean isClosing = false;
                if (s.charAt(pos) == '<'){
                    pos++;
                    if (s.charAt(pos) == '/') {
                        isClosing = true;
                        pos++;
                    }
                    StringBuilder tagName = new StringBuilder();
                    while (pos < s.length() && s.charAt(pos) != '>' && s.charAt(pos) != '<' && s.charAt(pos) != '/'){
                        tagName.append(s.charAt(pos));
                        pos++;
                    }
                    if (pos >= s.length()){
                        res = false;
                        break;
                    } else if (s.charAt(pos) == '<' || s.charAt(pos) == '/'){
                        res = false;
                        break;
                    }
                    if (!isClosing){
                       tagNames.add(tagName.toString());
                    } else {
                        if (!tagNames.isEmpty()){
                            String openName = tagNames.pop();
                            res = openName.equals(tagName.toString());
                            if (!res) break;
                        } else {
                            res = false;
                            break;
                        }
                    }
                } else {
                    res = false;
                    break;
                }
            }
            if (!tagNames.isEmpty()) res = false;
            return res;
        }

    }
}
