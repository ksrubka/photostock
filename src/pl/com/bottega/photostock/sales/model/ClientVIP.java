package pl.com.bottega.photostock.sales.model;

/**
 * Created by Beata Iłowiecka on 07.04.16.
 */
public class ClientVIP extends Client {

    private double debt;
    private double creditLimit;

    public ClientVIP(String name, String address, double amount, boolean active, double debt, double creditLimit){
        super(name, address, ClientStatus.VIP, amount, active);
        this.debt = debt;
        this.creditLimit = creditLimit;
    }

    @Override
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
    }
}
