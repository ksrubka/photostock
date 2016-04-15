package pl.com.bottega.photostock.sales.model;

import pl.com.bottega.commons.math.Fraction;

import java.util.Currency;
import java.util.Date;

/**
 * Created by Beata Iłowiecka on 12.03.2016.
 */
public class Money {

    private final Fraction value = null;
    private final Currency currency = null;

    public Money(Double value, String currency) {
        //TODO
    }

    public Money(int integerValue, int cents, String currency) {
        //TODO

    }

    public Money(double value) {
        this(value, "PLN");
    }

    public Money add(Money amount){
        return null;//TODO
    }

    public Money substract(Money amount){
        return null;//TODO
    }

    public Money multiple(int ratio){
        return null;//TODO
    }

    public Money multiple(double ratio){
        return null;//TODO
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Money money = (Money) o;

        if (!value.equals(money.value)) return false;
        return currency.equals(money.currency);
    }

    @Override
    public int hashCode() {
        int result = value.hashCode();
        result = 31 * result + currency.hashCode();
        return result;
    }
}
