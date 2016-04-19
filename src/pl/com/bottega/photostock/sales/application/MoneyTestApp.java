package pl.com.bottega.photostock.sales.application;

import pl.com.bottega.photostock.sales.model.Money;

/**
 * Created by Beata Iłowiecka on 19.04.16.
 */
public class MoneyTestApp {

    public static void main(String[] args){

        Money m1 = new Money(80, 20, "PLN");
        Money m2 = new Money(80, 19, "PLN");
        Money m3 = new Money(80, 20, "PLN");

        Money m4 = new Money(800, 20, "PLN");
        Money m5 = new Money(800, 19, "PLN");
        Money m6 = new Money(800, 20, "PLN");


        System.out.println("Money.value  < 100");
        System.out.println(m1.equals(m2));
        System.out.println(m1.equals(m3));

        System.out.println("Money.value > 100");
        System.out.println(m4.equals(m5));
        System.out.println(m4.equals(m6));
    }
}
