package testing.from11to20;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;
import java.util.stream.Collectors;

public class Conveyor_17 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(reader.readLine());

        List<List<Double>> tests = new ArrayList<>(n);

        for (int i = 0; i < n; i++) tests.add(Arrays.stream(reader.readLine().split(" ")).map(Double::valueOf).collect(Collectors.toCollection(ArrayList::new)));
        for (int i = 0; i < n; i++) tests.get(i).removeFirst();

        List<List<Double>> sortedTests = new ArrayList<>(n);
        for (List<Double> el : tests) sortedTests.add(List.copyOf(el).stream().sorted().toList());
        //for (int i = 0; i < n; i++) sortedTests.set(i, sortedTests.get(i).reversed());
        //System.out.println(sortedTests.getFirst());

        /*System.out.println(tests.getFirst());
        System.out.println(sortedTests.getFirst());*/

        for (List<Double> l : tests) System.out.println(sortTest(l));

        reader.close();
        writer.close();
    }

    private static int sortTest(List<Double> originalTest){
        int result = -1;

        Queue<Double> A = new ArrayDeque<>(originalTest);
        Stack<Double> storage = new Stack<>();
        List<Double> B = new LinkedList<>();

        Stack<Double> addStack = new Stack<>();
        List<Double> sortedTest = List.copyOf(originalTest).stream().sorted().toList();
        for (int i = sortedTest.size() - 1; i >= 0; i--) {

            addStack.add(sortedTest.get(i));
            //System.out.println(addStack);
        }
        //addStack.addAll(sortedTest);
        //System.out.println(addStack);

        double expected = addStack.peek();
        while (!addStack.isEmpty()){
            if (!A.isEmpty() && A.peek() == expected) { // сразу в B
                B.add(A.poll());
                addStack.pop();
                if (!addStack.isEmpty()){
                    expected = addStack.peek();
                }
            }
            else if(!storage.isEmpty() && !A.isEmpty() && A.peek() != expected && storage.peek() == expected) {
                B.add(storage.pop());
                addStack.pop();
                if (!addStack.isEmpty()){
                    expected = addStack.peek();
                }
            } else if (!A.isEmpty() && A.peek() != expected) storage.add(A.poll());
            else if (A.isEmpty() && !storage.isEmpty() && storage.peek() == expected) {
                B.add(storage.pop());
                addStack.pop();
                if (!addStack.isEmpty()){
                    expected = addStack.peek();
                }
            } else {
                //System.out.println("OOPS!");
                result = 0;
                break;
            }
        }

        if (result == -1) result = sortedTest.equals(B) ? 1 : 0;
        return result;
    }
}
