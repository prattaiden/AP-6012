package lab06;

public interface  PriorityQueue<T extends Comparable <? super T> > {

    /**
     * Inserts the specified element into this priority queue.
     * @param element element to insert
     */
   void add(T element);


    /**
     * Retrieves and removes the head of this queue, or null if this queue is empty.
     *
     * @return the head of this queue, or null if this queue is empty
     */
    T removeMin();


    /**
     * @return true if queue is empty
     */
    boolean isEmpty();



}


