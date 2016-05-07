package pl.com.bottega.photostock.sales.model.client_strategies.charging;

import pl.com.bottega.photostock.sales.model.Money;

/**
 * Created by Beata Iłowiecka on 21.04.16.
 */
public class StandardChargingStrategy implements ChargingStrategy {

    @Override
    public boolean canAfford(Charging charging, Money productCost) {
        return charging.getAmount().greaterOrEqual(productCost);
    }

    @Override
    public void charge(Charging charging, Money productCost, String cause) {
        if (canAfford(charging, productCost)){
            Money newAmount = charging.getAmount().substract(productCost);
            charging.setAmount(newAmount);
        }
        else {
            throw new IllegalStateException("Przepraszamy, nie posiadasz wystarczającej kwoty by nabyć ten produkt.");
        }
    }

    @Override
    public void recharge(Charging charging, Money amount) {
        Money newAmount = charging.getAmount().add(amount);
        charging.setAmount(newAmount);
    }

    @Override
    public Money getSaldo(Charging charging) {
        return charging.getAmount();
    }

    @Override
    public void setCreditLimit(Money creditLimit) { // bez sensu
        return;
    }

    @Override
    public Money getDebt() {
        return null;
    }

    @Override
    public void setDebt(double amount) {
        return;
    }
}
