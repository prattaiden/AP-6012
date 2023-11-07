package lab01;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class lab01Test {

    private int[] arr1, arr2, arr3, arr4, arr5, arr6, arr7;


    @org.junit.jupiter.api.BeforeEach
    protected void setUp() throws Exception {
        arr1 = new int[0];
        arr2 = new int[] { 3, 3, 3 };
        arr3 = new int[] { 52, 4, -8, 0, -17 };
        arr4 = new int[] {1, 2, 3, 4};
        arr5 = new int[] {-100, -50, -20, -10};
        arr6 = new int[] {5, 6, 10, 6, 5, 10};
        arr7 = new int[] {10, 8, 5, 3};
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }


    @Test
    public void emptyArray() {
        assertEquals(-1, DiffUtil.findSmallestDiff(arr1));
    }

    @Test
    public void allArrayElementsEqual() {
        assertEquals(0, DiffUtil.findSmallestDiff(arr2));
    }

    @Test
    public void smallRandomArrayElements() {
        assertEquals(4, DiffUtil.findSmallestDiff(arr3));
    }

    //adding new test--
    //testing an array of ascending values
    @Test
    public void ascendingArrayElements(){
        assertEquals(1, DiffUtil.findSmallestDiff(arr4));
    }

    @Test
    public void negativeElementArray(){
        assertEquals(10, DiffUtil.findSmallestDiff(arr5));
    }

    @Test
    public void repeatedElementsArray(){
        assertEquals(0, DiffUtil.findSmallestDiff(arr6));
    }

    @Test
    public void descendingArrayElements(){
        assertEquals(2, DiffUtil.findSmallestDiff(arr7));
    }

}