package assignment06;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpellCheckerTest {

    SpellChecker mySC = new SpellChecker(new File("myDictionary.txt"));

    private static void run_spell_check(SpellChecker sc, String documentFilename) {

        File doc = new File(documentFilename);
        List<String> misspelledWords = sc.spellCheck(doc);
        if (misspelledWords.size() == 0) {
            System.out.println("There are no misspelled words in file " + doc + ".");
        } else {
            System.out.println("The misspelled words in file " + doc + " are:");
            for (String w : misspelledWords) {
                System.out.println("\t" + w);
            }
        }
    }
    @Test
    public void testAddSpellCheck(){


        run_spell_check(mySC, "testdic.txt");

        mySC.addToDictionary("aiden");

        run_spell_check(mySC,"testdic.txt");

    }

    @Test
    public void testRemove(){
        mySC.removeFromDictionary("aiden");

        run_spell_check(mySC, "testdic.txt");
    }

    @Test
    public void testListSpellChecker(){
        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add("bye");
        list.add("world");
        list.add("wassup");

        SpellChecker myList = new  SpellChecker(list);

        run_spell_check(myList, "testdic.txt");

    }


}