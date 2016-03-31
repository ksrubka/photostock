package pl.com.bottega.photostock.sales.model;

/**
 * Created by Beata Iłowiecka on 12.03.2016.
 */
public class Offer {

    private Picture[] items;

    public Offer(Picture[] items) {

        this.items = items;
    }

    public boolean sameAs(Offer offer, double percent){

        return false; //TODO dodaj obliczenia
    }

    public int getItemsCount(){

        return 0; //TODO dodaj obliczenia
    }

    public Picture[] getItems(){

        return items;
    }

    public double getTotalCost(){

        return 0; //TODO dodaj obliczenia
    }

}
