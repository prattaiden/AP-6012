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

        int index = functor_.hash(item)%capacity_;
        if (storageLL_[index] == null) {
            storageLL_[index] = new LinkedList<>();

        }
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

        for(String item : items){

            int index = functor_.hash(item)%capacity_;
            if (storageLL_[index] == null) {
                storageLL_[index] = new LinkedList<>();
            }
            storageLL_[index].push(item);
            itAdded = true;
        }
        return itAdded;
    }

    /**
     * Removes all items from this set. The set will be empty after this method
     * call.
     */
    @Override
    public void clear() {
        size_ = 0;
        for (LinkedList<String> LL : storageLL_){
            if(LL != null) {
                LL.clear();
            }
        }

        //clear the storage?

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

        int index = functor_.hash(item)%capacity_;
        boolean found = false;
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
        boolean itContains = false;

        for(String item : items){
            if(contains(item)) {
                itContains = true;
            }
        }
        return itContains;
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
        if(item == null){
            return false;
        }
        if(!contains(item)){
            return false;
        }

        int index = functor_.hash(item)%capacity_;
        storageLL_[index].pop();
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
