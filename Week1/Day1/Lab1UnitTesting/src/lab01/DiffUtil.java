package lab01;

public class DiffUtil {


    public static int findSmallestDiff(int[] a) {

        if (a.length < 2) {
            return -1;
        }

        //subracting the element in position 1 from the element in position 0
        int diff = Math.abs(a[0] - a[1]);

        //for loop looping through the length of the array
        for (int i = 0; i < a.length; i++) {
            //inner for loop setting j = to i + 1
            //
            for (int j = i + 1; j < a.length; j++) {
                //added absolute value
                int tmp_diff = Math.abs(a[i] - a[j]);

                if (tmp_diff < diff)
                    diff = tmp_diff;
            }
        }

        return diff;
    }

}

