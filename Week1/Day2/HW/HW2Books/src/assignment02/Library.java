package assignment02;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * Class representation of a library (a collection of library books).
 * 
 */
public class Library<T> {

//  private LibraryBook libraryBook = new LibraryBook();
  private ArrayList<LibraryBook<T>> library;

  public Library() {
    library = new ArrayList<LibraryBook<T>>();
  }

  /**
   * Add the specified book to the library, assume no duplicates.
   * 
   * @param isbn
   *          -- ISBN of the book to be added
   * @param author
   *          -- author of the book to be added
   * @param title
   *          -- title of the book to be added
   */
  public void add(long isbn, String author, String title) {
    library.add(new LibraryBook(isbn, author, title));
  }

  /**
   * Add the list of library books to the library, assume no duplicates.
   * 
   * @param list
   *          -- list of library books to be added
   */
  public void addAll(ArrayList<LibraryBook<T>> list) {
    library.addAll(list);
  }

  /**
   * Add books specified by the input file. One book per line with ISBN, author,
   * and title separated by tabs.
   * 
   * If file does not exist or format is violated, do nothing.
   * 
   * @param filename
   */
  public void addAll(String filename) {
    ArrayList<LibraryBook<T>> toBeAdded = new ArrayList<LibraryBook<T>>();

    try (Scanner fileIn = new Scanner(new File(filename))) {

      int lineNum = 1;

      while (fileIn.hasNextLine()) {
        String line = fileIn.nextLine();

        try (Scanner lineIn = new Scanner(line)) {
          lineIn.useDelimiter("\\t");

          if (!lineIn.hasNextLong()) {
            throw new ParseException("ISBN", lineNum);
          }
          long isbn = lineIn.nextLong();

          if (!lineIn.hasNext()) {
            throw new ParseException("Author", lineNum);
          }
          String author = lineIn.next();

          if (!lineIn.hasNext()) {
            throw new ParseException("Title", lineNum);
          }
          String title = lineIn.next();
          toBeAdded.add(new LibraryBook(isbn, author, title));
        }
        lineNum++;
      }
    } catch (FileNotFoundException e) {
      System.err.println(e.getMessage() + " Nothing added to the library.");
      return;
    } catch (ParseException e) {
      System.err.println(e.getLocalizedMessage() + " formatted incorrectly at line " + e.getErrorOffset()
          + ". Nothing added to the library.");
      return;
    }

    library.addAll(toBeAdded);
  }

  //----------------------------------AIDEN-------------------------------------------------
  /**
   * Returns the holder of the library book with the specified ISBN.
   * 
   * If no book with the specified ISBN is in the library, returns null.
   * 
   * @param isbn
   *          -- ISBN of the book to be looked up
   */
  public T lookup(long isbn) {
    //looping through each book in the library array
    for(LibraryBook<T> book : library ){
      //if the entered isbn is a book in the library return the holder of that book
      if(isbn == book.getIsbn()) {
        return book.getHolder();
      }
    }
    return null;
  }

  /**
   * Returns the list of library books checked out to the specified holder.
   * 
   * If the specified holder has no books checked out, returns an empty list.
   * 
   * @param holder
   *          -- holder whose checked out books are returned
   */
  public ArrayList<LibraryBook> lookup(T holder) {
    //creating array of libary books called holder books that will be added to
    ArrayList<LibraryBook> holderBooks = new ArrayList<>();
    //looping through the library array and checking if entered holder is in possession of any library books
    for(LibraryBook book : library){
      if(book.getHolder() != null && book.getHolder() == holder){
        holderBooks.add(book);
      }
    }
    //will be empty if holder does not have any library books
    return holderBooks;
  }

  /**
   * Sets the holder and due date of the library book with the specified ISBN.
   * 
   * If no book with the specified ISBN is in the library, returns false.
   * 
   * If the book with the specified ISBN is already checked out, returns false.
   * 
   * Otherwise, returns true.
   * 
   * @param isbn
   *          -- ISBN of the library book to be checked out
   * @param holder
   *          -- new holder of the library book
   * @param month
   *          -- month of the new due date of the library book
   * @param day
   *          -- day of the new due date of the library book
   * @param year
   *          -- year of the new due date of the library book
   * 
   */
  public boolean checkout(long isbn, T holder, int month, int day, int year) {

    //loop through books in library
    for(LibraryBook book : library){

      //if the book is in the library, set isbn
      if (isbn == book.getIsbn()){

        //if there the holder is not eqaul to null, return false, cannot be checked out bc there is a holder
       if(book.getHolder() != null){
         return false;
       }

       //else there is no holder, and it can be checked out
       //setting the holder to the book
       //setting the due date entered
       //returning true because there is a holder now, and it cannot be checked out
       else {
         book.setHolder(holder);
         book.setDueDate(new GregorianCalendar(year,month,day));
         return true;
       }

      }
    }

    //if the book is not in the library
    return false;
  }

  /**
   * Unsets the holder and due date of the library book.
   * 
   * If no book with the specified ISBN is in the library, returns false.
   * 
   * If the book with the specified ISBN is already checked in, returns false.
   * 
   * Otherwise, returns true.
   * 
   * @param isbn
   *          -- ISBN of the library book to be checked in
   */
  public boolean checkin(long isbn) {
    for(LibraryBook book : library){
      if(book.getHolder() != null && isbn == book.getIsbn()){
        //if there is a holder for the book and the isbn is in the library
          book.holder_ = null;
          book.dueDate_ = null;
          return true;

      }
    }
    //if book is not in library return false
    return false;
  }

