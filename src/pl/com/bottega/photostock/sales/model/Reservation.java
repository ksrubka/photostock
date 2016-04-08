package pl.com.bottega.photostock.sales.model;

import pl.com.bottega.photostock.sales.model.products.Picture;
import pl.com.bottega.photostock.sales.model.products.Product;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Beata Iłowiecka on 12.03.2016.
 */
public class Reservation {

    private Client owner;
    private List<Product> items = new LinkedList<>();

    public Reservation(Client owner) {

        this.owner = owner;
    }

    public void add(Product product){

        if (!product.canBeReservedBy(owner)){
           throw  new IllegalArgumentException("Produkt jest niedostępny.");
        }
        if (items.contains(product)){
            throw  new IllegalArgumentException("Produkt jest już zarezerwowany.");
        }
        items.add(product);
    }

    public void remove(Product product){
        boolean removed = items.remove(product);

        if (!removed){
            throw new IllegalArgumentException("Nie ma takiego produktu w rezerwacji");
        }
    }

    public Offer generateOffer(){
        List<Product> result = new ArrayList<>();
        for (Product product : items){
            if (product.canBeReservedBy(owner)){
                result.add(product);
            }
        }
        Offer offer = new Offer(owner, result);
        return offer;
    }

    public int getItemsCount(){
        return items.size();
    }

}
