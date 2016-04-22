package pl.com.bottega.photostock.sales.model.client_strategies;

import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.Money;

/**
 * Created by Beata Iłowiecka on 22.04.16.
 */
public class ChargingData {

    Money amount;
    Money debt;
    Money creditLimit;

    public ChargingData(Client client){
        this.amount = client.getAmount();
        this.debt = client.getDebt();
        this.creditLimit = client.getCreditLimit();
    }
}
