package assignment07;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static assignment07.RandomString.generateRandomString;
import static org.junit.jupiter.api.Assertions.*;

class ChainingHashTableTest {

    List<String> allStrings = new ArrayList<>();

    List<String> sameCharStrings = new ArrayList<>();



    @BeforeEach
    public void setUp(){
        allStrings.add("a");
        allStrings.add("d");
        allStrings.add("v");
        allStrings.add("t");
        allStrings.add("f");
        allStrings.add("r");

        sameCharStrings.add("crab");
        sameCharStrings.add("crap");
        sameCharStrings.add("car");
        sameCharStrings.add("char");
        sameCharStrings.add("curry");
        sameCharStrings.add("clang");
    }


    @Test
    public void testBad(){
        BadHashFunctor bad = new BadHashFunctor();
        ChainingHashTable chainingHashTable = new ChainingHashTable(10, bad);


        assertTrue(chainingHashTable.add("hi"));
        assertTrue(chainingHashTable.contains("hi"));


        assertTrue(chainingHashTable.addAll(allStrings));
        assertTrue(chainingHashTable.containsAll(allStrings));

        chainingHashTable.addAll(sameCharStrings);


    }

    @Test
    public void testMediocre(){
        MediocreHashFunctor mediocre = new MediocreHashFunctor();
        ChainingHashTable chainingHashTable = new ChainingHashTable(10, mediocre);

        assertTrue(chainingHashTable.add("mediocre"));
        assertTrue(chainingHashTable.contains("mediocre"));

        assertTrue(chainingHashTable.addAll(allStrings));
        assertTrue(chainingHashTable.containsAll(allStrings));

    }

    @Test
    public void testGood(){
        GoodHashFunctor good = new GoodHashFunctor();
        ChainingHashTable chainingHashTable = new ChainingHashTable(10, good);

        assertTrue(chainingHashTable.add("good"));
        assertTrue(chainingHashTable.contains("good"));

        assertTrue(chainingHashTable.addAll(allStrings));
        assertTrue(chainingHashTable.containsAll(allStrings));
    }

    @Test
    public void randomNum(){
        BadHashFunctor bad = new BadHashFunctor();
        MediocreHashFunctor mid = new MediocreHashFunctor();
        for(int i = 1; i <= 10; i++) {
            System.out.println(generateRandomString(i));
            System.out.println(bad.hash(generateRandomString(i)));
            System.out.println(mid.hash(generateRandomString(i)));
        }


    }





}