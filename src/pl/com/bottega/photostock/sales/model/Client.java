package pl.com.bottega.photostock.sales.model;

/**
 * Created by Beata Iłowiecka on 12.03.2016.
 */
public class Client {

    private String name;
    private String address;
    private boolean isVip;
    private double amount;
    private double debt;
    private double creditLimit;
    //private boolean active = true;

    public Client(String name, String address, boolean isVip, double amount, double debt, double creditLimit) {
        // TODO  konstruktor nie powinien zawierać możliwości ustawiania debetu, powinien on domyślnie wynosić 0
        this.name = name;
        this.address = address;
        this.isVip = isVip;
        this.amount = amount;
        this.debt = debt;
        this.creditLimit = creditLimit;
    }

    public Client(String name, String address, double amount) { // bez sensu żeby NIE vipowi ustawiać creditLimit
        this(name, address, false, amount, 0, 0);
    }

    public boolean canAfford(double money) {

        //===== git wersja bez ifów =====
        double purchasePotential = amount + creditLimit;
        return  purchasePotential >= money;

    }

    public void charge(double pictureCost, String cause){

        if (canAfford(pictureCost)){
            if (!isVip){
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
        }/* else {
            if (debt )
        }*/
    }

    public double getSaldo(){
        if (amount >= 0){
            return amount;
        } else return debt;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return true;
    }
}