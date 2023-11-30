package assignment07;



public class GoodHashFunctor implements HashFunctor{

    //djb2 by Dan Bernstein
    @Override
    public int hash(String item) {
        int hash = 5381;
        int c = 1;
        int ch = item.length();
        while (c++ != ch) {
            hash = ((hash << 5) + hash) + item.charAt(c - 1); /* hash * 33 + c */
        }
        return Math.abs(hash);
    }

}
