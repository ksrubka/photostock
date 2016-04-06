package pl.com.bottega.commons.math.bubble_sort;

/**
 * Created by Beata Iłowiecka on 06.04.16.
 */
public class Utils {

    public static int[] convert(String[] numbers){

        int[] anArray = new int[numbers.length];

        for (int i = 0; i < numbers.length; i++){
            anArray[i] = Integer.parseInt(numbers[i]);
        }
        return anArray;
    }

    public static void display(int[] numbers){

        for (int i : numbers){
            System.out.printf(i + " ");
        }
    }
}
