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
        Formatter formatter1 = new Formatter("12345678901234");

        /*System.out.println("get digits: ");
        List<Byte> copyDigits1 = formatter1.getDigits();
        printArray((ArrayList<Byte>) copyDigits1);

        System.out.println("This is chunkedArray:");
        ArrayList<ArrayList<Byte>> chunkArray = UtilsPL.chunkArray(copyDigits1);
        printDigits1(chunkArray);*/

        System.out.println("* Każda cyfra osobno: ");
        String[] digits = formatter1.formatDigits("pl");
        printDigits(digits);
        System.out.println();

        System.out.println("* Cała liczba: ");
        String formattedNumber = formatter1.formatNumbers(FormattingLanguage.PL);
        System.out.println(formattedNumber + "\n");

        System.out.println("* Cyfra z indeksu 5: ");
        String digitAtPosition = formatter1.getDigit(5, FormattingLanguage.PL);
        System.out.println(digitAtPosition + "\n");

        // *************** Drugi test ***************
        System.out.println("*************** Drugi test *************** \n*** Konstruktor Formater(long) ***\n");

        Formatter formatter2 = new Formatter(2123456678919607567L);

        /*System.out.println("get digits: ");
        List<Byte> copyDigits = formatter2.getDigits();
        printArray((ArrayList<Byte>) copyDigits);

        System.out.println("This is chunkedArray:");
        ArrayList<ArrayList<Byte>> chunkArray1 = UtilsPL.chunkArray(copyDigits);
        printDigits1(chunkArray1);*/

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


        // *************** Trzeci test ***************
        System.out.println("*************** Trzeci test *************** \n*** FormattingLanguage: ENG ***\n");

        Formatter formatter3 = new Formatter("1234789019876587234");

        System.out.println("* Każda cyfra osobno: ");
        String[] digits3 = formatter3.formatDigits("eng");
        printDigits(digits3);
        System.out.println();

        System.out.println("* Cała liczba: ");
        String formattedNumber3 = formatter3.formatNumbers(FormattingLanguage.ENG);
        System.out.println(formattedNumber3 + "\n");

        System.out.println("* Cyfra z indeksu 5: ");
        String digitAtPosition3 = formatter3.getDigit(5, FormattingLanguage.ENG);
        System.out.println(digitAtPosition3 + "\n");
    }


    public static void printDigits(String[] digits){
        for (String s : digits){
            System.out.print(s + " ");
        }
        System.out.println();
    }

    /*public static void printArray(ArrayList<Byte> array) {
        for (Byte digit : array) {
            System.out.print(digit);
        }
        System.out.println("\n");
    }

    public static void printDigits1(ArrayList<ArrayList<Byte>> digits){
        for (ArrayList<Byte> array : digits){
            for (byte b : array){
                System.out.print(b);
            }
        }
        System.out.println("\n");
    }*/
}
