package pl.com.bottega.commons.math.Probability;

/**
 * Created by Beata Iłowiecka on 18.04.16.
 */
public class Probability {

      /*public enum EventType {
        FRACTION, PERCENT;
    }*/

    private double value; // wartość procentowa

    private Probability(double value){
        this.value = value;
    }

    public Probability and(Probability v2){
        return new Probability(value * v2.value);
    }

    @Override
    public String toString(){
        return Double.toString(value);
    }

    public static Probability fromPercentage(double value) {
        if (value < 0 || value > 100)
            throw new IllegalArgumentException("value must be <0, 100>");
        return new Probability(value);
    }

    public static Probability fromFraction(double value) {
        if (value < 0 || value > 1)
            throw new IllegalArgumentException("value must be <0,1>");
        return new Probability(value);
    }

    public boolean equals(Object p2) {
        if (this == p2) return true;
        if (p2 == null || getClass() != p2.getClass()) return false;

        Probability probability2 = (Probability) p2;
        double delta = 0.00001;

        return (Math.abs(value - probability2.value) < delta);
    }

   /* @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(value);
        return (int) (temp ^ (temp >>> 32));
    }*/
}
