package pl.com.bottega.photostock.sales.model.client_strategies;

import pl.com.bottega.photostock.sales.model.Money;
import pl.com.bottega.photostock.sales.model.client_strategies.ChargingStrategy;

/**
 * Created by Beata Iłowiecka on 21.04.16.
 */
public class VIPChargingStrategy implements ChargingStrategy {

    @Override
    public boolean canAfford(Money productCost) {
        return false;
    }

    @Override
    public void charge(Money productCost, String cause) {

    }

    @Override
    public void recharge(Money amount) {

    }

    @Override
    public Money getSaldo() {
        return null;
    }

    /*@Override
    public boolean canAfford(double money) {

        double purchasePotential = amount + creditLimit;
        return  purchasePotential >= money;
    }

    @Override
    public void charge(double pictureCost, String cause){ //TODO what to do with 'cause'?

        if (canAfford(pictureCost)){
            if (amount >= pictureCost){
                amount -= pictureCost;
            } else {
                if ((pictureCost - amount) <= creditLimit){
                    debt = pictureCost - amount;
                    amount = 0;
                } else return;
            }
        }
    }

    @Override
    public void recharge(double amount){
        if (this.amount >= 0){
            this.amount += amount;
        }
        else {
            if (debt >= amount) {
                debt -= amount;
            } else {
                this.amount += amount - debt;
                debt = 0;
            }
        }
    }

    @Override
    public double getSaldo(){
        if (amount >= 0){
            return amount;
        }
        else {
            return -debt;
        }
    }*/
}
