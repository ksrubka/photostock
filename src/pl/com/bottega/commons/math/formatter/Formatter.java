package pl.com.bottega.commons.math.formatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Formatter {

    public enum FormattingLanguage {
        PL, ENG;
    }

    public List<Byte> digits = new ArrayList<>();
    private long number;
    private boolean godMode = true;
    public byte[] bytes = {1, 2, 3};

    public long example = 1234567891234567890L;
    public int i = 247;

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

    public static final String[] HUNDREDS = {"", "sto", "dwieście", "trzysta", "czterysta", "pięćset", "sześćset", "siedemset", "osiemset", "dziewięćset"};

    public static final String[] TENS = {"", "", "dwadzieścia", "trzydzieści", "czterdzieści", "pięćdziesiąt", "sześćdziesiąt",
            "siedemdziesiąt", "osiemdziesiąt", "dziewięćdziesiąt"};

    public static final String[] SEVERALS = {"", "jeden", "dwa", "trzy", "cztery", "pięć", "sześć", "siedem", "osiem", "dziewięć","dziesięć",
            "jedenaście", "dwanaście", "trzynaście", "czternaście", "piętnaście", "szesnaście",
            "siedemnaście", "osiemnaście", "dziewiętnaście"};

    public static final String[] SEVERALS_FOR_BIG_NUMBERS = {"", "", "dwa", "trzy", "cztery", "pięć", "sześć", "siedem", "osiem", "dziewięć","dziesięć",
            "jedenaście", "dwanaście", "trzynaście", "czternaście", "piętnaście", "szesnaście",
            "siedemnaście", "osiemnaście", "dziewiętnaście"};

    public static final String[][] BIG_NUMBERS = {
            {"tysiąc", "milion", "miliard", "bilion", "biliard", "trylion"},
            {"tysiące", "miliony", "miliardy", "biliony", "biliardy", "tryliony"},
            {"tysięcy", "milionów", "miliardów", "bilionów", "biliardów", "trylionów"}
    };


    /**
     *
     * @param lang
     * @return dla 123 zwraca sto dwadzieścia trzy
     */
    public String formatNumbers(String lang) {
        //TODO zaimplementować
        // digits.get(0);
        String[] result = new String[digits.size()];

        int numOfDigits = digits.size();
        int flag = numOfDigits % 3;

        int index = 0;

        for (Byte digit : digits){
            if (flag == 0){
                result[index++] = formatFromHundreds(digit);
            }

        }
        return "";
    }

    private String formatFromHundreds(byte digit) {
        if (digit == 0);
        return "";
      /*  if (digits.size() > 16){

        }
        else if ()*/
    }

    private String formatFromTens(int numOfDigits){
        return "";
    }

    private String formatFromUnits(int numOfDigits){
        return "";
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