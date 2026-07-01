package testing.from21to30;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Avto_26 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int [] ints = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::valueOf).toArray();

        int N = ints[0]; // кол-во машинок всего
        int K = ints[1]; // макс. колво машинок внизу
        int P = ints[2]; // колво машинок в ленте приёма

        List<Integer> carTakingsList = new LinkedList<>();
        //Map<Integer, Boolean> carsOnFloorFlags = new HashMap<>();
        boolean [] carsOnFloorFlags = new boolean[N];
        int [] actual = new int[N];

        Map<Integer, Stack<Integer>> carTakingsMap = new HashMap<>(N);
        for (int i = 0; i < N; i++){
            carTakingsMap.put(i, new Stack<>()); // базовое заполнение карты взятий машинок по времени
            //carsOnFloorFlags.put(i, false);
            carsOnFloorFlags[i] = false;
        }

        for (int i = 0; i < P; i++){
            carTakingsList.add(Integer.parseInt(reader.readLine()) - 1); // список взятий машинок
        }

        reader.close();

        int [] carTakingsArr = carTakingsList.stream().mapToInt(Integer::valueOf).toArray();

        for (int i = P - 1; i >= 0; i--){
            carTakingsMap.get(carTakingsArr[i]).add(i); // добавление в карту взятий пометок времени взятия
        }

        //carTakingsMap.values().forEach(Stack::pop); // зачистка первых взятий для каждой машинки
        carTakingsMap.values().forEach(v -> {if (!v.isEmpty()) v.pop();} );

        PriorityQueue<CarEntry> carsOnFloor = new PriorityQueue<>(K); // очередь с приоритетом (куча) - машинка с наибольшим временем следующего взятия - в голове
        // (жадность до времени)

        int res = 0;
        int cnt = 0;

        for (int car : carTakingsArr){
            //if (carsOnFloorFlags.get(car)){ // если эта машинка уже на полу, ничего не делаем
            if (carsOnFloorFlags[car]){ // если эта машинка уже на полу, ничего не делаем
                //System.out.println("машинка уже на полу: " + (car + 1));

                // обновляем следующее использование для машинки на полу

                /*carsOnFloor.stream() // - ломает структуру кучи, не обновляет в ней порядок
                        .filter(e -> e.carID == car)
                        .forEach(c -> c.setNextUsing(carTakingsMap.get(car).isEmpty() ? P + 1 : carTakingsMap.get(car).pop()));*/

                //carsOnFloor.removeIf(e -> e.carID == car); // НЕ ломает структуру кучи
                int nU = carTakingsMap.get(car).isEmpty() ? P + 1 : carTakingsMap.get(car).pop();
                carsOnFloor.add(new CarEntry(car, nU));
                //System.out.println(carsOnFloor);
                actual[car] = nU;
                continue;
            } else if (cnt < K){ // на полу ещё есть мето под машинки => спускаем машинку с полки
                res += 1;
                cnt += 1;
                //System.out.println("снять машинку: " + (car + 1));
                int nU = carTakingsMap.get(car).isEmpty() ? P + 1 : carTakingsMap.get(car).pop();
                carsOnFloor.add(new CarEntry(car, nU));
                carsOnFloorFlags[car] = true;
                actual[car] = nU;
            } else if (cnt == K) { // если места на полу нет => выбираем на полу машинку, которая дольше всего будет не нужна
                res += 1;
                CarEntry removedCar = carsOnFloor.poll(); // убираем машинку с пола на полку

                while (true){
                    if (!carsOnFloorFlags[removedCar.carID]){
                        removedCar = carsOnFloor.poll();
                        continue;
                    }

                    if (actual[removedCar.carID] != removedCar.nextUsing){
                        removedCar = carsOnFloor.poll();
                        continue;
                    }
                    else {
                        break;
                    }
                }

                carsOnFloorFlags[removedCar.carID] = false;
                //System.out.println(String.format("поднять машинку - %s, снять машинку - %s", removedCar.carID + 1, car + 1));
                int nU = carTakingsMap.get(car).isEmpty() ? P + 1 : carTakingsMap.get(car).pop();
                carsOnFloor.add(new CarEntry(car, nU)); // спускаем машинку на пол
                // к машинке приписано следующее время взятия
                carsOnFloorFlags[car] = true;
                actual[car] = nU;
            }
            //System.out.println(carsOnFloor);
        }

        //System.out.println(carTakingsMap);
        writer.write(String.valueOf(res));
        writer.close();
    }


    private static class CarEntry implements Comparable {
        private int carID;
        private int nextUsing;

        public CarEntry(int id, int nU) {
            carID = id;
            nextUsing = nU;
        }

        public void setNextUsing(int nU){
            nextUsing = nU;
        }

        @Override
        public int compareTo(Object o) {
            CarEntry obj = (CarEntry) o;
            return Integer.compare(obj.nextUsing, this.nextUsing);
        }

        @Override
        public String toString() {
            return String.format("id%s-nU%s", carID + 1, nextUsing);
        }

        @Override
        public boolean equals(Object o) {
            CarEntry obj = (CarEntry) o;
            return this.carID == obj.carID;
        }
    }
}
