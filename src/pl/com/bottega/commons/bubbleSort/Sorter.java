package pl.com.bottega.commons.bubbleSort;

/**
 * Created by radekibeatka on 24.03.16.
 */
public class Sorter {

    public static int[] sort(int[] numbers){

        int[] anArray = numbers.clone();

        int arraySize = numbers.length;

        for (int i = arraySize -1; i > 1; i--){
            for (int j = 0; j < i; j++){
                if (anArray[j] > anArray[j+1])
                    swapValues(j, j+1, anArray);
            }
        }
        return anArray;
    }

    public static void sortInPlace(int[] numbers){

        int arraySize = numbers.length;

        for (int i = arraySize -1; i > 1; i--){
            for (int j = 0; j < i; j++){
                if (numbers[j] > numbers[j+1])
                    swapValues(j, j+1, numbers);
            }
        }
    }

    private static void swapValues(int index1, int index2, int[] numbers){

        int cupOfInt = numbers[index1];

        numbers[index1] = numbers[index2];
        numbers[index2] = cupOfInt;

    }
}
