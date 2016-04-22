package pl.com.bottega.photostock.sales.model.client_strategies;

import pl.com.bottega.commons.math.Fraction;
import pl.com.bottega.photostock.sales.model.Money;
import pl.com.bottega.photostock.sales.model.client_strategies.ChargingStrategy;

/**
 * Created by Beata Iłowiecka on 21.04.16.
 */
public class VIPChargingStrategy implements ChargingStrategy {

    private Money debt;
    private Money creditLimit;
    private Money amount;

    @Override
    public boolean canAfford(Money money) {
        Money purchasePotential = amount.add(creditLimit);
        return  purchasePotential.greaterOrEqual(money);
    }

    @Override
    public void charge(Money productCost, String cause){ //TODO what to do with 'cause'?
        if (canAfford(productCost)){
            if (amount.greaterOrEqual(productCost)){
                amount = amount.substract(productCost); // wyrażenie po znaku '=' nie odejmuje od amount ale zwraca nowy obiekt
            } else {
                if ((productCost.substract(amount)).lowerOrEqual(creditLimit)){
                    debt = productCost.substract(amount);
                    amount = amount.getZero();
                }
            }
        }
    }

    @Override
    public void recharge(Money amount){
        if (this.amount.greaterOrEqual(amount.getZero())){
            this.amount = this.amount.add(amount);
        }
        else {
            if (debt.greaterOrEqual(amount)) {
                debt = debt.substract(amount);
            } else {
                this.amount = this.amount.add(amount.substract(debt));
                debt = debt.getZero();
            }
        }
    }

    @Override
    public Money getSaldo(){
        if (amount.greaterOrEqual(amount.getZero())){
            return amount;
        }
        else {
            return new Money(-(debt.getValue().getNumerator()));
        }
    }
}
