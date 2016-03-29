package pl.com.bottega.commons.math;

import static pl.com.bottega.commons.math.Fraction.ONE;
import static pl.com.bottega.commons.math.Fraction.ZERO;

/**
 * Created by Beata Iłowiecka on 19.03.2016.
 */
public class FractionTestConsoleApp {

    public static void main(String[] args) {

        Fraction f1;
        Fraction f2 = new Fraction(2);

        try {
            f1 = new Fraction(0, 3);
        }
        catch(IllegalArgumentException ex) {
            System.out.println("Złe dane wejściowe! " + ex.getMessage());
            return;
        }

        //Fraction onePrim = new Fraction(1, 1); bez sensu bo tworzymy dwa takie same obiekty i tak niemodyfikowalne
        //Fraction sum = f1.add(f2).add(ONE).add(Fraction.ZERO); // SEXY FLUENT INTERFACE

        Fraction sum = f1.add(f2);
        System.out.println(sum.toString());


        /*int f1nominator = 1;
        int f1denominator = 10;

        int f2nominator = 2;
        int f2denominator = 10;

        double f1 = (double) f1nominator / f1denominator;
        double f2 = (double) f2nominator / f2denominator;

        double sum = f1 + f2;
        System.out.println(sum);*/

    }
}
