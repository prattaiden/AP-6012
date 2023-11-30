package assignment07;

public class BadHashFunctor implements HashFunctor{

    //bad hash functor
    @Override
    public int hash(String item) {
        return item.charAt(0);
    }
}

