package pl.com.bottega.photostock.sales.model.client_strategies;

import pl.com.bottega.photostock.sales.model.Money;

/**
 * Created by Beata Iłowiecka on 21.04.16.
 */
public interface ChargingStrategy {

    boolean canAfford(Money productCost);
    void charge(Money productCost, String cause);
    void recharge(Money amount);
    Money getSaldo();
}
