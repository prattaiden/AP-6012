package assignment03;

import java.util.*;

public class BinarySearchSet<E> implements assignment03.SortedSet<E>, Iterable {

    private E[] set_;
    private int capacity_ = 0;
    private int size_ = 0;
    //private boolean isSorted = false;
    private Comparator<? super E> comparator_;




    //Constructor 1
    //If this constructor is used to create the sorted set,
    // it is assumed that the elements are ordered using their natural ordering
    public BinarySearchSet(){

        capacity_ = 10;
        set_ = (E[]) new Object[capacity_];
        size_ = 0;


    }

    //Constructor 2
    //If this constructor is used to create the sorted set,
    // it is assumed that the elements are ordered using the provided comparator.
    public BinarySearchSet(Comparator<? super E> comparator){
        comparator_ = comparator;
        capacity_ = 10;
        set_ = (E[]) new Object[capacity_];
        size_ = 0;

    }
    //------------------------------overriding methods----------------------------------
    @Override
    public Comparator<? super E> comparator() {
        return comparator_;
    }

    @Override
    public E first() throws NoSuchElementException {
        if (set_.length == 0){
            throw new NoSuchElementException("set is empty");
        }
        return set_[0];
    }

    @Override
    public E last() throws NoSuchElementException {
        if(set_.length == 0){
            throw new NoSuchElementException("set is empty");
        }
        return set_[this.size_ - 1];
    }

    @Override
    public boolean add(E element) {
        if (set_ == null) {
            set_ = (E[]) new Object[]{element};
            size_ = 1;
            return true;
        }
        if (!contains(element)) {
            if (size_ >= capacity_) {
                growArray();
            }
            // Call binarySearch to find the insertion point
            int insertionPoint = binarySearch(element);
            // if insertion point is negative, negates it and subracts one

            if (insertionPoint < 0) {
                insertionPoint = -(insertionPoint + 1);
            }
            // Shift the elements to the right, starting from the insertion point, to make space for the new element.
            System.arraycopy(set_, insertionPoint, set_, insertionPoint + 1, size_ - insertionPoint);
            // Assign the new element to the insertion point and increment the size of the set.
            set_[insertionPoint] = element;
            size_++;
            return true;

        }
        return false;
    }

    @Override
    public boolean addAll(Collection elements) {
        for(Object obj: elements){
            this.add((E) obj);
        }
        return this.containsAll(elements);
    }

    @Override
    public void clear() {
        if(this.size_ > 0) {
            size_ = 0;
        }
    }

    @Override
    public boolean contains(E element) {

        int index = binarySearch(element);
        if(index >= 0){
            return true;
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection <? extends E> elements) {
        for(Object obj: elements){
            if(!this.contains((E) obj)){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return size_ == 0;
    }

    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public boolean remove(E element) {
        int index = this.binarySearch(element);

        if (index >= 0) {
            // Element found, remove it
            System.arraycopy(set_, index + 1, set_, index, size_ - index - 1);
            set_[--size_] = null;  // Clear the last element
            return true;
        }

        // Element not found
        return false;
    }

    @Override
    public boolean removeAll(Collection elements) {
        return false;
    }

    @Override
    public int size() {
        return size_;
    }

    @Override
    public E[] toArray() {
        return (E[]) new Object[0];
    }

    //----------------------------additional helper methods----------------------------\\
    //grow the array
    public void growArray() {
        //Dynamically allocate memory for a temporary array that is twice the size of the original.
        capacity_ = 2 * capacity_;
        //Copy the contents over from set to this temp array by looping over the temp array.
        E[] tempArray = Arrays.copyOf(set_, capacity_);
        //Set = the pointer to the temp array.
        set_ = tempArray;
        //Set the pointer to the temp array to nullptr.
        tempArray = null;
    }

    //binary search from tree set
    private int binarySearch(E element) {
        int low = 0;
        int high = size_ - 1;

        while (low <= high) {
            int mid = (low + high) >>> 1;
            @SuppressWarnings("unchecked")
            Comparable<? super E> midVal = (Comparable<? super E>) set_[mid];

            if (midVal == null) {
                // Skip comparison if element is null
                low = mid + 1;
                continue;
            }

            int cmp = midVal.compareTo(element);

            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid; // Element found
            }
        }
        return -(low + 1); // Element not found, return the insertion point
    }

}
