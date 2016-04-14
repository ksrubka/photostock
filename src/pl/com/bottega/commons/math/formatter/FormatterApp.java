package pl.com.bottega.commons.math.formatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Beata Iłowiecka on 09.04.16.
 */
public class FormatterApp {

    public static void main(String[] args) {



        System.out.println("********************************************************    " +
                "Metoda  używająca  gotowej  instancji  Formattera    ********************************************************\n");


        System.out.println("*************** Konstruktor Formater(String) *************** \n************ FormattingLanguage: PL ************\n");
        Formatter formatter1 = new Formatter("12345678901234");

        System.out.println(testFormatter(formatter1, FormattingLanguage.PL));

        System.out.println("*************** Konstruktor Formater(long) *************** \n************ FormattingLanguage: PL ************\n");

        Formatter formatter2 = new Formatter(2123456678919607567L);

        System.out.println(testFormatter(formatter2, FormattingLanguage.PL));


        System.out.println("*************** Konstruktor Formater(String) *************** \n*************** FormattingLanguage: ENG ***************\n");

        Formatter formatter3 = new Formatter("5567432147890194");

        System.out.println(testFormatter(formatter3, FormattingLanguage.ENG));

        System.out.println("*************** Konstruktor Formater(long) *************** \n************ FormattingLanguage: ENG ************\n");

        Formatter formatter4 = new Formatter("1587234");

        System.out.println(testFormatter(formatter3, FormattingLanguage.ENG));



        System.out.println("********************************************************    " +
                "Metody  tworzące  instancję  Formattera  z  parametru    ********************************************************");
        System.out.println("********************************************************************   " +
                " osobne  dla  String  i  long    *******************************************************************\n");



        System.out.println("*************** Konstruktor Formater(String) *************** \n************ FormattingLanguage: PL ************\n");

        System.out.println(testStringFormatter("9090909090909112", FormattingLanguage.PL));

        System.out.println("*************** Konstruktor Formater(long) *************** \n************ FormattingLanguage: PL ************\n");

        System.out.println(testLongFormatter(2123456678919607567L, FormattingLanguage.PL));

        System.out.println("*************** Konstruktor Formater(String) *************** \n*************** FormattingLanguage: ENG ***************\n");

        System.out.println(testStringFormatter("123765432147890194", FormattingLanguage.ENG));

        System.out.println("*************** Konstruktor Formater(long) *************** \n************ FormattingLanguage: ENG ************\n");

        System.out.println(testLongFormatter(1587234L, FormattingLanguage.ENG));




        System.out.println("***************************************  " +
                "J E D N A   M E T O D A   B Y   W S Z Y S T K I M I   R Z Ą D Z I Ć . . . ***************************************\n");

        System.out.print(testThemAll("1234567457945178029", "123", 98263462811L, 46909L));

        System.out.println("******************************************** " +
                ". . .   I   W   C I E M N O Ś C I   Z G R O M A D Z I Ć   ********************************************");
        System.out.println("************************ (tylko dla użytkowników Dracula Theme ;) ************************");
        System.out.println("... w przeciwnym wypadku należy gromadzić w jasności.");
    }



    public static String testThemAll(String stringFormatterParam1, String stringFormatterParam2, long longFormatterParam1, long longFormatterParam2){

        StringBuilder test = new StringBuilder();

        test.append("*************** Konstruktor Formater(String) *************** \n************ FormattingLanguage: PL ************\n");
        test.append(testStringFormatter(stringFormatterParam1, FormattingLanguage.PL));

        test.append("*************** Konstruktor Formater(long) *************** \n************ FormattingLanguage: PL ************\n");
        test.append(testLongFormatter(longFormatterParam1, FormattingLanguage.PL));

        test.append("*************** Konstruktor Formater(String) *************** \n*************** FormattingLanguage: ENG ***************\n");
        test.append(testStringFormatter(stringFormatterParam2, FormattingLanguage.ENG));

        test.append("*************** Konstruktor Formater(long) *************** \n************ FormattingLanguage: ENG ************\n");
        test.append(testLongFormatter(longFormatterParam2, FormattingLanguage.ENG));

        return test.toString();
    }

    private static String testStringFormatter(String formatterParam, FormattingLanguage lang){
        Formatter formatter = new Formatter(formatterParam);

        return testFormatter(formatter, lang);
    }

    private static String testLongFormatter(Long formatterParam, FormattingLanguage lang){
        Formatter formatter = new Formatter(formatterParam);

        return testFormatter(formatter, lang);
    }

    private static String testFormatter(Formatter formatter, FormattingLanguage lang){
        StringBuilder formatterTest = new StringBuilder();

        formatterTest.append("* Liczba: \n");
        List<List<Byte>> chunkedDigits = Formatter.chunkDigits(formatter.getDigits());
        printDigits(chunkedDigits, formatterTest);

        formatterTest.append("* Każda cyfra osobno: \n");
        String[] digits = formatter.formatDigits(lang);
        printDigits(digits, formatterTest);
        formatterTest.append("\n");

        formatterTest.append("* Cała liczba: \n");
        String formattedNumber = formatter.formatNumbers(lang);
        formatterTest.append(formattedNumber + "\n\n");

        formatterTest.append("* Cyfra z indeksu 1: \n");
        String digitAtPosition = formatter.getDigit(1, lang);
        formatterTest.append(digitAtPosition + "\n\n");

        formatterTest.append("* Cyfra z ostatniego indeksu: \n");
        String digitAtLastPosition = formatter.getLastDigit(lang);
        formatterTest.append(digitAtLastPosition + "\n\n");

        return formatterTest.toString();
    }

    private static void printDigits(List<List<Byte>> digits, StringBuilder test){
        for (List<Byte> hundred : digits){
            for (byte digit : hundred){
                // cut prefix zeros
                if (digits.indexOf(hundred) == 0 && ((hundred.indexOf(digit) == 0 || hundred.indexOf(digit) == 1) && digit == 0)){
                    continue;
                }
                test.append((digit));
            }
            test.append(" ");
        }
        test.append("\n\n");
    }

    private static void printDigits(String[] digits, StringBuilder test){
        for (String digit : digits){
            test.append((digit + " "));
        }
        test.append("\n");
    }
}
