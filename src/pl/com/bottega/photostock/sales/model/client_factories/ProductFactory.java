package pl.com.bottega.photostock.sales.model.client_factories;

import pl.com.bottega.photostock.sales.model.Product;
import pl.com.bottega.photostock.sales.model.products.*;

import java.time.Duration;

import static pl.com.bottega.photostock.sales.model.products.Channel.STEREO;

/**
 * Created by Beata Iłowiecka on 02.05.16.
 */
public class ProductFactory {

    public static Product create(String productNumber, double price, ProductType productType) {
        switch (productType){
            case PICTURE:
                return new Picture(productNumber, price, null);
            case CLIP:
                return new Clip(productNumber, price, Duration.ZERO);
            case SONG:
                return  new Song(productNumber, price,"", "", Duration.ZERO, STEREO);
            default:
                throw new IllegalArgumentException("Podany typ produktu nie istnieje");
        }
    }
}
