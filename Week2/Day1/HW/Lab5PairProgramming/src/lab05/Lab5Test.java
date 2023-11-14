package lab05;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

import static lab05.Lab5.findMedian;
import static lab05.Lab5.findMedianComparator;
import static org.junit.jupiter.api.Assertions.*;

class Lab5Test {

    @Test
    public void testMedian(){

        Object[] oddNums = new Object[]{1,2,46,7,666,8,5};
        Object[] evenNums = new Object[]{1,2,3,4,5,6};
        Object[] empty = new Object[0];

        assertEquals(7, findMedian(oddNums));
        assertEquals(4, findMedian(evenNums));
        assertThrows(NoSuchElementException.class,() ->{
            findMedian(empty);
        });
    }

    public class MyDescendingComparator implements Comparator<Integer>{
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2.compareTo(o1);
        }
    }

    @Test
    public void testComparatorCustom() {
        MyDescendingComparator myComp = new MyDescendingComparator();

        Integer[] oddNums = new Integer[]{1,2,46,7,666,8,5};
        Integer[] evenNums = new Integer[]{1,2,3,4,5,6};
        Integer[] empty = new Integer[0];
        assertEquals(7, findMedianComparator(oddNums, myComp));
        assertEquals(3, findMedianComparator(evenNums, myComp));
        assertThrows(NoSuchElementException.class,() ->{
            findMedianComparator(empty, myComp);
        });

    }
}