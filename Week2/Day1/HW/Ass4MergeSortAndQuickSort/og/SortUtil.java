package assignment04;

import java.util.*;

public class SortUtil<T> {

    private static int threshold_;

    private static int size;

    public static void setThreshold(int threshold){
        threshold_=threshold;
    }
    /**
     *
     * @param array
     * @param comparator
     * @param <T>
     */

    //DRIVER METHOD
    public static <T> void mergesort(ArrayList<T> array, Comparator<? super T> comparator) {
        int start = 0;
        int end = array.size() - 1;

        mergeSortRecursive(array, start, end, comparator);
    }

    /**
     *
     * @param array
     * @param start
     * @param end
     * @param comparator
     * @param <T>
     */
    //MERGE-SORT METHOD
    private static <T> void mergeSortRecursive(ArrayList<T> array, int start, int end, Comparator<? super T> comparator) {
        threshold_ = 6;
        //if the array length is less than the threshold, do insertion sort
        if (end - start <= threshold_) {
            insertionSort(array, comparator);

        } else { //merge-sort
            //Get the middle index
            int middle = (start + end) / 2;
            //sort left
            mergeSortRecursive(array, start, middle, comparator);
            //sort right
            mergeSortRecursive(array, middle + 1, end, comparator);
            //merge
            merge(array, start, middle, end, comparator);
        }

    }

    /**
     *
     * @param array
     * @param start
     * @param middle
     * @param end
     * @param comparator
     * @param <T>
     */
    //MERGE METHOD
    public static <T> void merge(ArrayList<T> array, int start, int middle, int end, Comparator<? super T> comparator) {
        //Initalize two temp arrays for left and right side of OG array
        T[] leftArray = (T[]) new Comparable[middle - start + 1];
        T[] rightArray = (T[]) new Comparable[end - middle];

        //Copy into temp arrays
        for (int i = 0; i < leftArray.length; i++) {
            leftArray[i] = array.get(start + i);
        }

        for (int i = 0; i < rightArray.length; i++) {
            rightArray[i] = array.get(middle + 1 + i);
        }

        //initialize indices
        int leftIndex = 0, rightIndex = 0;

        int currentIndex = start;

        //While L/R are smaller than their array length
        while (leftIndex < leftArray.length && rightIndex < rightArray.length) {
            //If left index is less than right index
            if (comparator.compare(leftArray[leftIndex], rightArray[rightIndex]) < 0) {
                //put the smaller value into the current OG array position
                array.set(currentIndex, leftArray[leftIndex]);
                //moves to compare
                leftIndex++;
            } else {//else right is smaller
                //put the smaller value into the current OG array position
                array.set(currentIndex, rightArray[rightIndex]);
                //moves to compare
                rightIndex++;
            }
            //updating current index in OG array
            currentIndex++;
        }
        //while left has more elements
        while (leftIndex < leftArray.length) {
            //copy those into OG array + increment
            array.set(currentIndex++, leftArray[leftIndex++]);
        }
        //while right has more elements
        while (rightIndex < rightArray.length) {
            //copy those into OG array + increment
            array.set(currentIndex++, rightArray[rightIndex++]);
        }
    }

    /**
     *
     * @param arrayList
     * @param comparator
     * @param <T>
     */
    //INSERTION SORT
    private static <T> void insertionSort(ArrayList<T> arrayList, Comparator<? super T> comparator) {
        int length = arrayList.size();

        for (int i = 1; i < length; i++) {
            T current = arrayList.get(i);
            int j = i - 1;

            // Keep moving elements to the right until the correct position is found
            while (j >= 0 && comparator.compare(arrayList.get(j), current) > 0) {
                // Shift elements to the right
                arrayList.set(j + 1, arrayList.get(j));
                j--;
            }

            // Set the correct position for the current element
            arrayList.set(j + 1, current);
        }
    }

//--------------

    /**
     *
     * @param arrayList
     * @param comparator
     * @param <T>
     */
    public static <T> void quicksort(ArrayList<T> arrayList, Comparator<? super T> comparator) {
        int start = 0;
        //int end = arrayList.size() - 1;
        int end = arrayList.size() -1;
        quicksortRecurse(arrayList, start, end, comparator);
    }

