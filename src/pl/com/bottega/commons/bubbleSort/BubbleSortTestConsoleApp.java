package pl.com.bottega.commons.bubbleSort;

/**
 * Created by radekibeatka on 24.03.16.
 */
public class BubbleSortTestConsoleApp {

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
