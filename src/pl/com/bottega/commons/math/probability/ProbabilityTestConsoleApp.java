package pl.com.bottega.commons.math.probability;

import pl.com.bottega.commons.math.probability.Probability;

import static pl.com.bottega.commons.math.probability.Probability.fromFraction;
import static pl.com.bottega.commons.math.probability.Probability.fromPercentage;

/**
 * Created by Beata Iłowiecka on 18.04.16.
 */
public class ProbabilityTestConsoleApp {

    public static void main(String[] args){
        shouldCreateFractionRepresentation();
        canNotCreateFractionRepresentationIfValueGTOne();

        shouldCreatePercentageRepresentation();
        canNotCreatePercentageRepresentation();

        shouldCalculateIfDifferentRepresentation();
        shouldEqualZeroIfOneIsZero();
    }

    private static void shouldEqualZeroIfOneIsZero() {

    }

    private static void shouldCalculateIfDifferentRepresentation() {
        Probability bothEvents = fromFraction(0.5).and(fromPercentage(50));

        System.out.println("wynik: " + bothEvents);
    }

    private static void canNotCreatePercentageRepresentation() {
        try {
            Probability p = fromPercentage(190.5);
            throw new RuntimeException("Powinien być wyjątek");
        } catch (IllegalArgumentException ex){
        }
    }

    private static void shouldCreatePercentageRepresentation() {
        try {
            Probability p = fromPercentage(190.5);
            throw new RuntimeException("Powinien być wyjątek");
        } catch (IllegalArgumentException ex) {

        }
    }

    private static void canNotCreateFractionRepresentationIfValueGTOne() {
        try {
            Probability p = fromFraction(1.5);
            throw new RuntimeException("Powinien być wyjątek");
        } catch (IllegalArgumentException ex) {
        }
    }

    private static void shouldCreateFractionRepresentation() {
        Probability p = fromFraction(0.5);
    }
}
