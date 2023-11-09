package assignment03;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchSetTest {


    @Test
    public void testAddMethod(){
        BinarySearchSet<Integer> testAdd = new BinarySearchSet();
        testAdd.add(1);
        testAdd.add(2);

        assertEquals(testAdd.first() , 1);
        assertEquals(testAdd.contains(1), true);
        assertEquals(testAdd.last(), 2);
    }

    BinarySearchSet<Integer> integerSet = new BinarySearchSet<>();
    @Test
    public void testAdd2(){

        System.out.println(integerSet.size());
        integerSet.add(1);
        System.out.println(integerSet.size());
        integerSet.add(5);
        integerSet.add(2);
        integerSet.add(7);
        integerSet.add(8);
        assertEquals(integerSet.size(), 5);
//        for(int i = 0; i < integerSet.size(); i++) {
//            System.out.println(integerSet(i));
//        }

        assertEquals(integerSet.first(), 1);
        assertEquals(integerSet.last(), 8);
    }


    @Test
    public void testAdd() {
        BinarySearchSet<Integer> set = new BinarySearchSet<>();

        assertTrue(set.isEmpty());
        assertEquals(0, set.size());

        assertTrue(set.add(3));
        assertTrue(set.add(1));
        assertTrue(set.add(5));

        assertFalse(set.isEmpty());
        assertEquals(3, set.size());

        assertTrue(set.contains(1));
        assertTrue(set.contains(3));
        assertTrue(set.contains(5));
        assertFalse(set.contains(2));

        // Add duplicates, expect false return
        assertFalse(set.add(3));
        assertFalse(set.add(1));
        assertFalse(set.add(5));

        assertEquals(3, set.size());

        // Add multiple elements at once
        List<Integer> elementsToAdd = Arrays.asList(2, 4, 6);
        assertTrue(set.addAll(elementsToAdd));

        assertEquals(6, set.size());
        assertTrue(set.containsAll(elementsToAdd));
    }

    @Test
    public void testContains() {
        BinarySearchSet<Integer> set = new BinarySearchSet<>();
        Integer[] newArray = {3, 1, 5};
        assertTrue(set.isEmpty());
        assertEquals(0, set.size());

        assertFalse(set.contains(1));

        set.add(3);
        set.add(1);
        set.add(5);

        assertFalse(set.isEmpty());
        assertEquals(3, set.size());

        assertTrue(set.contains(1));
        assertTrue(set.contains(3));
        assertTrue(set.contains(5));
        assertFalse(set.contains(2));
    }
}


