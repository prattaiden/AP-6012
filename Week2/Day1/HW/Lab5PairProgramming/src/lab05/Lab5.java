package lab05;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class Lab5<T> implements Comparable<T> {

    public static <T> T findMedian (T[] array) {
        T median = null;
        Integer length = array.length;
        if(length == 0){
            throw new NoSuchElementException("array was empty");
        }
        Arrays.sort(array);//sorts in order using compareTo
          median = array[length/2];
        System.out.println(median);
        return median;

    }


    public static <T> T findMedianComparator (T[] array, Comparator<T> comparator){
        T median = null;
        Integer length = array.length;

        if(length == 0){
            throw new NoSuchElementException("array was empty");
        }

        //Sorting based on comparator passed in
        for (int i = 0; i < array.length - 1; i++) {
            int j, minIndex;
            for (minIndex = i, j = i + 1; j < array.length; j++)
                if (comparator.compare(array[j], array[minIndex]) < 0)
                    minIndex = j;
            T temp = array[i];
            array[i] = array[minIndex];
            array[minIndex] = temp;
        }

        median = array[length/2];
        System.out.println(median);

        return median;
    }



    @Override
    public int compareTo(T o) {
        return this.compareTo(o);
    }


}
