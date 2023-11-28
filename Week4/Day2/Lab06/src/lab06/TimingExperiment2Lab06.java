package lab06;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class TimingExperiment2Lab06 {
    private final static int ITER_COUNT = 100;

    public static void main(String[] args) throws IOException {

        //HEAP version
        ArrayListPQ<Integer> aPq ;
        for (int N = 1000; N <= 100000; N *= 2) {
            ArrayList<Integer> arr = new ArrayList<>(N);

            for (int i = 1; i <= N; i++) {
                arr.add(i);
            }
            Collections.shuffle(arr);

            long totalTimeRemove = 0;
            aPq = new ArrayListPQ(arr);


            // Loop execution with heap-based data structure
           long startTime = System.nanoTime();
            while (!aPq.isEmpty()) {
                aPq.removeMin();
            }
            long endTime = System.nanoTime();

            totalTimeRemove = endTime - startTime;

            long avg = totalTimeRemove / ITER_COUNT;

            System.out.println(N + "\t" + avg);

        }

        //TREE version
//        TreeSetPQ treeSetPQ = new TreeSetPQ();
//        // Random random = new Random();
//        for (int N = 1000; N <= 100000; N *= 2) {
//            ArrayList<Integer> arr = new ArrayList<>(N);
//            for (int i = 1; i <= N; i++) {
//                arr.add(i);
//            }
//            Collections.shuffle(arr);
//
//            long duration = 0;
//            long avg;
//            for (int i = 0; i < ITER_COUNT; i++) {
//
//                for (int num : arr) {
//                    treeSetPQ.add(num);
//                }
//
//                long startTime = System.nanoTime();
//                while (!treeSetPQ.isEmpty()) {
//                    treeSetPQ.removeMin();
//                }
//                long endTime = System.nanoTime();
//
//                duration += endTime - startTime;
//            }
//            avg = duration / ITER_COUNT;
//
//            System.out.println(N + "\t" + avg);
//
//
//
//        }

        // Loop execution with tree-based data structure




// Loop execution with heap-based data structure

    }


}


