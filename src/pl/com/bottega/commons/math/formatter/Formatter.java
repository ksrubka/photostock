package pl.com.bottega.commons.math.formatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Beata Iłowiecka on 06.04.16.
 */
public class Formatter {

    public enum FormattingLanguage {
        PL, ENG;
    }

    List<Byte> digits = new ArrayList<>();

    public Formatter(long number) {
        recursiveCutter(number);
        Collections.reverse(digits);
    }

    private void recursiveCutter(long number) {
        long digit = number % 10;
        digits.add((byte)digit);
        long newValue = number / 10;
        if (newValue != 0)
            recursiveCutter(newValue);
    }

    public String[] formatDigits(FormattingLanguage lang) {
        String[] result = new String[digits.size()];
        int nr = 0;

        for (byte digit : digits) {
            result[nr++] = generateDigit2(digit, lang);
        }
        return result;
    }

    private static final String[][] DICTIONARY = {
            {"zero", "jeden", "dwa", "trzy", "cztery", "pięć", "sześć", "siedem", "osiem", "dziewięć"},
            {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"}
    };

    private String generateDigit2(byte digit, FormattingLanguage lang) {
       /* byte langNr;
        switch (lang){
            case PL:
                langNr = 0;
                break;
            case ENG:
                langNr = 1;
                break;
            default:
                throw new IllegalArgumentException(lang + " is not supported.");
        }*/
        return DICTIONARY[lang.ordinal()][digit];
    }

    private String generateDigit(byte digit, String lang) {
        switch(lang) {
            case "pl":
                switch (digit) {
                    case 0:
                        return "zero";
                    case 1:
                        return "jeden";
                    case 2:
                        return "dwa";
                    case 3:
                        return "trzy";
                    case 4:
                        return "cztery";
                    case 5:
                        return "pięć";
                    case 6:
                        return "sześć";
                    case 7:
                        return "siedem";
                    case 8:
                        return "osiem";
                    case 9:
                        return "dziewięć";
                }
                break;

            case "eng":
                switch (digit) {
                    case 0:
                        return "zero";
                    case 1:
                        return "one";
                    case 2:
                        return "two";
                    case 3:
                        return "three";
                    case 4:
                        return "four";
                    case 5:
                        return "five";
                    case 6:
                        return "six";
                    case 7:
                        return "seven";
                    case 8:
                        return "eight";
                    case 9:
                        return "nine";
                }
                break;
            default:
                throw new IllegalArgumentException(lang + " is not supported.");
        }
        throw new RuntimeException("Coś dziwnego z danymi " + lang + " " + digit);
    }
}
