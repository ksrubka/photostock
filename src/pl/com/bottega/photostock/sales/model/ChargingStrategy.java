package pl.com.bottega.photostock.sales.model;

/**
 * Created by Beata Iłowiecka on 21.04.16.
 */
public interface ChargingStrategy {

    boolean canAfford(double money);
    void charge(double pictureCost, String cause);
    void recharge(double amount);
    double getSaldo();
}
