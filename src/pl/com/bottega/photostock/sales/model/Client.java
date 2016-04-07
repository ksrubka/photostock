package pl.com.bottega.photostock.sales.model;

/**
 * Created by Beata Iłowiecka on 12.03.2016.
 */
public class Client {

    private String name;
    private String address;
    private ClientStatus status;
    private double amount;
    private double debt;
    private double creditLimit;
    private boolean active;

    public Client(String name, String address, ClientStatus status, double amount, double debt, double creditLimit, boolean active) {
        // TODO  konstruktor nie powinien zawierać możliwości ustawiania debetu, powinien on domyślnie wynosić 0
        this.name = name;
        this.address = address;
        this.status = status;
        this.amount = amount;
        this.debt = debt;
        this.creditLimit = creditLimit;
        this.active = active;
    }

    public Client(String name, String address, double amount) { // bez sensu żeby NIE-vipowi ustawiać creditLimit
        this(name, address, ClientStatus.STANDARD, amount, 0, 0, true);
    }

    public Client() {
        this("Helena Ferenc", "Księżyc", ClientStatus.STANDARD, 500, 0, 0, true);
    }

    public boolean canAfford(double money) {

        double purchasePotential = amount + creditLimit;
        return  purchasePotential >= money;
    }

    public void charge(double pictureCost, String cause){ //TODO what to do with 'cause'?

        if (canAfford(pictureCost)){
            if (!isVip()){
                amount -= pictureCost;
            } else {
                if (amount >= pictureCost){
                    amount -= pictureCost;
                } else if ((pictureCost - amount) <= creditLimit){
                    debt = pictureCost - amount;
                    amount = 0;
                } else return;
            }
        }
    }

    public void recharge(double amount){
        if (this.amount >= 0){
            this.amount += amount;
        } else {
            if (debt >= amount) {
                debt -= amount;
            } else {
                debt = 0;
                this.amount += amount - debt;
            }
        }
    }

    public double getSaldo(){
        if (amount >= 0){
            return amount;
        }
        else {
            return -debt;
        }
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isVip() {

        if (status == ClientStatus.VIP){
            return true;
        }
        else {
            return false;
        }
    }

    public String introduce(){
        return name + " - " + status.getPolishString();
    }
}