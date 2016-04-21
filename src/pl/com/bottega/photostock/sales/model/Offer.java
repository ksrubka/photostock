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

    private Money calculateTotalCost() {
        Money totalCost = new Money(0);
        for (Product product : items){
            totalCost.add(product.getPrice());
        }
        return totalCost;
    }

    public boolean sameAs(Offer offer, double percent){
        if (owner == offer.owner && items == offer.items && totalCost == offer.totalCost){
            return true;
        }
        else {
            return false;
        }
    }

    public int getItemsCount(){
        return items.size();
    }

    public List<Product> getItems(){
        return items;
    }

}
