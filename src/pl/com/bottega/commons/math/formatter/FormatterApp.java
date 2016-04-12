package pl.com.bottega.commons.math.formatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Beata Iłowiecka on 09.04.16.
 */
public class FormatterApp {

    public static void main(String[] args) {

        // *************** Pierwszy test ***************
        System.out.println("*************** Pierwszy test *************** \n*** Konstruktor Formater(String) ***\n");
        Formatter formatter1 = new Formatter("7654321098098750765");

        System.out.println("* Każda cyfra osobno: ");
        String[] digits = formatter1.formatDigits("pl");
        printDigits(digits);

        System.out.println("* Cała liczba: ");
        String formattedNumber = formatter1.formatNumbers(FormattingLanguage.PL);
        System.out.println(formattedNumber);

        System.out.println("* Cyfra z indeksu 5: ");
        String digitAtPosition = formatter1.getDigit(5, FormattingLanguage.PL);
        System.out.println(digitAtPosition + "\n");

        // *************** Drugi test ***************
        System.out.println("*************** Drugi test *************** \n*** Konstruktor Formater(long) ***\n");

        Formatter formatter2 = new Formatter(986789697);

        System.out.println("get digits: ");
        List<Byte> copyDigits = formatter2.getDigits();

        ArrayList<ArrayList<Byte>> chunkArray = UtilsPL.chunkArray(copyDigits);
        printDigits1(chunkArray);

        System.out.println("* Każda cyfra osobno: ");
        String[] digits2 = formatter2.formatDigits("pl");
        printDigits(digits2);
        System.out.println();

        System.out.println("* Cała liczba: ");
        String formattedNumber2 = formatter2.formatNumbers(FormattingLanguage.PL);
        System.out.println(formattedNumber2 + "\n");

        System.out.println("* Cyfra z indeksu 5: ");
        String digitAtPosition2 = formatter2.getDigit(5, FormattingLanguage.PL);
        System.out.println(digitAtPosition2 + "\n");

    }

    public static void printDigits(String[] digits){
        for (String s : digits){
            System.out.print(s + " ");
        }
        System.out.println();
    }

    public static void printDigits1(ArrayList<ArrayList<Byte>> digits){
        for (ArrayList<Byte> array : digits){
            for (byte b : array){
                System.out.print(b);
            }
        }
        System.out.println();
    }
}
