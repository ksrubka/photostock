package pl.com.bottega.photostock.sales.application;

import pl.com.bottega.photostock.sales.infrastructure.repositories.ClientRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.FakeClientRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.FakeProductRepository;
import pl.com.bottega.photostock.sales.infrastructure.repositories.ProductRepository;
import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.Product;

/**
 * Created by Beata Iłowiecka on 22.04.16.
 */
public class ChargingApp {

 /*   public static void main(String[] args) {

        ClientRepository clientRepo = new FakeClientRepository();
        Client panLeszek = clientRepo.load("Pan Leszek");

        ProductRepository productRepo = new FakeProductRepository();
        Product mustang = productRepo.load("nr1");

        double saldoPanaLeszka = panLeszek.getSaldo().getDoubleValue();

        System.out.println("Saldo Pana Leszka przed kupnem produktu: " + saldoPanaLeszka);
        System.out.println("Tyle " + panLeszek.getName() + " zapłacił za produkt " + mustang.getNumber() + ": " + mustang.getPrice().getDoubleValue());
        panLeszek.recharge(mustang.getPrice());
        System.out.println("Saldo Pana Leszka po kupnnie produktu: " + saldoPanaLeszka);
    }*/
}
