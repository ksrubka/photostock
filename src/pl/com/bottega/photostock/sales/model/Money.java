package pl.com.bottega.photostock.sales.model;

import pl.com.bottega.commons.math.fraction.Fraction;

import java.util.Currency;

/**
 * Created by Beata Iłowiecka on 12.03.2016.
 */
public class Money {

    private final Fraction value;
    private final Currency currency;

    public Money(double value, String currency) {
        int fractionNumerator = getNumerator(value);
        this.value = new Fraction(fractionNumerator, 100);
        this.currency = Currency.getInstance(currency);
    }

    public Money(int value, int cents, String currency) {
        int fractionNumerator = getNumerator(value, cents);
        this.value = new Fraction(fractionNumerator, 100);
        this.currency = Currency.getInstance(currency);
    }

    public Money(double value) {
        this(value, "PLN");
    }

    public Money(int value, int cents) {
        this(value, cents, "PLN");
    }

    public int getNumerator(Double value) {

        return (int) ((Math.floor(value) * 100) + (value - Math.floor(value)));
    }

    public int getNumerator(int value, int cents) {
        return (value * 100) + cents;
    }

    public Money add(Money amount){
        if (!(currency == amount.currency))
            throw new IllegalArgumentException("Can not add if different currency");
        return new Money((double) value.getNumerator() + amount.value.getNumerator(), currency.getCurrencyCode());
    }

    public Money subtract(Money amount){
        if (!currency.equals(amount.currency))
            throw new IllegalArgumentException("Can subtract add if different currency");
        return new Money((double) value.getNumerator() - amount.value.getNumerator(), currency.getCurrencyCode());
    }

    public Money multiple(int ratio){
        return new Money((double) (value.getNumerator() * ratio), currency.getCurrencyCode());
    }

    public Money multiple(double ratio){
        return new Money((value.getNumerator() * ratio), currency.getCurrencyCode());
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

    public boolean greaterOrEqual(Money val) {
        return this.value.getNumerator() >= val.value.getNumerator();
    }

    public boolean lowerOrEqual(Money val) {
        return this.value.getNumerator() <= val.value.getNumerator();
    }

    public boolean lowerThan(Money val) {
        return this.value.getNumerator() < val.value.getNumerator();
    }

    public boolean greaterThan(Money val) {
        return this.value.getNumerator() > val.value.getNumerator();
    }

    public Money getZero(){
        return new Money(0d, currency.getCurrencyCode());
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getDoubleValue() {
        return value.getNumerator()/100;
    }

    public int cents() {
        return value.getNumerator();
    }
}