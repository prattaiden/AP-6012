package assignment02;
import java.util.GregorianCalendar;
public class LibraryBook<T> extends Book{

    //member variable for the generic holder
    T holder_ = null;

    //member variable for the due date
    GregorianCalendar dueDate_ = null;

    //constructor
    public LibraryBook(long isbn, String author, String title) {
        super(isbn, author, title);
    }

    public T getHolder() {
        return holder_;
    }

    public GregorianCalendar getDueDate() {
        return dueDate_;
    }

    public void setHolder(T holder){
       this.holder_ = holder;
    }

    public void setDueDate(GregorianCalendar gC){

        this.dueDate_ = gC;

    }


}
