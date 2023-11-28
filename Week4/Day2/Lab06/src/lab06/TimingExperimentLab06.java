package lab06;

import java.io.IOException;
import java.util.*;


public class TimingExperimentLab06 {
    private final static int ITER_COUNT = 100;
        public static void main(String[] args) throws IOException {

            //HEAP versions
            for (int N = 1000; N <= 100000; N *= 2) {
                ArrayList<Integer> arr = new ArrayList<>(N);
                for (int i = 1; i <= N; i++) {
                    arr.add(i);
                }
                Collections.shuffle(arr);

                long totalTimeCreate = 0;
                for (int i = 0; i < ITER_COUNT; i++) {
                    long startTimeCreate = System.nanoTime();
                    ArrayListPQ aPq = new ArrayListPQ(arr);
                    long endTimeCreate = System.nanoTime();

                    totalTimeCreate += endTimeCreate - startTimeCreate;
                }
                long avg = totalTimeCreate / ITER_COUNT;

                System.out.println(N + "\t" + avg);

            }

            //TREE version
            TreeSetPQ treeSetPQ = new TreeSetPQ();
            // Random random = new Random();
            for (int N = 1000; N <= 100000; N *= 2) {
                ArrayList<Integer> arr = new ArrayList<>(N);
                for (int i = 1; i <= N; i++) {
                    arr.add(i);
                }
                Collections.shuffle(arr);
                long totalTimeTreeAdd = 0;
                long avg;
                for (int i = 0; i < ITER_COUNT; i++) {
                    //int element = random.nextInt(1000);
                    long startTimeTreeAdd = System.nanoTime();
                    for (int num : arr) {
                        treeSetPQ.add(num);
                    }
                    long endTimeTreeAdd = System.nanoTime();

                    totalTimeTreeAdd += endTimeTreeAdd - startTimeTreeAdd;
                }
                avg = totalTimeTreeAdd / ITER_COUNT;

                System.out.println(N + "\t" + avg);



            }

            // Loop execution with tree-based data structure
            long startTime = System.nanoTime();
            while (!treeSetPQ.isEmpty()) {
                treeSetPQ.removeMin();
            }
            long endTime = System.nanoTime();

            long duration = endTime - startTime;

// Loop execution with heap-based data structure

        }


    }
