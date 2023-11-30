package assignment07;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import static assignment07.RandomString.generateRandomString;
import static assignment07.RandomString.getRandomNumber;

public class Assignment07TimingExperiment {

    public static void main(String[] args) throws IOException {

        BadHashFunctor bad = new BadHashFunctor();
        ChainingHashTable badHT = new ChainingHashTable(100, bad);

        MediocreHashFunctor med = new MediocreHashFunctor();
        ChainingHashTable medHT = new ChainingHashTable(10000000, med);

        GoodHashFunctor good = new GoodHashFunctor();
        ChainingHashTable goodHT = new ChainingHashTable(10000000, good);



        for (int N = 2000; N <= 270000; N *= 2) {


            long totalAdd = 0;
            //set for keeping track fo collisions
            HashSet<Integer> set = new HashSet<>();
            int count = 0;

            for(int j = 0; j < N; j++) {
                int randomNum = getRandomNumber(1, 10);
                String randomString = generateRandomString(randomNum);
                medHT.add(randomString);

                //----------collision calculation--------------------\\
                int temp  = med.hash(randomString) % 10000000;
                if(!set.add(temp)){count++;}
                //---------------------------------------------------\\
            }
            String rand = generateRandomString(11);
//            System.out.println(med.hash(rand));
                long startAdd = System.nanoTime();
                medHT.add(rand);
                long endAdd = System.nanoTime();
                totalAdd = endAdd - startAdd;


            System.out.println("good add:\t" + N + "\t" + totalAdd + "\tcollisions:\t" + count);
        }
    }
}
