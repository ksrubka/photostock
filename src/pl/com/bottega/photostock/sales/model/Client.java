package pl.com.bottega.photostock.sales.model;

import pl.com.bottega.photostock.sales.model.client_strategies.ChargingData;
import pl.com.bottega.photostock.sales.model.client_strategies.ChargingStrategy;
import pl.com.bottega.photostock.sales.model.client_strategies.StandardChargingStrategy;
import pl.com.bottega.photostock.sales.model.client_strategies.VIPChargingStrategy;

import java.sql.ClientInfoStatus;

/**
 * Created by Beata Iłowiecka on 12.03.2016.
 */
public class Client {

    private String name;
    private String address;
    private ChargingStrategy chargingStrategy;
    private ClientStatus status;
    private Money amount;
    private boolean active;


    public Client(String name, String address, ClientStatus status, double amount, boolean active) {
        this.name = name;
        this.address = address;
        this.status = status;
        this.chargingStrategy = ;//todo chargingStrategyFactory
                //(status == ClientStatus.VIP) ? new VIPChargingStrategy() : new StandardChargingStrategy();
        this.amount = new Money(amount);
        this.active = active;
    }

    public Client(String name, String address, double amount) {
        this(name, address, ClientStatus.STANDARD, amount, true);
    }

    public Client() {
        this("Helena Ferenc", "Księżyc", ClientStatus.STANDARD, 500, true);
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    /*public boolean isVip(){
        return false;
    }*/
    /*if (status == ClientStatus.VIP)
            this.chargingStrategy = new VIPChargingStrategy();
        else {
            this.chargingStrategy = new StandardChargingStrategy();
        }*/

    public String introduce(){
        return name + " - " + status.getPolishString();
    }

    public boolean canAfford(Money money) {
        return chargingStrategy.canAfford(money);
    }

    public void charge(Money productCost, String cause){ //TODO what to do with 'cause'?
        chargingStrategy.charge(productCost, cause);
    }

    public void recharge(Money amount){
        chargingStrategy.recharge(amount);
    }

    public Money getSaldo(){
        return chargingStrategy.getSaldo();
    }

    public ChargingData generateData() {
        return new ChargingData(amount);
    }

    public Money getAmount() {
        return amount;
    }
}