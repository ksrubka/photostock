package pl.com.bottega.photostock.sales.model;

import java.util.List;

/**
 * Created by Beata Iłowiecka on 12.03.2016.
 */
public class Offer {

    final Client owner;
    private List<Product> items;
    Money totalCost;

    public Offer(Client owner, List<Product> items) {
        this.items = items;
        this.owner = owner;
        this.totalCost = calculateTotalCost();
    }

    public Money calculateTotalCost() {
        Money totalCost = new Money(0, owner.getCurrency());
        for (Product product : items) {
            if (product.hasSameCurrencyAs(owner))
                totalCost = totalCost.add(product.getPrice());
        }
        return totalCost;
    }

    public boolean sameAs(Offer otherOffer, double percent) {
        return (owner.equals(otherOffer.owner) && items.equals(otherOffer.items) && totalCost.equals(otherOffer.totalCost));
    }

    public int getItemsCount(){
        return items.size();
    }

    public List<Product> getItems(){
        return items;
    }

    public Client getOwner() {
        return owner;
    }
}