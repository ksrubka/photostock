package pl.com.bottega.photostock.sales.application;

import pl.com.bottega.photostock.sales.infrastructure.FakeProductRepository;
import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.ProductNotAvailableException;
import pl.com.bottega.photostock.sales.model.Reservation;
import pl.com.bottega.photostock.sales.model.products.Clip;
import pl.com.bottega.photostock.sales.model.products.Product;
import pl.com.bottega.photostock.sales.model.products.ProductRepository;

/**
 * Created by Beata Iłowiecka on 17.04.16.
 */
public class ReservationTestConsoleApp {

    public static void main(String[] args) {

        ProductRepository repo = new FakeProductRepository();

        Product mustang = repo.load("nr1");
        Product multipla = repo.load("nr2");
        Product mazda = repo.load("nr3");
        Product kotek = repo.load("01");
        Product clip = repo.load("01a");

        Client takiSobieClient = new Client("Zegrzysław", "tajny", 120);

        Reservation reservation = new Reservation(takiSobieClient);

        try {
            reservation.add(mustang, mazda, multipla, kotek, clip);
            reservation.generateOffer();
        }
        catch (ProductNotAvailableException ex){
            System.out.println(ex.getClazz() + " " + ex.getMessage() + " " + ex.getNumber());
        }

        repo.save(mustang);
        repo.save(multipla);
        repo.save(mazda);
        repo.save(kotek);
        repo.save(clip);
    }
}
