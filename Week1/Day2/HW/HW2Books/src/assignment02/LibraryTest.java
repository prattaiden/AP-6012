package assignment02;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    @Test
    public void testEmpty() {
        Library lib = new Library();
        assertNull(lib.lookup(978037429279L));

        ArrayList<LibraryBook> booksCheckedOut = lib.lookup("Jane Doe");
        assertEquals(booksCheckedOut.size(), 0);

        assertFalse(lib.checkout(978037429279L, "Jane Doe", 1, 1, 2008));
        assertFalse(lib.checkin(978037429279L));
        assertFalse(lib.checkin("Jane Doe"));
    }

    @Test
    public void testNonEmpty() {

        var lib = new Library();
        // test a small library
        lib.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
        lib.add(9780330351690L, "Jon Krakauer", "Into the Wild");
        lib.add(9780446580342L, "David Baldacci", "Simple Genius");

        assertNull(lib.lookup(9780330351690L)); //not checked out
        var res = lib.checkout(9780330351690L, "Jane Doe", 1, 1, 2008);
        assertTrue(res);

        //changed this from var
        ArrayList<LibraryBook> booksCheckedOut = lib.lookup("Jane Doe");
        assertEquals(booksCheckedOut.size(), 1);

        assertEquals(booksCheckedOut.get(0),new Book(9780330351690L, "Jon Krakauer", "Into the Wild"));
        assertEquals(booksCheckedOut.get(0).getHolder(), "Jane Doe");
        assertEquals(booksCheckedOut.get(0).getDueDate(),new GregorianCalendar(2008, 1, 1));

        res = lib.checkin(9780330351690L);
        assertTrue(res);
        res = lib.checkin("Jane Doe");
        assertFalse(res);
    }

    @Test
    public void testLargeLibrary(){
        // test a medium library
        var lib = new Library();
        lib.addAll("Mushroom_Publishing.txt");

    }
    @Test
    public void stringLibraryTest() {
        // test a library that uses names (String) to id patrons
        Library<String> lib = new Library<>();
        lib.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
        lib.add(9780330351690L, "Jon Krakauer", "Into the Wild");
        lib.add(9780446580342L, "David Baldacci", "Simple Genius");

        String patron1 = "Jane Doe";

        assertTrue(lib.checkout(9780330351690L, patron1, 1, 1, 2008));
        assertTrue(lib.checkout(9780374292799L, patron1, 1, 1, 2008));

        var booksCheckedOut1 = lib.lookup(patron1);
        assertEquals(booksCheckedOut1.size(), 2);
        assertTrue(booksCheckedOut1.contains(new Book(9780330351690L, "Jon Krakauer", "Into the Wild")));
        assertTrue(booksCheckedOut1.contains(new Book(9780374292799L, "Thomas L. Friedman", "The World is Flat")));
        assertEquals(booksCheckedOut1.get(0).getHolder(), patron1);
        assertEquals(booksCheckedOut1.get(0).getDueDate(), new GregorianCalendar(2008, 1, 1));
        assertEquals(booksCheckedOut1.get(1).getHolder(),patron1);
        assertEquals(booksCheckedOut1.get(1).getDueDate(),new GregorianCalendar(2008, 1, 1));

        assertTrue(lib.checkin(patron1));

    }

    @Test
    public void phoneNumberTest(){
        // test a library that uses phone numbers (PhoneNumber) to id patrons
        var lib = new Library<PhoneNumber>();
        lib.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
        lib.add(9780330351690L, "Jon Krakauer", "Into the Wild");
        lib.add(9780446580342L, "David Baldacci", "Simple Genius");

        PhoneNumber patron2 = new PhoneNumber("801.555.1234");

        assertTrue(lib.checkout(9780330351690L, patron2, 1, 1, 2008));
        assertTrue(lib.checkout(9780374292799L, patron2, 1, 1, 2008));

        //original copied in
       // ArrayList<LibraryBookGeneric<PhoneNumber>> booksCheckedOut2 = lib.lookup(patron2);
        ArrayList<LibraryBook> booksCheckedOut2 = lib.lookup(patron2);

        assertEquals(booksCheckedOut2.size(), 2);
        assertTrue(booksCheckedOut2.contains(new Book(9780330351690L, "Jon Krakauer", "Into the Wild")));
        assertTrue(booksCheckedOut2.contains(new Book(9780374292799L, "Thomas L. Friedman", "The World is Flat")));
        assertEquals(booksCheckedOut2.get(0).getHolder(),patron2);
        assertEquals(booksCheckedOut2.get(0).getDueDate(),new GregorianCalendar(2008, 1, 1));
        assertEquals(booksCheckedOut2.get(1).getHolder(), patron2);
        assertEquals(booksCheckedOut2.get(1).getDueDate(), new GregorianCalendar(2008, 1, 1));

        assertTrue(lib.checkin(patron2));

    }

    //------------------------------------------AIDEN TESTS-----------------------------------------------
    //duplicate checkout Test
    @Test
    public void testDuplicateCheckOut(){
        //creating library
        var lib = new Library();
        //adding book to the library
        lib.add(9780330351690L, "Jon Krakauer", "Into the Wild");

        //first person checking out the book
        var checkout1 = lib.checkout(9780330351690L, "Jane Doe", 1, 1, 2008);
        //asserting true because checking out is available
        assertTrue(checkout1);
        //second person checking out the book
        var checkout2 = lib.checkout(9780330351690L, "Aiden Pratt", 1, 1, 2009);
        //asserting false because checking out this book is unavailable
        assertFalse(checkout2);

    }

    //looking up a non valid ISBN number
    @Test
    public void testIncorrectIsbn(){
        Library lib = new Library<>();
        assertNull(lib.lookup(9780330351690L));
    }

    @Test
    public void testInvalidHolder(){
        Library lib = new Library<>();
        lib.add(100, "Dude Guy", "Cool Book");

        //checkout
        var checkout = lib.checkout(100, "Aiden Pratt", 2,1,1998);
        assertTrue(checkout);

        //check in
        assertTrue(lib.checkin("Aiden Pratt"));

        ArrayList<LibraryBook> booksArray = new ArrayList<>();
        var invalidHolder = lib.lookup("Jason Derulo");
        assertFalse(lib.checkin("Jason Derulo"));
        assertEquals(booksArray.size(), 0);
    }

    @Test
    public void testGetOverDueDate(){
        var lib = new Library();
        //adding book to the library
        lib.add(9780330351690L, "Jon Krakauer", "Into the Wild");
        lib.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
        lib.add(9780446580342L, "David Baldacci", "Simple Genius");
        lib.add(8000, "David Baldacci", "This Book");

        //first person checking out the book
        var checkout1 = lib.checkout(9780330351690L, "Jane Doe", 1, 1, 2008);
        assertTrue(checkout1);
        var checkout2 = lib.checkout(9780374292799L, "Aiden Pratt", 1, 1, 2008);
        assertTrue(checkout2);
        var checkout3 = lib.checkout(9780446580342L, "Melanie Prettyman", 10, 6, 2009);
        //this is not due yet by one day
        assertTrue(checkout3);
        var checkout4 = lib.checkout(8000, "Dude", 10, 5, 2009);
        //this is not due yet?
        assertTrue(checkout4);

        ArrayList<LibraryBook> books = lib.getOverdueList(10, 5, 2009);
        //asserting that two books are overdue
        assertEquals(books.size(), 2);

//        //testing if due date is same
//        var checkout4 = lib.checkout(8000, "Aiden Pratt", 1, 1, 2008);
//        ArrayList<LibraryBookGeneric> books2 = lib.getOverdueList(1, 1, 2008);
//        assertEquals(books2.size(),2);
    }

    @Test
    public void testGetInventoryList(){
        var lib = new Library();
        //adding book to the library
        lib.add(00000001, "Jon Krakauer", "Into the Wild");
        lib.add(00000002, "Thomas L. Friedman", "The World is Flat");
        lib.add(00000003, "David Baldacci", "Simple Genius");

        ArrayList<LibraryBook> sortByISBN = lib.getInventoryList();
        assertEquals(sortByISBN.get(2).getIsbn(), 00000003);
    }

    @Test
    public void testGetByAuthor(){
        var lib = new Library();
        //adding book to the library
        lib.add(00000001, "Jon Krakauer", "Into the Wild");
        lib.add(00000002, "Thomas L. Friedman", "The World is Flat");
        lib.add(00000003, "David Baldacci", "Simple Genius");
        ArrayList<LibraryBook> sortByAuthor = lib.getOrderedByAuthor();
        //found the author name
        assertEquals(sortByAuthor.get(0).getAuthor(), "David Baldacci");

        lib.add(00000003, "David Baldacci", "A");
        ArrayList<LibraryBook> sortByAuthor2 = lib.getOrderedByAuthor();

        assertEquals(sortByAuthor2.get(0).getTitle(), "A");

        //adding the same book to the libraty for a new array list sorted by author
        lib.add(0000003, "David Baldacci", "A");
        ArrayList<LibraryBook> sortByAuthor3 = lib.getOrderedByAuthor();
        //first to objects in the array list should have the same title,
        //they are equal but sort into position 0 and 1

        assertEquals(sortByAuthor3.get(0).getTitle(), "A");
        assertEquals(sortByAuthor3.get(1).getTitle(), "A");
        assertEquals(sortByAuthor3.get(2).getTitle(), "Simple Genius");
    }



    //someone checks out
    //normal check in
    //wrong ISBN look up if it doesn't exist
    //wrong holder name

}