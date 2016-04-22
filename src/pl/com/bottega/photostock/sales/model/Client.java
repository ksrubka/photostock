package pl.com.bottega.photostock.sales.model;

import pl.com.bottega.photostock.sales.infrastructure.repositories.factory.StrategyFactory;
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

    private Money debt;
    private Money creditLimit;
    private boolean active;

    public Client(String name, String address, ClientStatus status, double amount, double debt, double creditLimit, boolean active) {
        this.name = name;
        this.address = address;
        this.status = status;
        this.chargingStrategy = StrategyFactory.create(status);
        this.amount = new Money(amount);
        this.debt = new Money(debt);
        this.creditLimit = new Money(creditLimit);
        this.active = active;
    }

    public Client(String name, String address, double amount) {
        this(name, address, ClientStatus.STANDARD, amount, 0, 0, true);
    }

    public Client() {
        this("Helena Ferenc", "Księżyc", ClientStatus.STANDARD, 500, 0, 0, true);
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

    public boolean canAfford(Money productCost, ChargingData chargingData) {
        return chargingStrategy.canAfford(productCost, this.generateData());
    }

    public void charge(Money productCost, String cause){ //TODO what to do with 'cause'?
        chargingStrategy.charge(productCost, cause, this.generateData());
    }

    public void recharge(Money amount){
        chargingStrategy.recharge(amount, this.generateData());
    }

    public Money getSaldo(){
        return chargingStrategy.getSaldo(this.generateData());
    }

    public ChargingData generateData() {
        return new ChargingData(this);
    }

    public Money getAmount() {
        return amount;
    }

    public Money getDebt() {
        return debt;
    }

    public Money getCreditLimit() {
        return creditLimit;
    }
}