package pl.com.bottega.photostock.sales.model.client_strategies.charging;

import pl.com.bottega.photostock.sales.model.Money;
import pl.com.bottega.photostock.sales.model.client_strategies.charging.Charging;

/**
 * Created by Beata Iłowiecka on 21.04.16.
 */
public interface ChargingStrategy {

    boolean canAfford(Charging charging, Money productCost);
    void charge(Charging charging, Money productCost, String cause);
    void recharge(Charging charging, Money amount);
    Money getSaldo(Charging charging);
}
