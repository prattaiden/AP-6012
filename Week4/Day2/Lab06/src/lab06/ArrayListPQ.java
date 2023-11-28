package lab06;

import java.util.ArrayList;

public class ArrayListPQ<T extends Comparable<? super T>> implements PriorityQueue<T>{

    ArrayList<T> arrayList;

    int right_;
    int left_;
    int size_;
    int parent_;

    int root_ = 0;

   //no argument constructor
    ArrayListPQ(ArrayList arr){
        arrayList = arr;
        heapify(arrayList);
    }

    ArrayListPQ() {
        arrayList = new ArrayList<T>();
    }

    private void heapify(ArrayList arr){
        for(int i = arr.size()/2 ; i>= 0; i--){
            percolateDown(i);

        }
    }
    private void percolateDown(int index){
        right_ = (2 * index) + 2;
        left_ = (2 * index) + 1;
        parent_ = (index - 1)/2;
        size_ = arrayList.size();

        boolean canSwapLeft = (left_ < size_ && parent_ > left_);
        boolean canSwapRight = (right_ < size_ && parent_ > right_);

        //two children that can be swapped
        if(canSwapLeft && canSwapRight){
            int min = Math.min(right_, left_); //getting the smallest of the two children
            swap(arrayList, parent_, min);
        }

        if(canSwapLeft && !canSwapRight){
            swap(arrayList,parent_,left_);
        }

        if(canSwapRight && !canSwapLeft){
            swap(arrayList,parent_,right_);
        }

    }


    private void percolateUp(int index) {
        parent_ = (index - 1)/2;
        T currentValue = arrayList.get(index);
        T parentValue = arrayList.get(parent_);

        while ( currentValue.compareTo(parentValue) < 0 &&  currentValue != arrayList.get(0) ) {
            swap(arrayList, parent_, index);
        }

    }

    private void swap(ArrayList<T> arr, int a, int b){
        T temp = arr.get(a);
        arr.set(a, arr.get(b));
        arr.set(b, temp);
    }
    @Override
    public void add(T element) {
        int elementIndex = size_ -1;
        arrayList.set(elementIndex, element);
        percolateUp(elementIndex);
    }

    @Override
    public T removeMin() {
        T ret = arrayList.get(root_);
        swap(arrayList, root_, size_ -1);
        arrayList.remove(size_ -1);
        percolateDown(0); //fixing the ordering


        return ret;
    }

    @Override
    public boolean isEmpty() {
        return size_ == 0;
    }
}
