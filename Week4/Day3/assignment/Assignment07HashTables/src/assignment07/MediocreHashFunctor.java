package assignment07;

public class MediocreHashFunctor implements HashFunctor{


    //takes the sum of each character in the strings and multiplies it's ascii value
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
