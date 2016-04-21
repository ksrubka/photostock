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

    public ClientVIP(){
        this("Pani Kasia", "tajny", 122, true, 0, 500);
    }

    public boolean isVip(){
        return true;
    }
}
