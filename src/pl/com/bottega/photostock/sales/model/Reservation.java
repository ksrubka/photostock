package pl.com.bottega.photostock.sales.model;

import pl.com.bottega.photostock.sales.model.products.Picture;

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

        if (product.isAvailable()){
           throw  new IllegalArgumentException("Product is not available.");
        }
        if (items.contains(product)){
            throw  new IllegalArgumentException("Product is already reserved.");
        }
    }

    public void remove(Picture picture){
        return;
    }

    public Offer generateOffer(){
        List<Product> result = new ArrayList<>();
        for (Product product : items){
            if (product.isAvailable()){
                result.add(product);
            }
        }
        Offer offer = new Offer(owner, result);
        return offer;
    }

    public int getItemsCount(){
        return 0; //TODO dodaj obliczenia
    }

}
