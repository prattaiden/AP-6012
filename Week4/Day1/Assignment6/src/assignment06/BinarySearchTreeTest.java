package assignment06;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
class BinarySearchTreeTest {



    @Test
    public void testAdd(){
        BinarySearchTree bst = new BinarySearchTree();

        bst.add(5);
        bst.add(3);
        //double adding should not work
        bst.add(5);
        System.out.println(bst.getHeight());

        assertEquals(1, bst.getHeight());
        assertEquals(2, bst.size());

        assertThrows(NullPointerException.class, () -> bst.add(null));


    }
    @Test
    public void testContains(){
        BinarySearchTree bst = new BinarySearchTree();
        //testing is empty
        assertTrue(bst.isEmpty());
        bst.add(5);
        bst.add(3);
        bst.add(1);

        ArrayList items = new ArrayList<>();
        items.add(4);
        items.add(8);
        items.add(2);



        bst.addAll(items);


        assertTrue(bst.containsAll(items));
        assertTrue(bst.contains(5));

        ArrayList nullItems = null;
        assertThrows(NullPointerException.class, ()-> bst.addAll(nullItems));
        assertThrows(NullPointerException.class, ()-> bst.containsAll(nullItems));

        bst.removeAll(items);
        assertFalse(bst.containsAll(items));
}

    @Test
    public void testFirstandLast(){
        BinarySearchTree bst = new BinarySearchTree();
        bst.add(6);
        bst.add(4);
        bst.add(8);
        bst.add(3);
        bst.add(5);
        bst.add(7);
        bst.add(9);

        assertEquals(3, bst.first());
        assertEquals(9, bst.last());

        bst.clear();
        assertTrue(bst.isEmpty());

        //TESTING NULL TREE
        BinarySearchTree bst1 = new BinarySearchTree();
        assertThrows(NoSuchElementException.class, ()-> bst1.first());
        assertThrows(NoSuchElementException.class, ()-> bst1.last());

        //single node, both first and last equal that 1
        bst1.add(1);
        assertEquals(1, bst1.first());
        assertEquals(1, bst1.last());

        bst1.add(2);
        bst1.add(3);
        bst1.add(4);
        assertEquals(4, bst1.last());

    }


    @Test
    public void testToArray(){
        BinarySearchTree bst = new BinarySearchTree();
        ArrayList items = new ArrayList<>();
        items.add(2);
        items.add(4);
        items.add(7);
        items.add(8);
        items.add(60);
        items.add(111);

        bst.addAll(items);

        ArrayList arrayList = new ArrayList<>();
        arrayList.addAll(items);


       ArrayList arrayListTest = bst.toArrayList();

       assertEquals(arrayList, arrayListTest);

       //pt 2, testing toarraylist with an empty bst and arraylist

        BinarySearchTree emptyBST = new BinarySearchTree();
        ArrayList emptyArray = emptyBST.toArrayList();

       assertEquals(0, emptyArray.size());


    }

}