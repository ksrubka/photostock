package pl.com.bottega.photostock.sales.model;

import pl.com.bottega.commons.math.Fraction;

import java.util.Currency;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Beata Iłowiecka on 12.03.2016.
 */
public class Money {

    private final Fraction value;
    private final Currency currency;

    public Money(Double value, String currency) {
        this.value = new Fraction(getNumerator(value), 100);
        this.currency = Currency.getInstance(currency);
    }

    public Money(int integerValue, int cents, String currency) {
        this.value = new Fraction(getNumerator(integerValue, cents), 100);
        this.currency = Currency.getInstance(currency);
    }

    public Money(double value) {
        this(value, "PLN");
    }

    private int getNumerator(Double value){
        return (int) ((Math.floor(value)) + (value - Math.floor(value)) * 100);
    }
    private int getNumerator(int value, int cents){
        return (value * 100) + cents;
    }

    public Money add(Money amount){
        if (!(currency == amount.currency))
            throw new IllegalArgumentException("Can not add if different currency");
        return new Money((double) value.getNumerator() + amount.value.getNumerator(), currency.getCurrencyCode());
    }

    public Money substract(Money amount){
        if (!currency.equals(amount.currency))
            throw new IllegalArgumentException("Can not add if different currency");
        return new Money((double) value.getNumerator() - amount.value.getNumerator(), currency.getCurrencyCode());
    }

    public Money multiple(int ratio){
        return null;//TODO
    }

    public Money multiple(double ratio){
        return null;//TODO
    }

    public boolean equals(Object m2) {
        if (this == m2) return true;
        if (m2 == null || getClass() != m2.getClass()) return false;

        Money money2 = (Money) m2;
        int v1 = value.getNumerator();
        int v2 = money2.value.getNumerator();

        if (this.currency.equals(money2.currency)){
            int smaller = (v1 < v2) ? v1 : v2;
            double delta = (smaller > 100) ? 0.001 : 0.01;

            return  (Math.abs(v1 - v2) < delta);
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
        return this.value.getNumerator() >= val.value.getNumerator();
    }

    /**
     *
     * @param val
     * @return true if this is less or equals than val
     */
    public boolean le(Money val) {
        return this.value.getNumerator() <= val.value.getNumerator();
    }

    /**
     *
     * @param val
     * @return  true if this is less than val
     */
    public boolean lt(Money val) {
        return this.value.getNumerator() < val.value.getNumerator();
    }

    /**
     *
     * @param val
     * @return  true if this is greater than val
     */
    public boolean gt(Money val) {
        return this.value.getNumerator() > val.value.getNumerator();
    }

    public Money getZero(){
        return new Money(0d, currency.getCurrencyCode());
    }


}
