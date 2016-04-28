package pl.com.bottega.photostock.sales.model.client_strategies;

import pl.com.bottega.photostock.sales.model.Money;

/**
 * Created by Beata Iłowiecka on 21.04.16.
 */
public class VIPChargingStrategy implements ChargingStrategy {

    Money debt;
    Money creditLimit;

    public VIPChargingStrategy(){
        this.debt = new Money(0);
        this.creditLimit = new Money(200); //todo do ustalenia czy każdy vip ma tyle sam czy może każdy inaczej
    }

    @Override
    public boolean canAfford(Charging charging, Money newAmount) {
        Money purchasePotential = charging.getAmount().add(creditLimit);
        return  purchasePotential.greaterOrEqual(newAmount);
    }

    @Override
    public void charge(Charging charging, Money productCost, String cause){ //TODO what to do with 'cause'?
        if (canAfford(charging, productCost)){
            if (charging.getAmount().greaterOrEqual(productCost)){
                charging.setAmount(charging.getAmount().substract(productCost)); // wyrażenie po znaku '=' nie odejmuje od amount ale zwraca nowy obiekt
            } else {
                if ((productCost.substract(charging.getAmount())).lowerOrEqual(creditLimit)){
                    debt = productCost.substract(charging.getAmount());
                    charging.setAmount(charging.getAmount().getZero());
                }
            }
        }
        //todo rzuć wyjątkiem
    }

    @Override
    public void recharge(Charging charging, Money newAmount){
        if (charging.getAmount().greaterOrEqual(newAmount.getZero())){
            charging.setAmount(charging.getAmount().add(newAmount));
        }
        else {
            if (debt.greaterOrEqual(newAmount)) {
                debt = debt.substract(newAmount);
            } else {
                charging.setAmount(charging.getAmount().add(newAmount.substract(debt)));
                debt = debt.getZero();
            }
        }
    }

    @Override
    public Money getSaldo(Charging charging){
        if (charging.getAmount().greaterOrEqual(charging.getAmount().getZero())){
            return charging.getAmount();
        }
        else {
            return new Money(-(debt.getDoubleValue()));
        }
    }
}