package pl.com.bottega.commons.math;

import pl.com.bottega.commons.math.formatter.Formatter;
import pl.com.bottega.commons.math.formatter.FormattingLanguage;

/**
 * Created by Beata Iłowiecka on 19.03.2016.
 */
public class Fraction {

    private final int nominator;
    private final int denominator;

    public static final Fraction ONE = new Fraction(1, 1);
    public static final Fraction ZERO = new Fraction(0, 1);

    /**
     * Klasa modeluje ułamek
     *
     * @param nominator
     * @param denominator
     *
     * @throws  IllegalArgumentException gdy mianownik jest równy 0
     */
    public Fraction(int nominator, int denominator) throws IllegalArgumentException {
        if (denominator == 0)
            throw new IllegalArgumentException("Denominator can not be zero");

        this.nominator = nominator;
        this.denominator = denominator;
    }

    public Fraction(int nominator) {
        this.nominator = nominator;
        this.denominator = 10;
        // nie chcemy walidować sprawdzać poprawnosć liczby 10
        //this(nominator, 10);
    }

    /**
     *
     * @param literal ułamek w reprezentaacji licznik/mianownik, np. 3/4
     */
    public Fraction(String literal) throws IllegalArgumentException {
        String[] words = literal.split("/");

        if (words.length != 2){
            throw new IllegalArgumentException("To nie jest ułamek");
        }

//      this(Integer.parseInt(words[0]), Integer.parseInt(words[1]));

        try {
            nominator = Integer.parseInt(words[0]);
            denominator = Integer.parseInt(words[1]);

            if (denominator == 0)
                throw new IllegalArgumentException("Denominator can not be zero");
        }
        catch (NumberFormatException ex){
            throw new IllegalArgumentException("To nie jest ułamek" + ex);
        }
    }

    public Fraction add(Fraction addend){

        if (denominator == addend.denominator){
            int sumOfNominators = nominator + addend.nominator;
            Fraction sum = new Fraction(sumOfNominators, denominator);
            return sum;
        } else {
            int thisNominator = nominator * addend.denominator;
            int addendNominator = denominator * addend.nominator;
            int commonDenominator = this.denominator * addend.denominator;
            return new Fraction(thisNominator + addendNominator, commonDenominator);
        }
    }

    public String toString(){

        int newNominator = nominator % denominator;
        int wholeNumber =  nominator / denominator;

        if (newNominator != 0){
            return getFirstLine(newNominator, wholeNumber) +
                    getSecondLine(newNominator, wholeNumber) +
                    getThirdLine(wholeNumber);
        }
        else {
            return getSecondLine(newNominator, wholeNumber);
        }
    }

    private int addSpaces(int wholeNumber){

        int spaces = 0;

        if (wholeNumber != 0){
            spaces += String.valueOf(wholeNumber).length() + 1;
        }
        return spaces;
    }

    private int addMoreSpaces(int newNominator){

        int spaces = (String.valueOf(denominator).length() / 2) - (String.valueOf(newNominator).length() / 2);

        if (String.valueOf(denominator).length() - String.valueOf(newNominator).length() == 1){
            return 0;
        }
        else if (String.valueOf(newNominator).length() % 2 == 1 && String.valueOf(denominator).length() % 2 == 0){
            return --spaces;
        }
        else {
            return spaces;
        }
    }

    private String getFirstLine(int newNominator, int wholeNumber) {

        StringBuilder firstLine = new StringBuilder();
        int spaces = addSpaces(wholeNumber) + addMoreSpaces(newNominator);

        for (int i = 0; i < spaces; i++){
            firstLine.append(" ");
        }
        firstLine.append(newNominator);
        firstLine.append("\n");

        return firstLine.toString();
    }

    private String getSecondLine(int newNominator, int wholeNumber) {

        StringBuilder secondLine = new StringBuilder();

        if (wholeNumber != 0) {
            secondLine.append(wholeNumber);
            secondLine.append(" ");
        }

        if (newNominator != 0){
            for (int i = 0; i < String.valueOf(denominator).length(); i++) {
                secondLine.append("-");
            }
        }
        secondLine.append("\n");

        return secondLine.toString();
    }

