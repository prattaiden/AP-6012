package assignment02;
import java.util.GregorianCalendar;
public class LibraryBookGeneric<T> extends Book{

    T holder_ = null;

    GregorianCalendar dueDate_ = null;

    int  month_ , day_,  year_;

    //constructor
    public LibraryBookGeneric(long isbn, String author, String title) {
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

//        month = month_;
//        day = day_;
//        year = year_;
//
//        GregorianCalendar dueDate = new GregorianCalendar(year,month,day);
//
//        this.dueDate_=dueDate;
    }

    //methods for checking the book in and out
//    public void checkBookOut(){
//
//    }
//
//    public void checkBookIn(){
//
//    }
}
