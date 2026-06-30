package testing.from21to30;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/*
* Две кучи:
*  - куча с свободными тупиками (приоритет отдаётся тупику с меньшим индексом => он будет в голове)
*  - куча с занятыми тупиками в виде пары: индекс тупика + время выезда из него поезда (врем освобождения тупика). Приоритет отдаётся элементу с меньшим временем освобождения.
*
* Выполняется проходка по входящим строкам с поездами:
*  - сначала из кучи занятых путей удаляем все пути (перемещаем их в кучу открытых), время освобождения которых СТРОГО меньше времени прибытия поезда
*  - потом, если куча открытых не пуста, берём её головной элеиент-путь (с наименьшим номером) и перемещаем его с временем освобожения в кучу занятых путей
*  -- если куча свободных путей пуста, то получили проблему невозможности принять состав
* */

public class DeadEnds_24 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        PriorityQueue<CloseEntry> closed = new PriorityQueue<>();
        PriorityQueue<Integer> open = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 < o2 ? -1 : o1 > o2 ? 1 : 0;
            }
        });

        int [] lens = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::valueOf).toArray();

        int nEnds = lens[0];
        int nTrain = lens[1];

        StringBuilder res = new StringBuilder();

        for (int i = 0; i < nEnds; i++) open.add(i);

        for (int i = 0; i < nTrain; i++){
            int [] times = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::valueOf).toArray();
            int arrive = times[0];
            int depart = times[1];

            while (!closed.isEmpty() && closed.peek().timeExit < arrive){
                open.add(closed.poll().index);
            }

            if (!open.isEmpty()){
                int goal = open.poll();
                closed.add(new CloseEntry(goal, depart));
                res.append(goal + 1).append('\n');
            } else {
                res = new StringBuilder(0 + " " + (i + 1));
                break;
            }
        }

        writer.write(res.toString());

        reader.close();
        writer.close();
    }

    private static class CloseEntry implements Comparable<CloseEntry>{
        private int index;
        private int timeExit;

        public CloseEntry(int i, int t){
            index = i;
            timeExit = t;
        }

        @Override
        public int compareTo(CloseEntry o) {
            return Integer.compare(this.timeExit, o.timeExit);
        }

        @Override
        public String toString() {
            return timeExit + " " + index;
        }
    }
}
