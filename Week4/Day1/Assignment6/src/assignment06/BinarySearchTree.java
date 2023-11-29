package assignment06;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

public class BinarySearchTree<T extends Comparable<? super T>> implements SortedSet<T> {


    public Node root_;

    public int size_;


    public BinarySearchTree() {
    }


    //-------------------------------------------NODE CLASS--------------------------------------------------\\
    class Node {
        T value_;
        Node left_;
        Node right_;
        public int height_ = -1;

        Node(T value) {
            value_ = value;
            left_ = null;
            right_ = null;
        }
    }

    //-------------------------------------UPDATING THE HEIGHT-------------------------------------------------------\\

    /**
     * driver method to calculate the height of the BST
     */
    public void updateHeight() {
        calculateHeight(root_);
    }

    /**
     *
     * @param node the node at which to begin the heigh calculation, root
     * @return return an int of the height value
     */
    private int calculateHeight(Node node) {
        if (node == null) {
            return -1;
        }

        //calculate the height of the left and right subtrees of the current node
        int leftHeight = calculateHeight(node.left_);
        int rightHeight = calculateHeight(node.right_);

        //determine the height of the current node by taking the maximum of the two heights and adding 1
        int height = Math.max(leftHeight, rightHeight) + 1;

        //update the height variable of the node to the calculated height
        node.height_ = height;

        return height;
    }

    /**
     *
     * @return the current height of the BST
     */
    public int getHeight() {
        return root_.height_;
    }

    //-----------------------------------IMPLEMENTED METHODS----------------------------------------\\
    /**
     * Ensures that this set contains the specified item.
     *
     * @param item
     *          - the item whose presence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if
     *         the input item was actually inserted); otherwise, returns false
     * @throws NullPointerException
     *           if the item is null
     */
    @Override
    public boolean add(T item) {
        if (item == null) {
            throw new NullPointerException("item attempting to be added is null.");
        }
        if (this.contains(item)) {
            return false; //item already exists in the BST, return false
        }

        //calling the recursive add method which returns the node of where this item should be added
        else {
            root_ = addRecursive(root_, item);
            //updateHeight(); //THIS IS O(N) , causing problems in my tests bc
            //recalculating the height of every node, causing O(N^2) in my test
            size_++;
            return true;
        }

    }

    /**
     *
     * @param index a Node and the index it is placed at
     * @param item The element to be added to the BST
     * @return the index of the current node after the recursive call
     */
    private Node addRecursive(Node index, T item) {
        //not a node at the current index
        if (index == null) {
            return new Node(item);
        }
        //item to add is < item at current index
        if (item.compareTo(index.value_) < 0) {
            //call recursive method on the indexes left node
            index.left_ = addRecursive(index.left_, item);
        } else if (item.compareTo(index.value_) > 0) {
            index.right_ = addRecursive(index.right_, item);
        }
        //new root of subtree, parent of item added

        return index;

    }

    /**
     * Ensures that this set contains all items in the specified collection.
     *
     * @param items
     *          - the collection of items whose presence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if
     *         any item in the input collection was actually inserted); otherwise,
     *         returns false
     * @throws NullPointerException
     *           if any of the items is null
     */
    @Override
    public boolean addAll(Collection<? extends T> items) {
        //check if the items collection is null
        if (items == null) {
            throw new NullPointerException("items attempting to be added are null.");
        }


        boolean changed = false; //initialize a boolean variable changed, to keep track of whether any items were actually added into the tree.

        //For each item, call the add method and check if it returns true, indicating that the item was inserted into the tree
        for (T item : items) {
            if (this.add(item)) {
                changed = true;
            }
        }

        return changed; //return the value of changed

    }

    /**
     * Removes all items from this set. The set will be empty after this method
     * call.
     */
    @Override
    public void clear() {
        root_ = null;
    }

    /**
     * Determines if there is an item in this set that is equal to the specified
     * item.
     *
     * @param item
     *          - the item sought in this set
     * @return true if there is an item in this set that is equal to the input item;
     *         otherwise, returns false
     * @throws NullPointerException
     *           if the item is null
     */
    @Override
    public boolean contains(T item) {
        return containsRecursive(root_, item);
    }

    /**
     *
     * @param index the index of the current node in the recursive call
     * @param item the item to check for it's presence
     * @return true if item is present, else false
     */
    private boolean containsRecursive(Node index, T item) {
        if (item == null) {
            throw new NullPointerException();
        }

        if (index == null) {
            return false;

        }

        if (item.compareTo(index.value_) == 0) {
            return true;
        }
        if (item.compareTo(index.value_) < 0) {
            return containsRecursive(index.left_, item);
        } else {
            return containsRecursive(index.right_, item);
        }

    }

