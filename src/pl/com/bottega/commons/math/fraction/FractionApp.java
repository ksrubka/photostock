package pl.com.bottega.commons.math.fraction;

import pl.com.bottega.commons.math.formatter.FormattingLanguage;

/**
 * Created by Beata Iłowiecka on 19.03.2016.
 */
public class FractionApp {

    public static void main(String[] args) {

        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);

        Fraction f1 = new Fraction(a, b);
        Fraction f2 = new Fraction(1000, 12345678);

        System.out.println("\n\n" + f1.toString());

        System.out.println("***********************  L I C Z N I K   >   M I N A O W N I K  ***********************\n");
        System.out.println(f1.toString(FormattingLanguage.PL));
        System.out.println(f1.toString(FormattingLanguage.ENG)+ "\n\n");

        System.out.println("***********************  L I C Z N I K   <   M I N A O W N I K  ***********************\n");
        System.out.println(f2.toString(FormattingLanguage.PL));
        System.out.println(f2.toString(FormattingLanguage.ENG) + "\n");

        /*try {
            Fraction fReversed = f1.reverse();
            System.out.println(fReversed);
        }
        catch (IllegalStateException  ex) { // przepakowanie błędu
            System.out.println("Nie mogę odwócić bo " + ex.getMessage());
        }*/
    }
}
