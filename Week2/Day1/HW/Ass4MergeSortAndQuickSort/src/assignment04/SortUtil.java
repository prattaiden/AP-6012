package assignment04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class SortUtil<T>{

    private static int threshold_ = 0;

    private static int size_ = 0;




   // This method performs a mergesort on the generic ArrayList given as input.
    //DRIVER
    public static <T> void mergesort(ArrayList<T> arrayList, Comparator<? super T> comparator) {
        int beginning = 0;
        int end = arrayList.size() - 1;
        ArrayList<T> ret = new ArrayList<>(arrayList);


        mergesortRecursive(arrayList, beginning, end, comparator, ret);

    }

    private static <T> void mergesortRecursive(ArrayList<T> arrayList, int beginning, int end, Comparator<? super T> comparator, ArrayList<T> copy) {
       // determine the threshold
//        if (arrayList.size() < threshold_) {
//            //TODO find threshold
//            insertionSort(arrayList, comparator);
//            return;
//        }
//        if(end - beginning < 1){
//            return;
//        }

//        T[] right = (T[]) new Object[end - middle];
//
//        T[] left = (T[]) new Object[middle - beginning +1];
            if(beginning < end) {
                int mid = (beginning + end) / 2;

                //left side split
                mergesortRecursive(arrayList, beginning, mid, comparator, copy);

                //right side
                mergesortRecursive(arrayList, mid + 1, end, comparator, copy);

                merge(arrayList, beginning, mid, end, comparator, copy);
            }
    }

    public static <T> void merge(ArrayList<T> arrayList, int beginning, int middle, int end, Comparator<? super T> comparator, ArrayList<T> copy) {
        int leftIndex = beginning, rightIndex = middle + 1;
        int currentIndex = beginning;

        while (leftIndex <= middle && rightIndex <= end) {
            // If left is smaller
            if (comparator.compare(copy.get(leftIndex), copy.get(rightIndex)) <= 0) {
                // Update the current index to left
                arrayList.set(currentIndex, copy.get(leftIndex));
                // Update left index
                leftIndex++;
            } else { // Right side is bigger
                // Update the current index to right
                arrayList.set(currentIndex, copy.get(rightIndex));
                // Update right side
                rightIndex++;
            }
            currentIndex++;
        }

        // While left has more elements, copy over remaining elements to arrayList
        while (leftIndex <= middle) {
            arrayList.set(currentIndex, copy.get(leftIndex));
            currentIndex++;
            leftIndex++;
        }

        // While right has more elements, copy over remaining elements to arrayList
        while (rightIndex <= end) {
            arrayList.set(currentIndex, copy.get(rightIndex));
            currentIndex++;
            rightIndex++;
        }
    }
//     public static <T> void merge(ArrayList<T> arrayList, int beginning, int middle, int end, Comparator<? super T> comparator, ArrayList<T> copy) {
//
//
//
//         int leftIndex = beginning, rightIndex = middle+1;
//         int currentIndex = beginning;
//
//         while (leftIndex <= middle && rightIndex <= end) {
//             //if left is smaller
//             if (comparator.compare(copy.get(leftIndex), copy.get(rightIndex)) <= 0) {
//                 //update the current index to left
//                 arrayList.set(currentIndex, copy.get(leftIndex));
//                 //update left index
//                 leftIndex++;
//             } else { //right side is bigger
//                 //update the current index to right
//                 arrayList.set(currentIndex,copy.get(rightIndex));
//                 //update right side
//                 rightIndex++;
//             }
//             currentIndex ++;
//         }
//         //While left has more elements, copy over remaining elements to arraylist
//         while(leftIndex<= middle){
//             arrayList.set(currentIndex, copy.get(leftIndex));
//             currentIndex++;
//             leftIndex++;
//         }
//         //While right has more elements, copy over remaining elements to arraylist
//         while(rightIndex<end){
//             arrayList.set(currentIndex, copy.get(rightIndex));
//             currentIndex++;
//             rightIndex++;
//         }
//     }

     //------------------------------------- INSERTION SORT-------------------------------------\\
     private static <T> void insertionSort(ArrayList<T> arrayList, Comparator<? super T> comparator){
        int length = arrayList.size();


        for(int i = 1; i < length; i++){
            T current = arrayList.get(i);
            int j = i -1;
            T before = arrayList.get(j);
            //comparing the current element and the element before it
            //if this comparison result is >0, before is larger, need to be swapped
            while (j >= 0 && comparator.compare(before, current) > 0){
                //setting current position with j element, swap
                    //shifts elements to the right, adds key
                arrayList.set(j+1, arrayList.get(j));
                j--;
            }
            //two things:
                //first: if coming out of while loop, sets i to current, putting smaller element in front
                //second: if not in while loop, keeps elements where they are
            arrayList.set(j+1 , current);
        }
     }
     //------------------------------------------------------------------------------------------------\\




//    public static void quicksort(ArrayList, Comparator<? super T>){}
//
//    public static ArrayList<Integer> generateBestCase(int size){}
public static ArrayList<Integer> generateAverageCase(int size) {
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
//
//    public static ArrayList<Integer> generateWorstCase(int size){}
//}

}