    /**
     * Determines if for each item in the specified collection, there is an item in
     * this set that is equal to it.
     *
     * @param items
     *          - the collection of items sought in this set
     * @return true if for each item in the specified collection, there is an item
     *         in this set that is equal to it; otherwise, returns false
     * @throws NullPointerException
     *           if any of the items is null
     */
    @Override
    public boolean containsAll(Collection<? extends T> items) {
        if(items == null){
            throw new NullPointerException();
        }

        //check each item
        for (T item : items) {
            if (!contains(item)) {
                return false; //return false if any item is not found in the set
            }
        }
        return true;
    }

    /**
     * Returns the first (i.e., smallest) item in this set.
     *
     * @throws NoSuchElementException
     *           if the set is empty
     */
    @Override
    public T first() throws NoSuchElementException {
        if (root_ == null) {
            throw new NoSuchElementException("tree is empty");
        }

        Node current = root_;

        while (current.left_ != null) {
            current = current.left_;
        }

        return current.value_;
    }

    /**
     * Returns true if this set contains no items.
     */
    @Override
    public boolean isEmpty() {
        return root_ == null;
    }

    /**
     * Returns the last (i.e., largest) item in this set.
     *
     * @throws NoSuchElementException
     *           if the set is empty
     */
    @Override
    public T last() throws NoSuchElementException {
        if (root_ == null) {
            throw new NoSuchElementException("tree is empty");
        }

        Node current = root_;

        while (current.right_ != null) {
            current = current.right_;
        }

        return current.value_;
    }


    /**
     * Ensures that this set does not contain the specified item.
     *
     * @param item
     *          - the item whose absence is ensured in this set
     * @return true if this set changed as a result of this method call (that is, if
     *         the input item was actually removed); otherwise, returns false
     * @throws NullPointerException
     *           if the item is null
     */
    @Override
    public boolean remove(T item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (this.contains(item)) {
            root_ = removeRecursive(root_, item);
            // Call updateHeight after removing a node
            updateHeight();
            size_--;
            return true;
        }
        return false;
    }

    /**
     *
     * @param current
     * @param item
     * @return
     */
    private Node removeRecursive(Node current, T item) {
        if (current == null) {
            return null;
        }

        if (item == current.value_) {// Node to remove found, remove node

            //NODE HAS NO CHILDREN – replace this node with null in its parent node
            if (current.left_ == null && current.right_ == null) {
                return null;
            }

            //NODE HAS ONLY 1 CHILD– in the parent node, replace this node with its only child
            //returning the non-null child so it can be assigned to the parent node.
            if (current.right_ == null) {
                return current.left_;
            }

            if (current.left_ == null) {
                return current.right_;
            }

            //NODE HAS TWO CHILDREN – tree reorganization
            //Find the successor of the node to be removed. The successor is the smallest value in the right subtree of the NTR
            Node successor = findSuccessor(current.right_);
            //Replace the value of the node to be removed with the value of its successor.
            current.value_ = successor.value_;
            //Remove the successor node from the tree. Recursively calling the remove on the right subtree, with successor
            current.right_ = removeRecursive(current.right_, successor.value_);
        }

        //compare the node to be removed (NTR), to the current node the method is on (starts at the root)
        int comparisonResult = item.compareTo(current.value_);

        //if the NTR is less than the current node
        if (comparisonResult < 0) {
            current.left_ = removeRecursive(current.left_, item);//traverse left
            return current;
        }
        //else the NTR is greater than the current node
        else {
            current.right_ = removeRecursive(current.right_, item); //traverse right
        }
        return current;
    }


    /**
     *
     * @param current
     * @return
     */
    private Node findSuccessor(Node current) {
        //The successor is the smallest value in the right subtree of the node to be removed.
        // Find the leftmost node in the right subtree.
        while (current.left_ != null) {
            current = current.left_;
        }
        return current;
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
     * @throws NullPointerException
     *           if any of the items is null
     */
    @Override
    public boolean removeAll(Collection<? extends T> items) {
        if(items == null){
            throw new NullPointerException("items are null");
        }
        boolean changed = false;
        for (T item : items) {
            if (remove(item)) {
                changed = true;
            }
        }
        return changed;
    }

    /**
     * Returns the number of items in this set from the size_ member variable
     */
    @Override
    public int size() {
        return size_;
    }

    /**
     * Returns an ArrayList containing all of the items in this set, in sorted
     * order.
     */
    @Override
    public ArrayList<T> toArrayList() {
        ArrayList<T> list = new ArrayList<>(); //create a new ArrayList called list to store the sorted items
        inOrderTraversal(root_, list);//call the inOrderTraversal() method, passing the root node and the list
        return list; //return list
    }

    /**
     *
     * @param node
     * @param list
     */
    private void inOrderTraversal(Node node, ArrayList<T> list) {
        // starts from the root and moves recursively to the left child until it encounters a null node
        if (node != null) {
            inOrderTraversal(node.left_, list);
            //When it reaches the smallest element (a leaf node in the leftmost branch), it adds that element to the ArrayList before moving to the right
            list.add(node.value_);
            inOrderTraversal(node.right_, list);
        }
//        else{
//            throw new NullPointerException("empty");
//        }
    }
}

