package pl.com.bottega.photostock.sales.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Beata Iłowiecka on 06.06.16.
 */
public class MoneyTest {

    private Money money5 = new Money(5);
    private Money money20 = new Money(20);
    private Money money50 = new Money(50);
    private Money money100 = new Money(100);

    private Money money5EUR = new Money(5, "EUR");
    private Money money20EUR = new Money(20, "EUR");

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
    public void shouldHave0CentsFromInt() {
        Money testMoney = new Money(0, 0);
        assertEquals(0, testMoney.cents());
    }

    @Test
    public void shouldHave50CentsFromInt() {
        Money testMoney = new Money(0, 50);
        assertEquals(50, testMoney.cents());
    }

    @Test
    public void shouldHave500Cents() {
        int cents = money5.cents();
        assertEquals(500, cents);
    }

    @Test
    public void shouldHave650CentsFromDouble() {
        Money testMoney = new Money(6.50);
        assertEquals(650, testMoney.cents());
    }

    @Test
    public void shouldHave50CentsFromDouble() {
        Money testMoney = new Money(0.50);
        assertEquals(50, testMoney.cents());
    }

    @Test
    public void shouldHave0CentsFromDouble() {
        Money testMoney = new Money(0.0);
        assertEquals(0, testMoney.cents());
    }

    @Test
    public void shouldBeEqualWithDecimals() {
        Money testMoney = new Money(5, 30);
        Money testDoubleMoney = new Money(5.3);
        assertEquals(testMoney, testDoubleMoney);
    }

    @Test
    public void shouldBeEqualWithDecimalsWithoutInteger() {
        Money testMoney = new Money(0, 60);
        Money testDoubleMoney = new Money(0.60);
        assertEquals(testMoney, testDoubleMoney);
    }

    @Test
    public void shouldNotBeEqualWithDecimalsWithoutInteger() {
        Money testMoney = new Money(0, 70);
        Money testDoubleMoney = new Money(0.60);
        assertNotEquals(testMoney, testDoubleMoney);
    }

    @Test
    public void shouldAddMoney() {
        Money testMoney = money5.add(money20);
        assertEquals(new Money(25), testMoney);
    }

    @Test
    public void shouldAddMoreMoney() {
        Money testMoney = money5.add(money20).add(money50);
        assertEquals(new Money(75), testMoney);
    }

    @Test
    public void shouldSubtractMoney() {
        Money testMoney = money100.subtract(money20);
        assertEquals(new Money(80), testMoney);
    }

    @Test
    public void shouldSubtractMoreMoney() {
        Money testMoney = money100.subtract(money20).subtract(money5);
        assertEquals(new Money(75), testMoney);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAddMoneyDifferentCurrency() {
        Money testMoney = money5.add(money20EUR);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateMoneyWithMoreThan2Decimals() {
        Money testMoney = new Money(0, 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCreateMoneyWithMoreThan2DecimalsFromDouble() {
        Money testMoney = new Money(6.765);
    }

    @Test
    public void shouldAdd70Cents() {
        Money testMoney = new Money(6.700);
        assertEquals(670, testMoney.cents());
    }
}