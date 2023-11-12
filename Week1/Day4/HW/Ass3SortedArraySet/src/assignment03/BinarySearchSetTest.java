package assignment03;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchSetTest {

    //--------------------------------Add Method Tests---------------------------------------\\

    @Test
    public void testAddMethod() {
        BinarySearchSet<Integer> testAdd = new BinarySearchSet();
        testAdd.add(1);
        testAdd.add(2);

        assertEquals(testAdd.first(), 1);
        assertEquals(testAdd.contains(1), true);
        assertEquals(testAdd.last(), 2);
    }

    @Test
    public void testAdd2() {
        BinarySearchSet<Integer> integerSet = new BinarySearchSet<>();
        System.out.println(integerSet.size());
        integerSet.add(1);
        System.out.println(integerSet.size());
        integerSet.add(5);
        integerSet.add(2);
        integerSet.add(7);
        integerSet.add(8);
        assertEquals(integerSet.size(), 5);
        assertEquals(integerSet.first(), 1);
        assertEquals(integerSet.last(), 8);
    }


    @Test
    public void testAdd3() {
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
        List<Integer> duplicateToAdd = Arrays.asList(3,1,5);

        //testing contains all
        assertEquals(6, set.size());
        assertTrue(set.containsAll(elementsToAdd));

        //testing adding duplicates
        assertFalse(set.addAll(duplicateToAdd));
        assertEquals(6, set.size());

        //--------------------------TESTING ITERABLE---------------------------------------\\
        for (int e : set) {
            System.out.println(e);
        }
    }

    //----------------------------TEST CONTAINS------------------------------------------\\
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

    @Test
    void containsAllTest() {
        BinarySearchSet<String> stringSet = new BinarySearchSet<>();

        stringSet.add("one");
        stringSet.add("two");
        stringSet.add("three");

        Set<String> elementsToCheck = new HashSet<>(Arrays.asList("one", "two", "three"));

        assertTrue(stringSet.containsAll(elementsToCheck));
    }

    //------------------------------TEST EMPTY&CLEAR-------------------------------\\

    @Test
    public void testEmptySet() {
        BinarySearchSet<Integer> emptySet = new BinarySearchSet<>();
        //tests for empty set func
        assertTrue(emptySet.isEmpty());
        emptySet.add(1);
        assertFalse(emptySet.isEmpty());
        assertEquals(emptySet.size(),1);
    }

    @Test
    public void testClearSet() {
        BinarySearchSet<String> stringSet = new BinarySearchSet<>();
        stringSet.add("HEYYYY");
        assertTrue(stringSet.contains("HEYYYY"));
        //test clear func
        stringSet.clear();
        assertTrue(stringSet.isEmpty());
    }


    //--------------------------------TEST REMOVE--------------------------------\\
    @Test
    public void testRemoveMethod(){
        BinarySearchSet<Integer> intSet = new BinarySearchSet<>();
        intSet.add(5);
        intSet.add(4);
        intSet.add(2);
        assertTrue(intSet.contains(2));
        intSet.remove(2);
        assertFalse(intSet.contains(2));
    }

    @Test
    public void testRemoveAllMethod(){
        BinarySearchSet<Integer> intSet = new BinarySearchSet<>();
        intSet.add(5);
        intSet.add(4);
        intSet.add(2);
        assertEquals(intSet.size(), 3);
        List<Integer> removeThese = new ArrayList<>();
        removeThese.add(4);
        removeThese.add(2);
        intSet.removeAll(removeThese);
        assertEquals(intSet.size(), 1);
    }

    //------------------------------TEST DUPLICATE---------------------------------------\\

    @Test
    public void testAddingAndRemovingDuplicates(){

        BinarySearchSet<Integer> intSet = new BinarySearchSet<>();
        intSet.add(5);
        intSet.add(4);
        intSet.add(2);
        assertEquals(3, intSet.size());

    }

    //----------------------------TEST GROW ARRAY-----------------------------------------\\

    @Test
    public void testGrowArrayHelper(){
        BinarySearchSet<Integer> test = new BinarySearchSet<>();

        //capacity is default set at 10
        assertEquals(10, test.getCapacity());

        test.add(5);
        assertEquals(test.first(), 5);

        //grow array, expected to double and give 20
        test.growArray();

        assertEquals(20, test.getCapacity());

    }

    @Test public void testGrowArrayInAddMethod(){
        BinarySearchSet<Integer> growSet = new BinarySearchSet<>();
        //expect it to need to grow array after ten elements are added
        List toAdd1 = Arrays.asList(1, 2, 3, 4, 5);
        growSet.addAll(toAdd1);
        assertEquals(10, growSet.getCapacity());
        assertEquals(5, growSet.size());

        List toAdd2 = Arrays.asList(6, 7, 8, 9, 10, 11);
        growSet.addAll(toAdd2);
        assertEquals(20, growSet.getCapacity());
        assertEquals(11, growSet.size());
    }

    //------------------------TEST COMPARATOR TEST---------------------------------\\

    public class MyDescendingComparator implements Comparator<Integer>{
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2.compareTo(o1);
        }
    }

    @Test
    public void testComparatorCustom(){
        MyDescendingComparator myComp = new MyDescendingComparator();

        //using the constructor where I pass in a custom comparator
        BinarySearchSet<Integer> compSet = new BinarySearchSet<>(myComp);

        List toAdd = Arrays.asList(1, 2, 3, 4, 5);
        compSet.addAll(toAdd);
        assertEquals(compSet.size(), 5);

        //asserting the positions of the elements
        assertEquals(5, compSet.first());
        assertEquals(1, compSet.last());
        //expected to be in reverse order because of the myComp Comparator made

    }

//    @Test
//    public void testToArray(){
//        BinarySearchSet<Integer> intSet = new BinarySearchSet<>();
//        intSet.add(1);
//
//        Integer[] toArrayTest = intSet.toArray();
//
//        //compare the content of toArrayTest with the expected contents, 1
//        assertArrayEquals(new Integer[] {1}, toArrayTest);
//    }



}
