package pl.com.bottega.commons.bubbleSort;

/**
 * Created by radekibeatka on 24.03.16.
 */
public class BubbleSortInPlaceTestConsoleApp {

    public static void main(String[] args) {

        int[] numbers = Utils.convert(args);

        Sorter.sortInPlace(numbers);

        System.out.println("Posortowana bąbelkowo tablica wejściowa:");
        Utils.display(numbers);


    }
}
