package pl.com.bottega.photostock.sales.model;

import pl.com.bottega.photostock.sales.model.client_factories.ApprovingFactory;
import pl.com.bottega.photostock.sales.model.client_factories.StrategyFactory;
import pl.com.bottega.photostock.sales.model.client_strategies.*;

/**
 * Created by Beata Iłowiecka on 12.03.2016.
 */
public class Client {

    private String name;
    private String address;
    private ChargingStrategy chargingStrategy;
    private ApprovingStrategy approvingStrategy;
    private Charging charging = new ChargingData();
    private ClientStatus status;
    private Money amount;
    private boolean active;
    private String number;

    public Client(String name, String address, ClientStatus status, double amount, boolean active, String nr) {
        this.name = name;
        this.address = address;
        this.status = status;
        this.chargingStrategy = StrategyFactory.create(status);
        this.approvingStrategy = ApprovingFactory.create(status);
        this.amount = new Money(amount);
        //this.debt = new Money(debt);
        //this.creditLimit = new Money(creditLimit);
        this.active = active;
        this.number = nr;
    }

    public Client(String name, String address, double amount, String nr) {
        this(name, address, ClientStatus.STANDARD, amount, true, nr);
    }

    public Client(String nr) {
        this("Helena Ferenc", "Księżyc", ClientStatus.STANDARD, 500, true, nr);
    }

    public class ChargingData implements Charging {
        public Money getAmount(){
            return amount;
        }

        public void setAmount(Money newAmount){
            amount = newAmount;
        }
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

    public String introduce(){
        return name + " - " + status.getPolishString();
    }

    public boolean canAfford(Money productCost) {
        return chargingStrategy.canAfford(charging, productCost);
    }

    public void charge(Money productCost, String cause){ //TODO what to do with 'cause'?
        chargingStrategy.charge(charging, productCost, cause);
    }

    public void recharge(Money amount){
        chargingStrategy.recharge(charging, amount);
    }

    public Money getSaldo(){
        return chargingStrategy.getSaldo(charging);
    }

    /*public Money getAmount() {
        return amount;
    }

    public Money getDebt() {
        return debt;
    }

    public Money getCreditLimit() {
        return creditLimit;
    }*/

    public ClientStatus getStatus() {
        return status;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}