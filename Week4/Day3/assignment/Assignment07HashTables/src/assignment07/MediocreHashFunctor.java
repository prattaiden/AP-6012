package assignment07;

public class MediocreHashFunctor implements HashFunctor{

    @Override
    public int hash(String item) {
        int c = 1;
        int ch = item.length();
        int sum = 1;
        while (c++ != ch) {
            sum *= item.charAt(c-1);
        }
        return Math.abs(sum);
        }
}
