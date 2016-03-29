package pl.com.bottega.commons.bubbleSort;

/**
 * Created by radekibeatka on 24.03.16.
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
