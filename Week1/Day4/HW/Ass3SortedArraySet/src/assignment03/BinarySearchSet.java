package assignment03;

import java.util.*;


//Aiden Pratt

public class BinarySearchSet<E> implements assignment03.SortedSet<E>, Iterable<E> {

    private E[] set_;
    private int capacity_ = 0;
    private int size_ = 0;
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
    //----------------------------overriding methods in SortedSet----------------------------------
    @Override
    public Comparator<? super E> comparator() {
        return comparator_;
    }

    @Override
    public E first() throws NoSuchElementException {
        if (isEmpty()){
            throw new NoSuchElementException("set is empty");
        }
        //first element in the set
        return set_[0];
    }

    @Override
    public E last() throws NoSuchElementException {
        if(isEmpty()){ //size_
            throw new NoSuchElementException("set is empty");
        }
        //last element in the set
        //-1
        return set_[this.size_ - 1];
    }

    @Override
    public boolean add(E element) {
        //handling case when set is initially null when addis called on it
        //initializes a new array of object
        //E is being cast on it
        if (set_ == null) {
            set_ = (E[]) new Object[]{element};
            size_ = 1;
            return true;
        }
        //if the element entered as a parameter is not in this
        //means it should be added, then check if the array needs to be grown
        if (!this.contains(element)) {
            if (size_ >= capacity_) {
                growArray();
            }
            // Call binarySearch to find the insertion point
            int insertionPoint = binarySearch(element);

            //if the insertion point is negative
            //negates it and subtracts one, obtaining where it should be placed
            if (insertionPoint < 0) {
                insertionPoint = -(insertionPoint + 1);
            }
            // Shift the elements to the right, starting from the insertion point,
            // to make space for the new element. //second point, same array
            System.arraycopy(set_, insertionPoint, set_,
                    insertionPoint + 1, size_ - insertionPoint);
            // Assign the new element to the insertion point and increment the size of the set.
            set_[insertionPoint] = element;
            size_++;
            return true;

        }
        //if element is already in the array retirn false
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> elements) {
        int og = this.size();
        //looping through the elements to be added
        //if this does not contain it , add it
        for(E obj: elements){
            if(!this.contains(obj)){
                this.add(obj);
            }
        }
        int newA = this.size();
        //after the for loop, if the new array is greater
        //return true, meaning that it grew
        //even if all elements trying to add are not, still true
        return newA > og;

    }

    @Override
    public void clear() {
        size_ = 0;
    }

    @Override
    public boolean contains(E element) {
        //binary search of the element
        //it is in the set if index is >= 0
        int index = binarySearch(element);
        if(index >= 0){
            return true;
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection <? extends E> elements) {

        //looping through the elements passed in as a collection
        //if one of the elements are there, returns false
        for(E obj: elements){
            if(!this.contains(obj)){
                return false;
            }
        }
        //return true of all elements are present
        return true;
    }

    @Override
    public boolean isEmpty() {
        return (size_ == 0);
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
        List<E> elementsToRemove = new ArrayList<>(elements);
        boolean anyRemoved = false;

        for (E element : elementsToRemove) {
            if (this.contains(element)) {
                this.remove(element);
                anyRemoved = true;
            }
        }

        return anyRemoved;

//
//       boolean removed = false;
//        for(Object obj : elements) {
//            if (this.contains((E) elements)) {
//                this.remove((E) obj);
//                removed  = true;
//            }
//        }
//        return removed;
    }

    @Override
    public int size() {
        return size_;
    }

    @Override
    public E[] toArray() {
        E[] array = Arrays.copyOf(set_, this.size());
        return array;
    }

    //---------------------------------Iterator Class--------------------------------------------\\
    @Override
    public Iterator iterator() {
        return new myIterator();
    }

    class myIterator implements Iterator<E>{
        private int position_ = 0;
        @Override
        public boolean hasNext() {
            //if the position is less than the size of the array
            return size_ > 0 && position_ < size_;
        }
        @Override
        public E next() {
            //if there is a next value in the array
            //return the position in the set incremented by 1
            if (hasNext()) {
                return set_[position_++];
            }
            else {
                throw new NoSuchElementException("invalid, no next");
            }
        }

        public void remove() {
            //check position in the set it is called on
            if (position_ == 0) {
                throw new IllegalStateException("cannot remove element from empty list");
            }
                //calling remove method from BSS class
                //removing the latest position to be called by the next method
                BinarySearchSet.this.remove(set_[position_ - 1]);
                position_--;
            }

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

    public int getCapacity(){
        return capacity_;
    }

    //binary search from tree set
    private int binarySearch(E element) {
        //initializing starting points for the binary search
        int startPoint = 0;
        int endPoint = size_ - 1;
        int comparison;
        //calculating the midpoint

        //loops through and continues until range is exhausted
        while (startPoint <= endPoint) {
            // >>> right shifting to ensure non-negative midpoint
            int mid = (startPoint + endPoint) >>> 1; //same as /2
            //if a custom comparator is given in the constructor
            //uses it
            if (comparator_ != null ){
                comparison = comparator_.compare(set_[mid], element);
            }
            //otherwise uses natural order and compareTo method
            else {
                //natural order
                Comparable<? super E> midVal = (Comparable<? super E>) set_[mid];

                if (midVal == null) {
                    throw new NullPointerException("not comparable");
                }
                //comparing the midval to the element
                //saving
                comparison = midVal.compareTo(element);
            }

            if (comparison < 0) {
                //if less than zero, target is larger than mid
                //start is set to mid + 1
                startPoint = mid + 1;
                //if greater than zero, target is smaller than mid
                //
            } else if (comparison > 0) {
                endPoint = mid - 1;
            } else {
                //comparison is 0, Element found
                return mid;
            }
        }
        // Element not found, return the insertion point
        return -(startPoint + 1);
    }

}