  /**
   * Unsets the holder and due date for all library books checked out by the
   * specified holder.
   * 
   * If no books with the specified holder are in the library, returns false;
   * 
   * Otherwise, returns true.
   * 
   * @param holder
   *          -- holder of the library books to be checked in
   */
  public boolean checkin(T holder) {
    //same thing but when the user enters a holder T instead of a isbn
    for(LibraryBook book : library){
      if(book.getHolder() != null && book.getHolder() == holder){
        book.holder_ = null;
        book.dueDate_ = null;
        return true;
      }
    }
    return false;
  }

  //------------------------------------------PHASE 3--------------------------------------------------
  //---------------------------------------------------------------------------------------------------
  /**
   * Returns the list of library books, sorted by ISBN (smallest ISBN
   first).
   */
  public ArrayList<LibraryBook<T>> getInventoryList() {
    ArrayList<LibraryBook<T>> libraryCopy = new ArrayList<LibraryBook<T>>();
    libraryCopy.addAll(library);
    OrderByIsbn comparator = new OrderByIsbn();
    sort(libraryCopy, comparator);
    return libraryCopy;
  }

  /**
   * Returns the list of library books, sorted by author
   */
  //-----------------------------------------AIDEN-------------------------------------------------------
  public ArrayList<LibraryBook<T>> getOrderedByAuthor() {
    //creating an array list of lib books
    ArrayList<LibraryBook<T>> libraryCopyByAuthor = new ArrayList<LibraryBook<T>>();
    //adding books of the library to the array list
    libraryCopyByAuthor.addAll(library);
    //calling the comparator from class OrderByAuthor
    OrderByAuthor comparator = new OrderByAuthor();
    //calling the sort method to sort them by author based on comparator's rules
    sort(libraryCopyByAuthor, comparator);
    return libraryCopyByAuthor;
  }

  /**
   * Returns the list of library books whose due date is older than the
   input
   * date. The list is sorted by date (oldest first).
   *
   * If no library books are overdue, returns an empty list.
   */
  public ArrayList<LibraryBook<T>> getOverdueList(int month, int day, int year) {
    //array list of calendar
    GregorianCalendar inputtedDate = new GregorianCalendar(year, month, day);
    ArrayList<LibraryBook<T>> libraryOverdueList = new ArrayList<LibraryBook<T>>();
    OrderByDueDate comparator = new OrderByDueDate();
    //loop through the books in the library
    for(LibraryBook book : library){

      //if the book due date is not null
      if(book.getDueDate()!= null){
        //if ther eis a due date, compare it to the due date inputted in the parameters
        int res = book.getDueDate().compareTo(inputtedDate);
        //if res < 0, the due date for book.getduedate is overdue, so it is added to array list
        if (res < 0) {
          libraryOverdueList.add(book);
        }
      }
    }
    //sorting these bookes by the earliest due date first
    sort(libraryOverdueList , comparator);
    System.out.println(libraryOverdueList);
    return libraryOverdueList;

  }

  /**
   * Performs a SELECTION SORT on the input ArrayList.
   * 1. Find the smallest item in the list.
   * 2. Swap the smallest item with the first item in the list.
   * 3. Now let the list be the remaining unsorted portion
   * (second item to Nth item) and repeat steps 1, 2, and 3.
   */
  private static <ListType> void sort(ArrayList<ListType> list, Comparator<ListType> c) {
    for (int i = 0; i < list.size() - 1; i++) {
      int j, minIndex;
      for (j = i + 1, minIndex = i; j < list.size(); j++)
        if (c.compare(list.get(j), list.get(minIndex)) < 0)
          minIndex = j;
      ListType temp = list.get(i);
      list.set(i, list.get(minIndex));
      list.set(minIndex, temp);
    }
  }

  /**
   * Comparator that defines an ordering among library books using the
   ISBN.
   */
  protected class OrderByIsbn implements Comparator<LibraryBook<T>> {
    /**
     * Returns a negative value if lhs is smaller than rhs. Returns a positive
     * value if lhs is larger than rhs. Returns 0 if lhs 	and rhs are equal.
     */
    public int compare(LibraryBook<T> lhs, LibraryBook<T> rhs) {
      return (int) (lhs.getIsbn() - rhs.getIsbn());
    }
  }

  /**
   * Comparator that defines an ordering among library books using the
   author, and book title as a tie-breaker.
   */
  protected class OrderByAuthor implements Comparator<LibraryBook<T>> {
    @Override
    public int compare(LibraryBook<T> o1, LibraryBook<T> o2) {

      //comparing the two authors in the method
      int author = o1.getAuthor().compareTo(o2.getAuthor());

      //if the authors are the same, compare the book titles
      if (author == 0){
       int bookTitle = o1.getTitle().compareTo(o2.getTitle());
       return bookTitle;

      }
      return author;
    }
  }

  /**
   * Comparator that defines an ordering among library books using the
   due date.
   */
  protected class OrderByDueDate implements Comparator<LibraryBook<T>> {
    @Override
    public int compare(LibraryBook<T> o1, LibraryBook<T> o2) {

      GregorianCalendar dueDate1 = o1.getDueDate();
      GregorianCalendar dueDate2 = o2.getDueDate();

      int date = dueDate1.compareTo(dueDate2);

      return date;

    }

  }
}
