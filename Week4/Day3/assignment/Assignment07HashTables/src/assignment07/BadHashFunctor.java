package assignment07;

public class BadHashFunctor implements HashFunctor{

    //first character ascii value in the string
    @Override
    public int hash(String item) {
        return item.charAt(0);
    }
}

