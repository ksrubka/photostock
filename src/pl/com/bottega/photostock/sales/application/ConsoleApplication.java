package pl.com.bottega.photostock.sales.application;

import pl.com.bottega.photostock.sales.model.*;
import pl.com.bottega.photostock.sales.model.products.Picture;
import pl.com.bottega.photostock.sales.model.Product;

import java.util.List;

/**
 * Created by Beata Iłowiecka on 12.03.2016.
 */
public class ConsoleApplication {

    /*public static void main(String[] args) {

        Picture pic1 = new Picture("01", 3, new String[]{"#piła", "#drzewo"});
        Picture pic2 = new Picture("02", 4, new String[]{"#skakanka", "#trawa"});
        Picture pic3 = new Picture("03", 5, new String[]{"#piła", "#kasza"});

        Client client1 = new Client("Kasia", "Leśna 5", 120);

        Reservation reservation1 = new Reservation(client1, "nr1");


        // nowy lightbox dostaje nazwę i ją wywołuje
        LightBox lbx = new LightBox(client1, "nr1");
        lbx.changeName("drzewo");
        System.out.println(lbx.getName());
        lbx.close();

        lbx.changeName("piłki");
        System.out.println(lbx.getName());


       // if (true)
         //   return;

        *//*LightBox lightBox1, lightBox2, lightBox3 = new LightBox();
        LightBox lightBox2 = new LightBox();
        LightBox lightBox3 = new LightBox();
        Picture pictureSaw = new Picture();
        lightBox1.add(pictureSaw);*//*

      *//*  Picture pic5, pic6, pic7 = new Picture();*//*

        // symulacja dostepu do bazy danych

        // ========================================

        reservation1.add(pic1);
        reservation1.add(pic2);
        reservation1.add(pic3);

        //sytuacja niedozwolona - ponowne dodanie zdjec do rezerwacji
        reservation1.add(pic1);
        reservation1.add(pic2);
        reservation1.add(pic3);

        // 6
        int count = reservation1.getItemsCount();
        System.out.println(count);

        //7
        Offer offer1 = reservation1.generateOffer();

       *//* //8
        double offerTotalCost = offer1.getTotalCost();

        boolean canAfford = client1.canAfford(offerTotalCost);

        //9
        if (canAfford){
            client1.charge(offerTotalCost, "For the pictures");
            //10
            List<Product> items = offer1.getItems();
            Purchase purchase1 = new Purchase(client1, items);//TODO przepakować z oferty do zukupu(Purchase)

        } else {
            System.out.println("You can not afford that. You need " +
                    offerTotalCost);
        }*//*

        //11

    }*/
}
