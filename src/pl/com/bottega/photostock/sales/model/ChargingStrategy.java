package pl.com.bottega.photostock.sales.model;

/**
 * Created by Beata Iłowiecka on 21.04.16.
 */
public interface ChargingStrategy {

    boolean canAfford(Money productCost);
    void charge(Money productCost, String cause);
    void recharge(Money amount);
    Money getSaldo();
}
