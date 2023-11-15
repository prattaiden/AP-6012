package assignment04;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import static assignment04.SortUtil.*;

public class MergeSortThresholdExperiment {
    public static void main(String[] args) {
        try (FileWriter fw = new FileWriter(new File("threshold_test.tsv"))) {

            int[] inputSizes = {1000, 5000, 10000, 50000, 100000}; // Example input sizes
            //int[] thresholdValues = {0, 10, 500, 1000, 5000, 50000, 100000}; // Example threshold values
            Comparator<Integer> comparator = Comparator.naturalOrder(); // Use natural ordering



                for (int size : inputSizes) {
                ArrayList<Integer> array = generateWorstCase(size);
                //System.out.println("Threshold Value: " + threshold);
                    //for (int threshold : thresholdValues) {
                    ArrayList<Integer> copy = new ArrayList<>(array);
                    long startTime = System.nanoTime();
                    SortUtil.setThreshold(500);
                    mergesort(copy, comparator);
                    long endTime = System.nanoTime();

                    long runningTime = endTime - startTime;
                    System.out.println("size: " + size + ", Running Time: " + runningTime + " ns");

                    fw.write(500 + "\t" + size + "\t" + runningTime + "\n"); // write to file.

                //}
                System.out.println();


            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}