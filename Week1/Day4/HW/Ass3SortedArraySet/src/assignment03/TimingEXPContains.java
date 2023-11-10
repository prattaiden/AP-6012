package assignment03;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TimingEXPContains {

    private static final int ITER_COUNT = 101;

    public static void main(String[] args) {
        // you spin me round baby, right round
        long startTime = System.nanoTime();
        while (System.nanoTime() - startTime < 1_000_000_000) ;

        try (FileWriter fw = new FileWriter(new File("BinarySearchSetTest.tsv"))) { // open up a file writer so we can write
            // to file.
//
            for (int run = 1; run < ITER_COUNT; run++) { // This is used as the exponent to calculate the size of the set.
//

                // Do the experiment multiple times, and average out the results
                long totalTime = 0;

                for (int iter = 0; iter < ITER_COUNT; iter++) {
                    // SET UP!
                    BinarySearchSet<Integer> timingSet = new BinarySearchSet<>();
                    List<Integer> elementsToAdd = Arrays.asList(2, 4, 30, 8, 10, 100, 3, 56); //eight elements
                    timingSet.addAll(elementsToAdd);

                    // TIME IT!
                    long start = System.nanoTime();
                    timingSet.contains(4);
                    long stop = System.nanoTime();
                    totalTime += stop - start;
                }
                double averageTime = totalTime / (double) ITER_COUNT;
                System.out.println("average time for run: " + run + " is "+ averageTime + " nano seconds."); // print to console
                fw.write(run + "\t" + averageTime + "\n"); // write to file.
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

