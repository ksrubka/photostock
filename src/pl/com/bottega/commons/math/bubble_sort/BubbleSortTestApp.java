package pl.com.bottega.commons.math.bubble_sort;

/**
 * Created by Beata Iłowiecka on 06.04.16.
 */
public class BubbleSortTestApp {

    public static void main(String[] args) {

        int[] numbers = Utils.convert(args);

        int[] sorted = Sorter.sort(numbers);

        System.out.println("Posortowana bąbelkowo tablica:");
        Utils.display(sorted);

        System.out.println("\n");

        System.out.println("Wyświetlenie tablicy wejściowej dla porównania");
        Utils.display(numbers);
    }
}
