package assignment06;

import com.sun.source.tree.Tree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

public class TimingExperiments2 {
        public static void main(String[] args) throws IOException {

            for (int N = 10000; N <= 100000; N += 10000) {
                // Creating a BST and a TreeSet for each N
                BinarySearchTree<Integer> BST = new BinarySearchTree<>();
                TreeSet<Integer> treeSet = new TreeSet<>();

                // Generating random order for insertion
                List<Integer> randomOrder = generateRandomOrder(N);

                long avgTree = 0;
                long avgBST = 0;

                long timeTakenTreeSet = 0;
                long timeTakenBST = 0;

                for (int i = 0; i < 100; i++) {
                    // Timing for add in TreeSet
//                    long startTimeTreeSet = System.nanoTime();
//                    for (int item : randomOrder) {
//                        treeSet.add(item);
//                    }
//                    long endTimeTreeSet = System.nanoTime();
//                    timeTakenTreeSet += endTimeTreeSet - startTimeTreeSet;

                    // Timing for add in BST
                    long startTimeBST = System.nanoTime();
                    for (int item : randomOrder) {
                        BST.add(item);
                    }
                    long endTimeBST = System.nanoTime();
                    timeTakenBST += endTimeBST - startTimeBST;
                }

//                avgTree = timeTakenTreeSet / 100;
//                System.out.println("TreeSet add:\t" + N + "\t" + avgTree);

                avgBST = timeTakenBST / 100;
                System.out.println("BST add:\t" + N + "\t" + avgBST);
            }
//            for(int N = 10000; N <= 100000; N += 10000){
//                //creating a bst for each N in the range, going up by 100000
//                BinarySearchTree<Integer> BST = new BinarySearchTree<>();
//                TreeSet<Integer> treeSet = new TreeSet<>();
//
//                //random order for insertion for TreeSet - self balancing
//                List<Integer> randomOrder = generateRandomOrder(N);
//
//                long avgTree = 0;
//                long avgBST = 0;
//                long TreeTimeTotal = 0;
//                long timeTakenBST = 0;
//
//                for(int i = 0; i < 100; i++) {
//                    //timing for add in Tree
//                    long startTimeTree = System.nanoTime();
//                    for (int item : randomOrder) {
//                        treeSet.add(item);
//                    }
//                    long endTimeTree = System.nanoTime();
//                    TreeTimeTotal += endTimeTree - startTimeTree;
//
//
//                    //time for add in BST
//                    long startTimeBST = System.nanoTime();
//                    for (int item : randomOrder) {
//                        BST.add(item);
//                    }
//                    long endtimeBST = System.nanoTime();
//                    timeTakenBST += endtimeBST - startTimeBST;
//
//                }
//                avgTree = TreeTimeTotal/100;
//                System.out.println("tree contains:\t" + N + "\t" + avgTree);
//                avgBST = timeTakenBST/100;
//                System.out.println("BST contains:\t" + N + "\t" + avgBST);
//



                //Timing for contains method in Tree
//                long totalTreeTime = 0;
//                for(int item : randomOrder){
//                    long startTreeContains = System.nanoTime();
//                    treeSet.contains(item);
//                    long endTreeContains = System.nanoTime();
//
//                    totalTreeTime += endTreeContains - startTreeContains;
//
//                }
//                long avgTree = totalTreeTime/randomOrder.size();
//
//                //System.out.println("Tree contains:\t" + N + "\t" + avgTree);
//
//                //Timing for contains in BST
//                long totalBSTTime = 0;
//                for(int item : randomOrder){
//                    long startBSTContains = System.nanoTime();
//                    BST.contains(item);
//                    long endBSTContains = System.nanoTime();
//
//                    totalBSTTime += endBSTContains - startBSTContains;
//                }
//
//                long avgBST = totalBSTTime/randomOrder.size();
//
//               System.out.println("BST contains:\t" + N + "\t" + avgBST);
        }



    public static List<Integer> generateRandomOrder(int N){
            List<Integer> randomOrder = new ArrayList<>();

            //adding integers from 1 to N in order
            for(int i = 1; i <= N; i++){
                randomOrder.add(i);
            }

            //method to put this list into a randomly shuffled order
            Collections.shuffle(randomOrder);

            return randomOrder;
    }

}