    /**
     *
     * @param arrayList
     * @param startIndex
     * @param endIndex
     * @param comparator
     * @param <T>
     */
    private static <T> void quicksortRecurse(ArrayList<T> arrayList, int startIndex, int endIndex, Comparator<? super T> comparator) {
        // verify that the start and end index have not overlapped
        if(endIndex - startIndex < 1)
        {
            return;
        }
        // Calculating the Pivot Index
        int pivotIndex = (startIndex + endIndex)/2;
        //Calculate the pivotPosition
        int pivotPosition = partition(arrayList, startIndex, endIndex, pivotIndex, comparator);
        // sort the left sub-array
        quicksortRecurse(arrayList, startIndex, pivotPosition, comparator);
        // sort the right sub-array
        quicksortRecurse(arrayList, pivotPosition + 1, endIndex, comparator);
    }

    /**
     *
     * @param arrayList
     * @param startIndex
     * @param endIndex
     * @param pivotIndex
     * @param comparator
     * @return
     * @param <T>
     */
    //PARTITION
    //returns the index value of the pivot
    public static <T> int partition(ArrayList<T> arrayList, int startIndex, int endIndex, int pivotIndex, Comparator<? super T> comparator) {
        int left = startIndex;
        int right = endIndex - 1; //true endIndex holds the pivot
        //Get pivot value
        var pivotValue = arrayList.get(pivotIndex);

        //Swap the pivot to the end of the array
        swap(arrayList, endIndex, pivotIndex);

        //Go until left and right meet on same index
        while (left <= right) {
            //check if left is smaller than pivot
            while (comparator.compare(arrayList.get(left), pivotValue) < 0) {
                left++; //keep moving towards pivot ([ L->   arr    ])
            }
            //check if right is bigger than pivot
            while (right >= startIndex && comparator.compare(arrayList.get(right), pivotValue) >= 0) {
                right--; //keep moving towards pivot ([   arr    <-R])
            }
            //if left is smaller than right swap
            if (left < right) {
                swap(arrayList, left, right);
            }
        }
        //Swap pivot to its rightful index
            //swap left (actually a big value) with the endIndex (which contains the pivot)
        swap(arrayList, left, endIndex);
        System.out.println(arrayList);
        System.out.println("left" + left);
        return left ;
    }


    /**
     *
     * @param arrayList
     * @param a
     * @param b
     * @param <T>
     */
    public static <T> void  swap(ArrayList<T> arrayList, int a, int b){
        T temp = arrayList.get(a);
        arrayList.set(a,arrayList.get(b));
        arrayList.set(b,temp);

    }

    /**
     *
     * @param size
     * @return
     */
    //This method generates and returns an ArrayList of integers 1 to size in ascending order.
    public static ArrayList<Integer> generateBestCase(int size){
        ArrayList<Integer> bestCaseArray = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            bestCaseArray.add(i);
        }
        return bestCaseArray;
    }

    /**
     *
     * @param size
     * @return
     */
//This method generates and returns an ArrayList of integers 1 to size in permuted order (i,e., randomly ordered).
    public static ArrayList<Integer> generateAverageCase(int size){
        Random rand = new Random();
        ArrayList<Integer> avgCaseArray = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            avgCaseArray.add(i);
        }
        for (int i = 0; i < size; i++) {
            int j = rand.nextInt(size);
            int temp = avgCaseArray.get(i);
            avgCaseArray.set(i, avgCaseArray.get(j));
            avgCaseArray.set(j, temp);
        }
        return avgCaseArray;
    }

    /**
     *
     * @param size
     * @return
     */
    //This method generates and returns an ArrayList of integers 1 to size in descending order.
    public static ArrayList<Integer> generateWorstCase(int size){
        ArrayList<Integer> worstCaseArray = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            worstCaseArray.add(i);
        }
        Collections.sort(worstCaseArray, Collections.reverseOrder());
        return worstCaseArray;

    }


}