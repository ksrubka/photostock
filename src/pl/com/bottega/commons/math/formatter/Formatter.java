package pl.com.bottega.commons.math.formatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Formatter {
    private List<Byte> digits = new ArrayList<>();
    private boolean godMode = true;


    public Formatter(long number) {
        if (godMode)//iterować jest rzeczą ludzką, rekursja jest rzeczą boską
            recursiveCutter(number);
        else
            iterativeCutter(number);

        Collections.reverse(digits);
    }

    public Formatter(String number){
        //TODO zaimplementować, ALE NIE jako wywołanie powyższego konstruktora, tylko jako przejśćie po znakach
        char[] digit = number.toCharArray();
        //...
    }

    private void recursiveCutter(long number) {
        long digit = number % 10;
        digits.add((byte)digit);
        long newValue = number / 10;
        if (newValue != 0)
            recursiveCutter(newValue);
    }

    private void iterativeCutter(long number){
        while (number != 0){
            long digit = number % 10;
            digits.add((byte)digit);
            number = number / 10;//zmiana ALE kopii paramteru metody,
        }
    }

    /**
     *
     * @param lang
     * @return dla 123 zwraca sto dwadzieśćia trzy
     */
    public String formatNumbers(String lang) {
        return null; //TODO zaimplementować
    }

    /**
     *
     * @param lang
     * @return dla 123 zwraca jeden dwa trzy
     */
    public String[] formatDigits(String lang) {
        String[] result = new String[digits.size()];

        int nr = 0;
        for (Byte digit : digits) {
            result[nr++] = generteDigit2(digit, lang);
        }

        return result;
    }

    /**
     *
     * @param lang
     * @return for 123 return "trzy"
     */
    public String getLastDigit(String lang) {
        return null;//TODO
    }

    /**
     *
     * @param position
     * @param lang
     * @return digit at given position
     */
    public String getDigit(int position, String lang) {
        return null;//TODO
    }

    private static final String[][] DICTIONARY = {
            {"zero", "jeden", "dwa", "trzy", "cztery", "pięc", "sześć", "siedem", "osiem", "dziewięc"},
            {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", " nine"}
    };

    private String generteDigit2(Byte digit, String lang){
        byte langNr;
        switch(lang){
            case "pl":
                langNr = 0;
                break;
            case "en":
                langNr = 1;
                break;
            default:
                throw new IllegalArgumentException(lang + " is not supported");
        }
        return DICTIONARY[langNr][digit];
    }

    private String generteDigit(Byte digit, String lang){
        switch(lang){
            case "pl":
                switch(digit){
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
            case "en":
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
                throw new IllegalArgumentException(lang + " is not supported");
        }
        throw new RuntimeException("coś dziwnego z danymi " + lang + " " + digit);
    }


}