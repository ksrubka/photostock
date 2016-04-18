package pl.com.bottega.photostock.sales.model;

import pl.com.bottega.commons.math.Fraction;

import java.util.Currency;
import java.util.Date;

/**
 * Created by Beata Iłowiecka on 12.03.2016.
 */
public class Money {

    private final Double value;//TODO zamienić na Fraction
    private final String currency;//TODO zamienić na użycie klasy Currency z biblioteki Javy

    public Money(Double value, String currency) {
        this.value = value;
        this.currency = currency;
    }

    public Money(int integerValue, int cents, String currency) {
        this(integerValue + (double)cents/100, currency);
    }

    public Money(double value) {
        this(value, "PLN");
    }

    public Money add(Money amount){
        if (!currency.equals(amount.currency))
            throw new IllegalArgumentException("Can not add if different currency");
        return new Money(value + amount.value, currency);
    }

    public Money substract(Money amount){
        if (!currency.equals(amount.currency))
            throw new IllegalArgumentException("Can not add if different currency");
        return new Money(value - amount.value, currency);
    }

    public Money multiple(int ratio){
        return null;//TODO
    }

    public Money multiple(double ratio){
        return null;//TODO
    }

    public boolean equals(Money m2) {
        /*if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Money money = (Money) o;

        if (!value.equals(money.value)) return false;
        return currency.equals(money.currency);*/
// ===============================================================
        if (this == m2) return true;
        else {
            if (this.currency == m2.currency){
                double delta;
                double smallerAmount;
                double biggerAmount;

                if (this.value - m2.value < 0){
                    smallerAmount = this.value;
                    biggerAmount = m2.value;
                }
                else {
                    smallerAmount = m2.value;
                    biggerAmount = this.value;
                }
                delta = (smallerAmount > 100) ? 0.001 : 0.01;
                return  (biggerAmount - smallerAmount < delta);
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = value.hashCode();
        result = 31 * result + currency.hashCode();
        return result;
    }

    /**
     *
     * @param val
     * @return true if this is grater or equals than val
     */
    public boolean ge(Money val) {
        return this.value >= val.value;
    }

    /**
     *
     * @param val
     * @return true if this is less or equals than val
     */
    public boolean le(Money val) {
        return this.value <= val.value;
    }

    /**
     *
     * @param val
     * @return  true if this is less than val
     */
    public boolean lt(Money val) {
        return this.value < val.value;
    }

    /**
     *
     * @param val
     * @return  true if this is greater than val
     */
    public boolean gt(Money val) {
        return this.value > val.value;
    }

    public Money getZero(){
        return new Money(0d, currency);
    }


}
