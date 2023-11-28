package lab06;

import java.util.TreeSet;

public class TreeSetPQ<T extends Comparable<? super T>> implements PriorityQueue<T> {


    private  java.util.TreeSet<T> treeSet_ = new TreeSet<>();


    @Override
    public void add(T element) {
        treeSet_.add(element);
    }

    @Override
    public T removeMin() {
        //removes left most element
        return treeSet_.pollFirst();
    }

    @Override
    public boolean isEmpty() {
        return treeSet_.isEmpty();
    }
}