    private String getThirdLine(int wholeNumber) {

        StringBuilder thirdLine = new StringBuilder();

        for (int i = 0; i < addSpaces(wholeNumber); i++){
            thirdLine.append(" ");
        }
        thirdLine.append(denominator);
        thirdLine.append("\n");

        return thirdLine.toString();
    }

    public Fraction reverseV2()throws IllegalArgumentException {
        if (nominator == 0){
            throw new IllegalStateException("Can not reverse zero");
        }
        return new Fraction(denominator, nominator);
    }


    public Fraction reverse() throws IllegalStateException {
        try {
            return new Fraction(denominator, nominator);
        }
        catch (IllegalArgumentException ex){
            throw new IllegalStateException("Zero can not be reversed", ex);
        }
    }









    public String toString(FormattingLanguage lang){
        int newNominator = nominator % denominator;
        int wholeNumber =  nominator / denominator;

        Formatter wholeNumberFormatter = new Formatter(wholeNumber);
        String wholeNumberFormatted = wholeNumberFormatter.formatNumbers(lang);

        Formatter newNominatorFormatter = new Formatter(newNominator);
        String newNominatorFormatted = newNominatorFormatter.formatNumbers(lang);

        Formatter denominatorFormatter = new Formatter(denominator);
        String denominatorFormatted = denominatorFormatter.formatNumbers(lang);

        if (newNominator != 0){
            return getFirstLineFormatted(newNominatorFormatted, denominatorFormatted, wholeNumberFormatted, lang) +
                    getSecondLineFormatted(newNominatorFormatted, denominatorFormatted, wholeNumberFormatted, lang) +
                    getThirdLineFormatted(wholeNumberFormatted, denominatorFormatted, lang);
        }
        else {
            return getSecondLineFormatted(newNominatorFormatted, denominatorFormatted, wholeNumberFormatted, lang);
        }
    }

    private int addFormattedSpaces(String wholeNumberFormatted, FormattingLanguage lang){

        int spaces = 0;

        if (wholeNumberFormatted.length() != 0){
            spaces += wholeNumberFormatted.length() + 1;
        }
        return spaces;
    }

    private int addMoreFormattedSpaces(String newNominatorFormatted, String denominatorFormatted, FormattingLanguage lang){


        int spaces = (denominatorFormatted.length() / 2) - (newNominatorFormatted.length() / 2);

        if (denominatorFormatted.length() - newNominatorFormatted.length() == 1){
            return 0;
        }
        else if (newNominatorFormatted.length() % 2 == 1 && denominatorFormatted.length() % 2 == 0){
            return --spaces;
        }
        else {
            return spaces;
        }
    }

    private String getFirstLineFormatted(String newNominatorFormatted, String denominatorFormatted, String wholeNumberFormatted, FormattingLanguage lang) {

        StringBuilder firstLine = new StringBuilder();
        int spaces = addFormattedSpaces(wholeNumberFormatted, lang) + addMoreFormattedSpaces(newNominatorFormatted, denominatorFormatted, lang);

        for (int i = 0; i < spaces; i++){
            firstLine.append(" ");
        }
        firstLine.append(newNominatorFormatted);
        firstLine.append("\n");

        return firstLine.toString();
    }

    private String getSecondLineFormatted(String newNominatorFormatted, String denominatorFormatted, String wholeNumberFormatted, FormattingLanguage lang) {

        StringBuilder secondLine = new StringBuilder();

        if (wholeNumberFormatted.length() != 0) {
            secondLine.append(wholeNumberFormatted);
            secondLine.append(" ");
        }

        if (newNominatorFormatted.length() != 0){
            for (int i = 0; i < denominatorFormatted.length(); i++) {
                secondLine.append("-");
            }
        }
        secondLine.append("\n");

        return secondLine.toString();
    }

    private String getThirdLineFormatted(String wholeNumberFormatted, String denominatorFormatted, FormattingLanguage lang) {

        StringBuilder thirdLine = new StringBuilder();

        for (int i = 0; i < addFormattedSpaces(wholeNumberFormatted, lang); i++){
            thirdLine.append(" ");
        }
        thirdLine.append(denominatorFormatted);
        thirdLine.append("\n");

        return thirdLine.toString();
    }
}
