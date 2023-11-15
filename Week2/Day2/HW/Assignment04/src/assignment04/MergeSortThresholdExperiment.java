package assignment04;

import java.util.ArrayList;
import java.util.Comparator;

import static assignment04.SortUtil.*;

public class MergeSortThresholdExperiment {
    public static void main(String[] args) {
        ArrayList<Integer> array = generateAverageCase(10000); // Generate a large random array
        Comparator<Integer> comparator = Comparator.naturalOrder(); // Use natural ordering

        int[] thresholdValues = {2, 4, 8, 16, 32}; // Experiment with different threshold values

        for (int threshold : thresholdValues) {
            long startTime = System.nanoTime();
            setThreshold(threshold);
            mergesort(array, comparator);

            long endTime = System.nanoTime();
            long executionTime = endTime - startTime;

            System.out.println("Threshold: " + threshold + ", Execution Time: " + executionTime + " ns " + (executionTime/ 1_000_000_000.0) + " seconds");
        }
    }
}