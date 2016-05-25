package pl.com.bottega.commons.math.formatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Formatter {

    public List<Byte> digits = new ArrayList<>();
    private long number = 0L;
    private boolean godMode = true;

    public Formatter(long number) {
        if (godMode)//iterować jest rzeczą ludzką, rekursja jest rzeczą boską
            recursiveCutter(number);
        else
            iterativeCutter(number);
        Collections.reverse(digits);
    }

    public Formatter(String number){
        this.number = Long.valueOf(number);
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
        while (number != 0) {
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
                List<List<Byte>> chunkedDigitsPL = chunkDigits(digits);
                return UtilsPL.formatBigNumber(chunkedDigitsPL);
            case ENG:
                List<List<Byte>> chunkedDigitsENG = chunkDigits(digits);
                return UtilsENG.formatBigNumber(chunkedDigitsENG);
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
    public String[] formatDigits(FormattingLanguage lang) {
        String[] result = new String[digits.size()];
        int nr = 0;
        for (Byte digit : digits)
            result[nr++] = generateDigit2(digit, lang);
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
                return DICTIONARY[0][lastDigitPL];
            case ENG:
                byte lastDigitENG = digits.get(digits.size() - 1);
                return DICTIONARY[1][lastDigitENG];
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
                return DICTIONARY[0][digitPL];
            case ENG:
                byte digitENG = digits.get(position);
                return DICTIONARY[1][digitENG];
        }
        throw new IllegalArgumentException("This language is not supported.");
    }

    private static final String[][] DICTIONARY = {
            {"zero", "jeden", "dwa", "trzy", "cztery", "pięć", "sześć", "siedem", "osiem", "dziewięć"},
            {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", " nine"}
    };

    private String generateDigit2(Byte digit, FormattingLanguage lang) {
        byte langNr;
        switch(lang){
            case PL:
                langNr = 0;
                break;
            case ENG:
                langNr = 1;
                break;
            default:
                throw new IllegalArgumentException(lang + " is not supported");
        }
        return DICTIONARY[langNr][digit];
    }

    // Splits an array into equal 3-element arrays. If number of elements is not dividable by 3,
    // additional zero's are inserted into the first array to make it contain 3-elements.
    // in: {1}                      out: {{0,0,1}}
    // in: {0,1,2,3}                out: {{0,0,0},{1,2,3}}
    // in: {1,2,3,4}                out: {{0,0,1},{2,3,4}}
    // in: {1,2,3,4,5},             out: {{0,1,2},{3,4,5}}
    // in: {9,8,7,6,5,4,3,2,1,0}    out: {{0,0,9},{8,7,6},{5,4,3},{2,1,0}}
    public static List<List<Byte>> chunkDigits(List<Byte> digits) {
        byte chunkSize = 3;
        int remainingDigits = digits.size() % chunkSize;
        int numOfChunks = (int) Math.ceil((double) digits.size() / chunkSize);
        List<List<Byte>> result = new ArrayList<>();
        result.add(getFirstHundred(remainingDigits, digits));
        int start = remainingDigits;
        if (remainingDigits == 0)
            start = 3;
        for (int chunkNumber = 1; chunkNumber < numOfChunks; chunkNumber++) {
            List<Byte> tempArray1 = new ArrayList<>();
            for (int hundredNumber = 1; hundredNumber <= chunkSize; hundredNumber++) {
                tempArray1.add(digits.get(start));
                start++;
            }
            result.add(tempArray1);
        }
        return result;
    }

    private static List<Byte> getFirstHundred(int remainingDigits, List<Byte> digits) {
        List<Byte> tempArray = new ArrayList<>();
        switch (remainingDigits){
            case 1:
                tempArray.add((byte) 0);
                tempArray.add((byte) 0);
                tempArray.add((digits.get(0)));
                break;
            case 2:
                tempArray.add((byte) 0);
                tempArray.add((digits.get(0)));
                tempArray.add((digits.get(1)));
                break;
            case 0:
                tempArray.add((digits.get(0)));
                tempArray.add((digits.get(1)));
                tempArray.add((digits.get(2)));
                break;
            default:
                break;
        }
        return tempArray;
    }

    public List<Byte> getDigits() {
        return digits;
    }
}