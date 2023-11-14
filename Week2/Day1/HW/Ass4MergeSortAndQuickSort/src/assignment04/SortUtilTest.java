package assignment04;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Comparator;

import static assignment04.SortUtil.mergesort;


import static org.junit.jupiter.api.Assertions.*;

class SortUtilTest {

    @Test
    public void testMergeSort(){
            // Create test data
            ArrayList<Integer> arrayList = new ArrayList<>();
            arrayList.add(5);
            arrayList.add(2);
            arrayList.add(8);
            arrayList.add(1);
//            arrayList.add(9);
//            arrayList.add(3);

            // Call the mergesort method
            mergesort(arrayList, Integer::compareTo);

            for(int i : arrayList){
                System.out.println(i);
            }

            // Verify the sorting
            for (int i = 0; i < arrayList.size() - 1; i++) {
                assert (arrayList.get(i) <= arrayList.get(i + 1));
            }
        }

    }
