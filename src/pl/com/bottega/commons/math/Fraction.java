package pl.com.bottega.commons.math;

import pl.com.bottega.commons.math.formatter.Formatter;
import pl.com.bottega.commons.math.formatter.FormattingLanguage;

/**
 * Created by Beata Iłowiecka on 19.03.2016.
 */
public class Fraction {

    private final int numerator;
    private final int denominator;

    public static final Fraction ONE = new Fraction(1, 1);
    public static final Fraction ZERO = new Fraction(0, 1);

    /**
     * Klasa modeluje ułamek
     *
     * @param numerator
     * @param denominator
     *
     * @throws  IllegalArgumentException gdy mianownik jest równy 0
     */
    public Fraction(int numerator, int denominator) throws IllegalArgumentException {
        if (denominator == 0)
            throw new IllegalArgumentException("Minownik nie może być równy zero");

        this.numerator = numerator;
        this.denominator = denominator;
    }

    public Fraction(int numerator) {
        this.numerator = numerator;
        this.denominator = 10;
        // nie chcemy walidować sprawdzać poprawnosć liczby 10
        //this(numerator, 10);
    }

    /**
     *
     * @param literal ułamek w reprezentaacji licznik/mianownik, np. 3/4
     */
    public Fraction(String literal) throws IllegalArgumentException {

        String[] words = literal.split("/");

        if (words.length != 2)
            throw new IllegalArgumentException("To nie jest ułamek");
        try {
            numerator = Integer.parseInt(words[0]);
            denominator = Integer.parseInt(words[1]);

            if (denominator == 0)
                throw new IllegalArgumentException("Zero w mianowniku");
        }
        catch (NumberFormatException ex){
            throw new IllegalArgumentException("To nie jest ułamek" + ex);
        }
    }

    public Fraction add(Fraction addend){

        if (denominator == addend.denominator){
            int sumOfNumerators = numerator + addend.numerator;
            return new Fraction(sumOfNumerators, denominator);
        } else {
            int thisNumerator = numerator * addend.denominator;
            int addendNumerator = denominator * addend.numerator;
            int commonDenominator = this.denominator * addend.denominator;
            return new Fraction(thisNumerator + addendNumerator, commonDenominator);
        }
    }

    public String toString(){

        int newNumerator = numerator % denominator;
        int wholeNumber =  numerator / denominator;

        if (newNumerator != 0){
            return getFirstLine(newNumerator, wholeNumber) +
                    getSecondLine(newNumerator, wholeNumber) +
                    getThirdLine(wholeNumber);
        }
        else
            return getSecondLine(newNumerator, wholeNumber);
    }

    private int addSpaces(int wholeNumber){

        int spaces = 0;
        if (wholeNumber != 0)
            spaces += String.valueOf(wholeNumber).length() + 1;
        return spaces;
    }

    private int addMoreSpaces(int newNumerator){

        int spaces = (String.valueOf(denominator).length() / 2) - (String.valueOf(newNumerator).length() / 2);

        if (String.valueOf(denominator).length() - String.valueOf(newNumerator).length() == 1)
            return 0;
        else if (String.valueOf(newNumerator).length() % 2 == 1 && String.valueOf(denominator).length() % 2 == 0)
            return --spaces;
        else
            return spaces;
    }

    private String getFirstLine(int newNumerator, int wholeNumber) {

        StringBuilder firstLine = new StringBuilder();
        int spaces = addSpaces(wholeNumber) + addMoreSpaces(newNumerator);

        for (int i = 0; i < spaces; i++)
            firstLine.append(" ");
        firstLine.append(newNumerator);
        firstLine.append("\n");

        return firstLine.toString();
    }

    private String getSecondLine(int newNumerator, int wholeNumber) {

        StringBuilder secondLine = new StringBuilder();

        if (wholeNumber != 0) {
            secondLine.append(wholeNumber);
            secondLine.append(" ");
        }
        if (newNumerator != 0){
            for (int i = 0; i < String.valueOf(denominator).length(); i++)
                secondLine.append("-");
        }
        secondLine.append("\n");
        return secondLine.toString();
    }

    private String getThirdLine(int wholeNumber) {

        StringBuilder thirdLine = new StringBuilder();

        for (int i = 0; i < addSpaces(wholeNumber); i++)
            thirdLine.append(" ");
        thirdLine.append(denominator);
        thirdLine.append("\n");
        return thirdLine.toString();
    }

    public Fraction reverseV2() throws IllegalStateException {

        if (numerator == 0)
            throw new IllegalStateException("Nie mogę odwrócić zera");
        return new Fraction(denominator, numerator);
    }

