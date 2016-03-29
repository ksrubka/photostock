package pl.com.bottega.commons.math;

/**
 * Created by Beata Iłowiecka on 19.03.2016.
 */
public class FractionApp {

    public static void main(String[] args) {

        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);

        Fraction f1 = new Fraction(a, b);

        try {
            Fraction fReversed = f1.reverse();
            System.out.println(fReversed);
        }
        catch (IllegalStateException  ex) { // przepakowanie błędu
            System.out.println("Nie mogę odwócić bo " + ex.getMessage());
    }
    }
}
