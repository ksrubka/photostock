package pl.com.bottega.photostock.sales.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Beata Iłowiecka on 06.06.16.
 */
public class MoneyTest {

    private Money money5;
    private Money money20;
    private Money money50;
    private Money money100;

    private Money money5EUR;
    private Money money20EUR;
    private Money money50EUR;
    private Money money100EUR;


    @Before
    public void initMoney() {
        money5 = new Money(5);
        money20 = new Money(20);
        money50 = new Money(50);
        money100 = new Money(100);

        money5EUR = new Money(5, "EUR");
        money20EUR = new Money(20, "EUR");
        money50EUR = new Money(50, "EUR");
        money100EUR = new Money(100, "EUR");
    }

    @Test
    public void shouldNotBeEqual() {
        Money testMoney = new Money(5, 1);
        assertNotEquals(testMoney, money5);
    }
    @Test
    public void shouldNotBeEqualWithDifferentCurrency() {
        assertNotEquals(money5, money5EUR);
    }

    @Test
    public void shouldBeEqual() {
        Money testMoney = new Money(5, 0);
        assertEquals(testMoney, money5);
    }

    @Test
    public void shouldHave500Cents() {
        int cents = money5.cents();
        assertEquals(500, cents);
    }

    @Test
    public void shouldHave650CentsFromInt() {
        Money testMoney = new Money(5, 150);
        assertEquals(650, testMoney.cents());
    }

    @Test
    public void shouldHaveSameNumerators() {
        Money testMoney = new Money(5, 0);
    }
}