    public Fraction reverse() throws IllegalStateException {

        try {
            return new Fraction(denominator, numerator);
        }
        catch (IllegalArgumentException ex){
            throw new IllegalStateException("Nie mogę odwrócić zera, ", ex);
        }
    }

    public String toString(FormattingLanguage lang){

        int newNumerator = numerator % denominator;
        int wholeNumber =  numerator / denominator;
        String wholeNumberFormatted = new Formatter(wholeNumber).formatNumbers(lang);
        String newNumeratorFormatted = new Formatter(newNumerator).formatNumbers(lang);
        String denominatorFormatted = new Formatter(denominator).formatNumbers(lang);

        if (newNumerator != 0){
            return getFirstLineFormatted(newNumeratorFormatted, denominatorFormatted, wholeNumberFormatted) +
                    getSecondLineFormatted(newNumeratorFormatted, denominatorFormatted, wholeNumberFormatted) +
                    getThirdLineFormatted(newNumeratorFormatted, denominatorFormatted, wholeNumberFormatted);
        }
        else
            return getSecondLineFormatted(newNumeratorFormatted, denominatorFormatted, wholeNumberFormatted);
    }

    private int addFormattedSpaces(String wholeNumberFormatted){

        int spaces = 0;
        if (wholeNumberFormatted.length() != 0)
            spaces += wholeNumberFormatted.length() + 1;
        return spaces;
    }

    private int addSpacesForDenominator(String newNumeratorFormatted, String denominatorFormatted){

        int spacesBiggerDenominator = (denominatorFormatted.length() / 2) - (newNumeratorFormatted.length() / 2);
        int spacesBiggerNumerator = (newNumeratorFormatted.length() / 2) - (denominatorFormatted.length() / 2);

        return (denominatorFormatted.length() >= newNumeratorFormatted.length()) ? spacesBiggerDenominator : spacesBiggerNumerator;
    }

    private int addSpacesForNumerator(String newNumeratorFormatted, String denominatorFormatted){

        int spacesBiggerDenominator = (denominatorFormatted.length() / 2) - (newNumeratorFormatted.length() / 2);
        int spacesBiggerNumerator = (newNumeratorFormatted.length() / 2) - (denominatorFormatted.length() / 2);

        return (denominatorFormatted.length() <= newNumeratorFormatted.length()) ? spacesBiggerNumerator : spacesBiggerDenominator;
    }

    private String getFirstLineFormatted(String newNumeratorFormatted, String denominatorFormatted, String wholeNumberFormatted) {

        StringBuilder firstLine = new StringBuilder();
        int spaces = addFormattedSpaces(wholeNumberFormatted);

        if (newNumeratorFormatted.length() < denominatorFormatted.length())
            spaces+= addSpacesForNumerator(newNumeratorFormatted, denominatorFormatted);
        for (int i = 0; i < spaces; i++)
            firstLine.append(" ");
        firstLine.append(newNumeratorFormatted);
        firstLine.append("\n");

        return firstLine.toString();
    }

    private String getSecondLineFormatted(String newNumeratorFormatted, String denominatorFormatted, String wholeNumberFormatted) {

        StringBuilder secondLine = new StringBuilder();

        // if there is a whole number, append it
        if (wholeNumberFormatted.length() != 0) {
            secondLine.append(wholeNumberFormatted);
            secondLine.append(" ");
        }
        // if there is a fraction, append it
        if (newNumeratorFormatted.length() != 0){
            int result = (denominatorFormatted.length() >= newNumeratorFormatted.length()) ? denominatorFormatted.length() : newNumeratorFormatted.length();
            for (int i = 0; i < result; i++)
                secondLine.append("-");
        }
        secondLine.append("\n");

        return secondLine.toString();
    }

    private String getThirdLineFormatted(String newNumeratorFormatted, String denominatorFormatted, String wholeNumberFormatted) {

        StringBuilder thirdLine = new StringBuilder();
        int spaces = addFormattedSpaces(wholeNumberFormatted);

        if (newNumeratorFormatted.length() > denominatorFormatted.length())
            spaces+= addSpacesForDenominator(newNumeratorFormatted, denominatorFormatted);
        for (int i = 0; i < spaces; i++)
            thirdLine.append(" ");
        thirdLine.append(denominatorFormatted);
        thirdLine.append("\n");

        return thirdLine.toString();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Fraction)) return false;

        Fraction fraction = (Fraction) o;

        return (numerator == fraction.numerator) && (denominator == fraction.denominator);
    }

    @Override
    public int hashCode() {

        int result = numerator;
        result = 31 * result + denominator;
        return result;
    }
}
