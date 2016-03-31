package pl.com.bottega.photostock.sales.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Beata Iłowiecka on 12.03.2016.
 */
public class Reservation {

    private Client owner;
    private ArrayList<Picture> items;

    public Reservation(Client owner) {

        this.owner = owner;
    }

    public void add(Picture picture){

        for (Picture p : items){
            if (picture == p){
                return;
            } else {
                if (picture.isAvailable()){
                    // items.add(picture);
                }
            }
        }
    }

    public void remove(Picture picture){
        return;
    }

    public Offer generateOffer(){
        // TODO trzeba dodać warunki
        Offer offer = new Offer(items);
        return offer;
    }

    public int getItemsCount(){
        return 0; //TODO dodaj obliczenia
    }

}
