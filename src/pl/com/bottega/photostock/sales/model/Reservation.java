package pl.com.bottega.photostock.sales.model;

import pl.com.bottega.photostock.sales.infrastructure.repositories.factory.ApprovingFactory;
import pl.com.bottega.photostock.sales.model.client_strategies.ApprovingStrategy;

import java.util.*;

/**
 * Created by Beata Iłowiecka on 12.03.2016.
 */
public class Reservation {

    private Client owner;
    private List<Product> items = new LinkedList<>();
    private String number;

    public Reservation(Client owner) {
        this.owner = owner;
    }

    public void add(Product... products){

        for (Product product : products) {
            if (!product.canBeReservedBy(owner)) {
                throw new IllegalArgumentException("Produkt jest niedostępny.");
            }
            if (items.contains(product)) {
                throw new IllegalArgumentException("Produkt jest już zarezerwowany.");
            }
            items.add(product);
        }
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
        Comparator<Product> comparator = new PriceAndNameProductComparator();
        Collections.sort(result, comparator);

        Offer offer = new Offer(owner, result);

        for (Product product : offer.getItems()){
            System.out.println("Nr produktu: " + product.getNumber() + ", cena: " + product.getPrice());
        }
        return offer;
    }

    public int getItemsCount(){
        return items.size();
    }

    public Client getOwner() {
        return owner;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
