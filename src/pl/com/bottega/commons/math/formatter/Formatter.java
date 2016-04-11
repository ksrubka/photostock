package pl.com.bottega.commons.math.formatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Formatter {

    public List<Byte> digits = new ArrayList<>();
    private long number;
    private boolean godMode = true;

    public Formatter(long number) {
        if (godMode)//iterować jest rzeczą ludzką, rekursja jest rzeczą boską
            recursiveCutter(number);
        else
            iterativeCutter(number);

        Collections.reverse(digits);
    }

    public Formatter(String number){
        this.number = Integer.valueOf(number);

        char[] digits = number.toCharArray();

        for (char character : digits){
            byte digit = Byte.parseByte(Character.toString(character));
            this.digits.add(digit);
        }
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
     * @return dla 123 zwraca sto dwadzieścia trzy
     */
    public String formatNumbers(FormattingLanguage lang) throws IllegalArgumentException {

        switch (lang){
            case PL:
                ArrayList<ArrayList<Byte>> chunkedDigits = UtilsPL.chunkArray(digits);
                String result = UtilsPL.formatBigNumber(chunkedDigits);
                return result;
            case ENG:
                break;
            default:
                break;
        }
        throw new IllegalArgumentException("This language is not supported.");
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
    public String getLastDigit(FormattingLanguage lang) throws IllegalArgumentException {

        switch (lang){
            case PL:
                byte lastDigitPL = digits.get(digits.size() - 1);
                String lastPL = DICTIONARY[0][lastDigitPL];
                return lastPL;
            case ENG:
                byte lastDigitENG = digits.get(digits.size() - 1);
                String lastENG = DICTIONARY[0][lastDigitENG];
                return lastENG;
        }
        throw new IllegalArgumentException("This language is not supported.");
    }


    /**
     *
     * @param position
     * @param lang
     * @return digit at given position
     */
    public String getDigit(int position, FormattingLanguage lang) {
        switch (lang){
            case PL:
                byte digitPL = digits.get(position);
                String digit1 = DICTIONARY[0][digitPL];
                return digit1;
            case ENG:
                byte digitENG = digits.get(position);
                String digit2 = DICTIONARY[0][digitENG];
                return digit2;
        }
        throw new IllegalArgumentException("This language is not supported.");
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