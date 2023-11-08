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
public class LibraryGeneric<T> {

//  private LibraryBook libraryBook = new LibraryBook();
  private ArrayList<LibraryBookGeneric<T>> library;

  public LibraryGeneric() {
    library = new ArrayList<LibraryBookGeneric<T>>();
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
    library.add(new LibraryBookGeneric(isbn, author, title));
  }

  /**
   * Add the list of library books to the library, assume no duplicates.
   * 
   * @param list
   *          -- list of library books to be added
   */
  public void addAll(ArrayList<LibraryBookGeneric<T>> list) {
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
    ArrayList<LibraryBookGeneric<T>> toBeAdded = new ArrayList<LibraryBookGeneric<T>>();

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
          toBeAdded.add(new LibraryBookGeneric(isbn, author, title));
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

  /**
   * Returns the holder of the library book with the specified ISBN.
   * 
   * If no book with the specified ISBN is in the library, returns null.
   * 
   * @param isbn
   *          -- ISBN of the book to be looked up
   */
  public <T> T lookup(long isbn) {
    //looping through each book in the library array
    for(LibraryBookGeneric book : library ){
      //if the entered isbn is a book in the library return the holder of that book
      if(isbn == book.getIsbn()) {
        return (T) book.getHolder();
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
  public ArrayList<LibraryBookGeneric> lookup(T holder) {
    //creating array of libary books called holder books that will be added to
    ArrayList<LibraryBookGeneric> holderBooks = new ArrayList<>();
    //looping through the library array and checking if entered holder is in possession of any library books
    for(LibraryBookGeneric book : library){
      if(book.getHolder() != null && book.getHolder() == holder){
        holderBooks.add(book);
      }
    }
    //will be empty if holder does not have any libray books
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

    for(LibraryBookGeneric book : library){

      //if the book is in the library
      if (isbn == book.getIsbn()){

        //if there is no holder for the book return true, can be checked out
       if(book.getHolder() != null){
         return false;
       }

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
    for(LibraryBookGeneric book : library){
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
    for(LibraryBookGeneric book : library){
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
  public ArrayList<LibraryBookGeneric<T>> getInventoryList() {
    ArrayList<LibraryBookGeneric<T>> libraryCopy = new ArrayList<LibraryBookGeneric<T>>();
    libraryCopy.addAll(library);
    OrderByIsbn comparator = new OrderByIsbn();
    sort(libraryCopy, comparator);
    return libraryCopy;
  }

  //write method gor get ordered by author and overdue list
  //write compare to methods
  /**
   * Returns the list of library books, sorted by author
   */
  public ArrayList<LibraryBookGeneric<T>> getOrderedByAuthor() {
    ArrayList<LibraryBookGeneric<T>> libraryCopyByAuthor = new ArrayList<LibraryBookGeneric<T>>();
    libraryCopyByAuthor.addAll(library);
    OrderByAuthor comparator = new OrderByAuthor();
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
  public ArrayList<LibraryBookGeneric<T>> getOverdueList(int month, int day, int year) {
    GregorianCalendar inputtedDate = new GregorianCalendar(year, month, day);
    ArrayList<LibraryBookGeneric<T>> libraryOverdueList = new ArrayList<LibraryBookGeneric<T>>();
    OrderByDueDate comparator = new OrderByDueDate();
    for(LibraryBookGeneric book : library){
      int res = book.getDueDate().compareTo(inputtedDate);
      if (res < 0){
        libraryOverdueList.add(book);
      }
    }
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
  protected class OrderByIsbn implements Comparator<LibraryBookGeneric<T>> {
    /**
     * Returns a negative value if lhs is smaller than rhs. Returns a positive
     * value if lhs is larger than rhs. Returns 0 if lhs 	and rhs are equal.
     */
    public int compare(LibraryBookGeneric<T> lhs, LibraryBookGeneric<T> rhs) {
      return (int) (lhs.getIsbn() - rhs.getIsbn());
    }
  }

  /**
   * Comparator that defines an ordering among library books using the
   author, and book title as a tie-breaker.
   */
  protected class OrderByAuthor implements Comparator<LibraryBookGeneric<T>> {
    @Override
    public int compare(LibraryBookGeneric<T> o1, LibraryBookGeneric<T> o2) {

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
  protected class OrderByDueDate implements Comparator<LibraryBookGeneric<T>> {
    @Override
    public int compare(LibraryBookGeneric<T> o1, LibraryBookGeneric<T> o2) {

      GregorianCalendar dueDate1 = o1.getDueDate();
      GregorianCalendar dueDate2 = o2.getDueDate();

      int date = dueDate1.compareTo(dueDate2);

      return date;

      //tests if the list only has one due date
      //test if the list is on the same day
    }

  }
}
