package pl.com.bottega.commons.math.bubble_sort;

/**
 * Created by Beata Iłowiecka on 06.04.16.
 */
public class BubbleSortInPlaceTestApp {

    public static void main(String[] args) {

        int[] numbers = Utils.convert(args);

        Sorter.sortInPlace(numbers);

        System.out.println("Posortowana bąbelkowo tablica wejściowa:");
        Utils.display(numbers);
    }
}
