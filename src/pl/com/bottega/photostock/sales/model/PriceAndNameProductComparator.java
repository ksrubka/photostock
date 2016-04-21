package pl.com.bottega.photostock.sales.model;

import java.util.Comparator;

/**
 * Created by Beata Iłowiecka on 17.04.16.
 */
public class PriceAndNameProductComparator implements Comparator<Product> {

    @Override
    public int compare(Product o1, Product o2){
        if (o1.getPrice().equals(o2.getPrice())){
            return o1.getNumber().compareTo(o2.getNumber());
        }
        else {
            if (o1.getPrice().lowerThan(o2.getPrice()))
                return 1;
            return -1;
        }
    }
}
