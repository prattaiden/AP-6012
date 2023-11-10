package assignment03;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TimingEXPAdd {
    private static final int ITER_COUNT = 101;

   public static BinarySearchSet<Integer> timingSet = new BinarySearchSet<>();
    public static List<Integer> elementsToAdd = Arrays.asList(2, 4, 30, 8, 10, 100, 3, 56); //eight elements

    public static void main(String[] args) {
        // you spin me round baby, right round
        long startTime = System.nanoTime();
        while (System.nanoTime() - startTime < 1_000_000_000) ;

        try (FileWriter fw = new FileWriter(new File("BinarySearchSetTestAdd.tsv"))) { // open up a file writer so we can write
            // to file.

            timingSet.addAll(elementsToAdd);
//
            for (int run = 1; run < ITER_COUNT; run++) { // This is used as the exponent to calculate the size of the set.
//
                // Do the experiment multiple times, and average out the results
                long totalTime = 0;

                for (int iter = 0; iter < ITER_COUNT; iter++) {
                    // SET UP!

                    // TIME IT!
                    long start = System.nanoTime();
                    timingSet.add(50);
                    long stop = System.nanoTime();
                    timingSet.remove(50);
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
