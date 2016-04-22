package pl.com.bottega.photostock.sales.model.client_strategies;

import pl.com.bottega.photostock.sales.model.Money;

/**
 * Created by Beata Iłowiecka on 21.04.16.
 */
public interface ChargingStrategy {

    boolean canAfford(Money productCost, ChargingData chargingData);
    void charge(Money productCost, String cause, ChargingData chargingData);
    void recharge(Money amount, ChargingData chargingData);
    Money getSaldo(ChargingData chargingData);
}
