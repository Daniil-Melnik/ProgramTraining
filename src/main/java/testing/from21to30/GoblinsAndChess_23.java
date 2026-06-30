package testing.from21to30;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Deque;

public class GoblinsAndChess_23 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(reader.readLine());

        /*
        * Две очереди: левая половина (голова очереди) и правая половина (хвост очереди)
        * Всего три состояния по длинам очередей: sL == sR, sL > sR, sL < sR
        * Добавление превилигированного в центр либо в конец левой (если sL == sR) либо в начало правой (если sL > sR, после центра)
        * Добавление обычного: добавляем последним в правую очередь. Если правая становится длинней левой (sL < sR) то из головы правой перекладываем в хвост левой
        * При приходе '-' удалить головной в левой. Если левая стала короче правой, перенести головной из правой в хвост левой
        * */

        Deque<String> left = new ArrayDeque<>();
        Deque<String> right = new ArrayDeque<>();

        StringBuilder res = new StringBuilder();


        for (int i = 0; i < N; i++){
            String [] input = reader.readLine().split(" ");

            if (input[0].equals("-")){
                res.append(left.pollFirst()).append('\n');
                if (left.size() < right.size()) left.addLast(right.pollFirst());
            } else {
                String sign = input[0];
                String index = input[1];

                switch (sign){
                    case "+":{
                        right.addLast(index);
                        if (right.size() > left.size()){
                            left.addLast(right.pollFirst());
                        }
                        break;
                    }
                    case "*":{
                        if (left.size() == right.size()){
                            left.addLast(index);
                        } else if (left.size() > right.size()){
                            right.addFirst(index);
                        }
                        break;
                    }
                }
            }

        }

        writer.write(res.toString());

        reader.close();
        writer.close();
    }
}
