package pl.com.bottega.photostock.sales.model.client_strategies;

import pl.com.bottega.photostock.sales.model.Money;
import pl.com.bottega.photostock.sales.model.client_strategies.ChargingStrategy;

/**
 * Created by Beata Iłowiecka on 21.04.16.
 */
public class StandardChargingStrategy implements ChargingStrategy {

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



    /*public boolean canAfford(double money) {
        return  amount >= money;
    }

    public void charge(double pictureCost, String cause){ //TODO what to do with 'cause'?

        if (canAfford(pictureCost)) {
            amount -= pictureCost;
        }
        else {
            throw new IllegalStateException("Sorry, you can not afford this product.");
        }
    }

    public void recharge(double amount){
        this.amount += amount;
    }

    public double getSaldo(){
        return amount;
    }*/
}
