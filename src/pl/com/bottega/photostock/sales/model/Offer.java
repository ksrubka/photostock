package pl.com.bottega.photostock.sales.model;

import pl.com.bottega.photostock.sales.model.products.AbstractProduct;
import pl.com.bottega.photostock.sales.model.products.Product;

import java.util.List;

/**
 * Created by Beata Iłowiecka on 12.03.2016.
 */
public class Offer {

    final Client owner;
    private List<Product> items;
    double totalCost;

    public Offer(Client owner, List<Product> items) {
        this.items = items;
        this.owner = owner;
        this.totalCost = calculateTotalCost();
    }

    private double calculateTotalCost() {
        double totalCost = 0;
        for (Product product : items){
            // niepotrzebne sprawdzenie bo było sprawdzane przy generowaniu oferty w klasie Reservation
            /*if (!product.canBeReservedBy(owner)){
                continue;
            }
            else {*/
            totalCost += product.getPrice();
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

    public double getTotalCost(){
        return totalCost;
    }

}
