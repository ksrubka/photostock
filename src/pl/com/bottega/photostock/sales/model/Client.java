package pl.com.bottega.photostock.sales.model;

/**
 * Created by Beata Iłowiecka on 12.03.2016.
 */
public class Client {

    protected String name;
    protected String address;
    protected ClientStatus status;
    protected Money amount;
    protected boolean active;

    public Client(String name, String address, ClientStatus status, double amount, boolean active) {
        this.name = name;
        this.address = address;
        this.status = status;
        this.amount = new Money(amount);
        this.active = active;
    }

    public Client(String name, String address, double amount) {
        this(name, address, ClientStatus.STANDARD, amount, true);
    }

    public Client() {
        this("Helena Ferenc", "Księżyc", ClientStatus.STANDARD, 500, true);
    }

    public boolean canAfford(double money) {
        return  amount. >= money;
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
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isVip(){
        return false;
    }

    public String introduce(){
        return name + " - " + status.getPolishString();
    }
}