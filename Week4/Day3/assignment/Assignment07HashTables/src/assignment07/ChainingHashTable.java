package assignment07;

import java.util.Collection;
import java.util.LinkedList;

public class ChainingHashTable implements Set<String>{


    private LinkedList<String>[] storageLL_;

    private int size_ = 0;

    private int capacity_ = 0;

    private HashFunctor functor_;

    //@SuppressWarnings("unchecked");
    public ChainingHashTable(int capacity, HashFunctor functor){
        storageLL_ = (LinkedList<String>[]) new LinkedList[capacity];
        functor_ = functor;
        capacity_ = capacity;
    }


    //-----------------------------------implementation------------------------------------------\\
    /**
     * Ensures that this set contains the specified item.
     *
     * @param item
     *          - the item whose presence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if
     *         the input item was actually inserted); otherwise, returns false
     */
    @Override
    public boolean add(String item) {
        if(item == null){
            return false;
        }

        if (contains(item)){
            return false;
        }

        //applying the hash function from the provided functor on the String passed in
        //take the modulo of that hash result, ensures will fall within capacity
        //save that into the index int
        int index = functor_.hash(item)%capacity_;
        //if there is not already a LL at the given index, make a new one
        if (storageLL_[index] == null || storageLL_[index].isEmpty()) {
            storageLL_[index] = new LinkedList<>();

        }
        //adding item and increasing the size
        storageLL_[index].push(item);
        size_++;
        return true;

    }

    /**
     * Ensures that this set contains all items in the specified collection.
     *
     * @param items
     *          - the collection of items whose presence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if
     *         any item in the input collection was actually inserted); otherwise,
     *         returns false
     */
    @Override
    public boolean addAll(Collection<? extends String> items) {
        if(items == null){
            return false;
        }
        boolean itAdded = false;

        //looping through the collection
        //if the item is not in the hashtable, add it, update to true
        for(String item : items){
            if(!contains(item)) {
                add(item);
                itAdded = true;
            }
        }
        return itAdded;
    }

    /**
     * Removes all items from this set. The set will be empty after this method
     * call.
     */
    @Override
    public void clear() {
        //setting the size to 0 and then looping through all the linked lists in the array
        //clearing the linked lists
        size_ = 0;
        for (LinkedList<String> LL : storageLL_){
            if(LL != null) {
                LL.clear();
            }
        }

    }

    /**
     * Determines if there is an item in this set that is equal to the specified
     * item.
     *
     * @param item
     *          - the item sought in this set
     * @return true if there is an item in this set that is equal to the input item;
     *         otherwise, returns false
     */
    @Override
    public boolean contains(String item) {
        if(item == null){
            return false;
        }
        if(isEmpty()){
            return false;
        }
        //applying the hash function from the provided functor on the String passed in
        //take the modulo of that hash result, ensures will fall within capacity
        //save that into the index int
        int index = functor_.hash(item)%capacity_;
        boolean found = false;

        //if this index exists, loop through and return true if item is found
        if (storageLL_[index] != null) {
            for(String search : storageLL_[index]){
                if(search == item){
                    found = true;
                    break;
                }
            }
        }
        return found;
    }

    /**
     * Determines if for each item in the specified collection, there is an item in
     * this set that is equal to it.
     *
     * @param items
     *          - the collection of items sought in this set
     * @return true if for each item in the specified collection, there is an item
     *         in this set that is equal to it; otherwise, returns false
     */
    @Override
    public boolean containsAll(Collection<? extends String> items) {
        if(items == null){
            return false;
        }
        if(isEmpty()){
            return false;
        }

        //loop through items in collection,
        // if any of the items are not found, return false
        for(String item : items){
            if(!contains(item)) {

                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if this set contains no items.
     */
    @Override
    public boolean isEmpty() {
        return size_ == 0;
    }

    /**
     * Ensures that this set does not contain the specified item.
     *
     * @param item
     *          - the item whose absence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if
     *         the input item was actually removed); otherwise, returns false
     */
    @Override
    public boolean remove(String item) {
        if (item == null) {
            return false;
        }
        if (!contains(item)) {
            return false;
        }

        //applying the hash function from the provided functor on the String passed in
        //take the modulo of that hash result, ensures will fall within capacity
        //save that into the index int
        int index = functor_.hash(item) % capacity_;
        //remove method from LinkedList class
        storageLL_[index].remove(item);
        size_--;
        return true;

    }

    /**
     * Ensures that this set does not contain any of the items in the specified
     * collection.
     *
     * @param items
     *          - the collection of items whose absence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if
     *         any item in the input collection was actually removed); otherwise,
     *         returns false
     */
    @Override
    public boolean removeAll(Collection<? extends String> items) {
        if(items == null){
            return false;
        }
        boolean itRemoved = false;

        //loop through items in collection,
        //if item exists, remove it
        for(String item : items){
            if(contains(item)) {
                remove(item);
                itRemoved = true;
            }
        }
        return itRemoved;
    }

    /**
     * Returns the number of items in this set.
     */
    @Override
    public int size() {
        return size_;
    }

    //old collisions method that was not good

//    public int collisions(){
//        int colCount = 0;
//        for(LinkedList<String> LL : storageLL_){
//            if(LL != null){
//                int temp = LL.size();
//                if(temp > colCount){
//                    colCount = temp;
//                }
//            }
//        }
//        return colCount;
//    }
}
