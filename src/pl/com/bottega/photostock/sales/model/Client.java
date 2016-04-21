package pl.com.bottega.photostock.sales.model;

/**
 * Created by Beata Iłowiecka on 12.03.2016.
 */
public class Client {

    protected String name;
    protected String address;
    protected ChargingStrategy chargingStrategy;
    protected Money amount;
    protected Money debt;
    protected Money creditLimit;
    protected boolean active;


    public Client(String name, String address, ClientStatus status, double amount, boolean active) {
        this.name = name;
        this.address = address;
        this.chargingStrategy = (status == ClientStatus.VIP) ? new VIPChargingStrategy() : new StandardChargingStrategy();
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

    /*public String introduce(){
        return name + " - " + status.getPolishString();
    }*/

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
}