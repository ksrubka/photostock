package pl.com.bottega.commons.math.formatter;

/**
 * Created by Beata Iłowiecka on 09.04.16.
 */
public class FormatterApp {

    public static void main(String[] args) {


        Formatter formatter1 = new Formatter("12345678");

        System.out.println("Każda cyfra osobno: ");
        String[] digits = formatter1.formatDigits("pl");
        printDigits(digits);
        System.out.println();

        System.out.println("Cała liczba: ");
        String formattedNumber = formatter1.formatNumbers(FormattingLanguage.PL);
        System.out.println(formattedNumber + "\n");

        System.out.println("Cyfra z indeksu 5: ");
        String digitAtPosition = formatter1.getDigit(5, FormattingLanguage.PL);
        System.out.println(digitAtPosition + "\n");
    }

    public static void printDigits(String[] digits){
        for (String s : digits){
            System.out.print(s + " ");
        }
        System.out.println();
    }

}
