package testing.from21to30;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/*
Используем двустороннюю очередь
Проходимся по каждому элементу массива:
- в конце удаляем все индексы элементы под которыми большие текущего (так ищем кандидата в наименьшие)
- добавляем в конец текущий индекс
- в начале очереди выбрасываем индексы вышедшие за пределы окна

После такой итерации обработки:
- первый элемент в очереди - наименьший в окне
 */

public class MinimumOfTheSegment_22 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int[] lens = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::valueOf).toArray();
        int[] array = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::valueOf).toArray();

        int arrLen = lens[0];
        int k = lens[1];

        Deque<Integer> deque = new ArrayDeque<>();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < arrLen; i++) {
            while (!deque.isEmpty() && array[deque.peekLast()] >= array[i]) {
                deque.pollLast();
            }
            deque.addLast(i);
            if (!deque.isEmpty())
                while (deque.getFirst() < i - k + 1) {
                    deque.pollFirst();
                }

            if (i >= k - 1) {
                result.append(array[deque.getFirst()]).append('\n'); // должна быть одна операция ввода/вывода
            }
        }
        writer.write(result.toString());
        reader.close();
        writer.close();
    }
}
