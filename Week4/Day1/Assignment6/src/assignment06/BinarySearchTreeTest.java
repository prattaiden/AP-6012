package assignment06;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
    }

}