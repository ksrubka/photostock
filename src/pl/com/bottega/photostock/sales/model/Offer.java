package pl.com.bottega.photostock.sales.model;

import pl.com.bottega.photostock.sales.model.products.Picture;

import java.util.ArrayList;

/**
 * Created by Beata Iłowiecka on 12.03.2016.
 */
public class Offer {

    private ArrayList<Picture> items;

    public Offer(ArrayList<Picture>  items) {

        this.items = items;
    }

    public boolean sameAs(Offer offer, double percent){

        return false; //TODO dodaj obliczenia
    }

    public int getItemsCount(){

        return 0; //TODO dodaj obliczenia
    }

    public ArrayList<Picture>  getItems(){

        return items;
    }

    public double getTotalCost(){

        return 0; //TODO dodaj obliczenia
    }

}
