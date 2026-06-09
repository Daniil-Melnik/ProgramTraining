package testing.from11to20;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class SpeleologistWay_15 {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(reader.readLine());

        Set<CubeElement> winElements = new HashSet<>();
        Set<CubeElement> visited = new HashSet<>();
        Map<CubeElement, Integer> costMap = new HashMap<>();
        Queue<CubeElement> queue = new ArrayDeque<>();
        CubeElement startElement = null;

        ArrayList<ArrayList<ArrayList<String>>> cube = new ArrayList<>(n);
        for (int i = 0; i < n; i++) cube.add(new ArrayList<>(n));

        for (int z = 0; z < n; z++){
            reader.readLine();
            for (int y = 0; y < n; y++){
                cube.get(z).add(new ArrayList<>(Arrays.stream(reader.readLine().split("")).toList()));
                if (startElement == null) if (cube.get(z).get(y).contains(String.valueOf("S"))) startElement = new CubeElement(cube.get(z).get(y).indexOf("S"), y, z);
            }
        }

        /*for (int z = 0; z < n; z++){
            System.out.println();
            for (int y = 0; y < n; y++) System.out.println(cube.get(z).get(y));
        }*/

        getWinElements(winElements, cube.get(0));
        //System.out.println(winElements);
        //System.out.println(startElement);
        //System.out.println();

        queue.add(startElement);
        costMap.put(startElement, 0);
        boolean isInWin = false;
        CubeElement current = null;
        while (!queue.isEmpty() && !isInWin){
            current = queue.poll();
            isInWin = winElements.contains(current);
            if (!isInWin){
                //System.out.println(current);
                visited.add(current);
                updateQueue(queue, current, costMap, visited, cube);
                //.out.println(queue);
            }
        }

        writer.write(String.valueOf(costMap.get(current)));

        //System.out.println(startElement);

        reader.close();
        writer.close();
    }

    private static void updateQueue(Queue<CubeElement> queue,
                                    CubeElement current,
                                    Map<CubeElement, Integer> costs,
                                    Set<CubeElement> visited,
                                    ArrayList<ArrayList<ArrayList<String>>> cube){

        int n = cube.size();

        if ((current.getZ() + 1 < n) && (cube.get(current.getZ() + 1).get(current.getY()).get(current.getX()).equals("."))){
            CubeElement el = new CubeElement(current.getX(), current.getY(), current.getZ() + 1);
            if (!visited.contains(el) && !queue.contains(el)){
                queue.add(el);
                costs.put(el, costs.get(current) + 1);
            }
        }

        if ((current.getZ() - 1 >= 0) && (cube.get(current.getZ() - 1).get(current.getY()).get(current.getX()).equals("."))){
            CubeElement el = new CubeElement(current.getX(), current.getY(), current.getZ() - 1);
            if (!visited.contains(el) && !queue.contains(el)){
                queue.add(el);
                costs.put(el, costs.get(current) + 1);
            }
        }

        if ((current.getY() + 1 < n) && (cube.get(current.getZ()).get(current.getY() + 1).get(current.getX()).equals("."))){
            CubeElement el = new CubeElement(current.getX(), current.getY() + 1, current.getZ());
            if (!visited.contains(el) && !queue.contains(el)){
                queue.add(el);
                costs.put(el, costs.get(current) + 1);
            }
        }

        if ((current.getY() - 1 >= 0) && (cube.get(current.getZ()).get(current.getY() - 1).get(current.getX()).equals("."))){
            CubeElement el = new CubeElement(current.getX(), current.getY() - 1, current.getZ());
            if (!visited.contains(el) && !queue.contains(el)){
                queue.add(el);
                costs.put(el, costs.get(current) + 1);
            }
        }

        if ((current.getX() + 1 < n) && (cube.get(current.getZ()).get(current.getY()).get(current.getX() + 1).equals("."))){
            CubeElement el = new CubeElement(current.getX() + 1, current.getY(), current.getZ());
            if (!visited.contains(el) && !queue.contains(el)){
                queue.add(el);
                costs.put(el, costs.get(current) + 1);
            }
        }

        if ((current.getX() - 1 >= 0) && (cube.get(current.getZ()).get(current.getY()).get(current.getX() - 1).equals("."))){
            CubeElement el = new CubeElement(current.getX() - 1, current.getY(), current.getZ());
            if (!visited.contains(el) && !queue.contains(el)){
                queue.add(el);
                costs.put(el, costs.get(current) + 1);
            }
        }
    }

    private static void getWinElements(Set<CubeElement> winElements, ArrayList<ArrayList<String>> topLevel){
        int n = topLevel.size();
        for (int y = 0; y < n; y++){
            for (int x = 0; x < n; x++){
                if (topLevel.get(y).get(x).equals(".")) winElements.add(new CubeElement(x, y, 0));
            }
        }
    }

    private static class CubeElement{
        private int x;
        private int y;
        private int z;

        public CubeElement(int aX, int aY, int aZ){
            x = aX;
            y = aY;
            z = aZ;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        @Override
        public String toString() {
            return String.format("<x%s/y%s/z%s>", x, y, z);
        }

        @Override
        public boolean equals(Object obj) {
            CubeElement el = (CubeElement) obj;
            return
                    this.getX() == el.getX()
                    && this.getY() == el.getY()
                    && this.getZ() == el.getZ();
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }
    }
}
