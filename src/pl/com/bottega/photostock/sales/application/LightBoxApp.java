package pl.com.bottega.photostock.sales.application;

import pl.com.bottega.photostock.sales.model.*;
import pl.com.bottega.photostock.sales.model.products.Picture;
import pl.com.bottega.photostock.sales.model.Product;

import java.util.ArrayList;

public class LightBoxApp {
    public static void main(String[] args) {

        //********************************************* Pictures *********************************************

        String[] var1 = {"tree", "grass", "big"};
        String[] var2 = {"tree", "dog", "big", "oak"};
        String[] var3 = {"lines", "stripes", "green", "oak"};
        String[] var4 = {};
        String[] var5 = {"wall", "call", "phone"};
        String[] var6 = {"rover", "road", "big"};
        String[] var7 = {"mice", "grass", "small"};
        String[] var8 = {"cloud", "elephant", "nice"};
        String[] var9 = {"baloon", "card", "sky"};

        Picture pic1 = new Picture("01", 1, var1, true);
        Picture pic2 = new Picture("02", 1, var2, false);
        Picture pic3 = new Picture("03", 7, var3, true);
        Picture pic4 = new Picture("04", 1, var4, true);
        Picture pic5 = new Picture("05", 1, var5, true);
        Picture pic6 = new Picture("06", -90, var6, true);
        Picture pic7 = new Picture("07", 10000, var7, true);
        Picture pic8 = new Picture("08", 1, var8, true);
        Picture pic9 = new Picture("09", 1, var9, false);

        //********************************************* LightBoxy w tablicy ********************************************

        Client paniHeleka = new Client();

        LightBox lightBox1 = new LightBox(paniHeleka);
        LightBox lightBox2 = new LightBox(paniHeleka);
        LightBox lightBox3 = new LightBox(paniHeleka);
        LightBox lightBox4 = new LightBox(paniHeleka);

        lightBox1.add(pic1, pic2);
        lightBox2.add(pic3, pic4);
        lightBox3.add(pic5, pic6);
        lightBox4.add(pic7, pic8, pic9);

        ArrayList<LightBox> lightBoxes = new ArrayList<>();

        addLightBoxes(lightBoxes, lightBox1, lightBox2, lightBox3, lightBox4);

        setLightBoxesNames(lightBoxes, args);

        displayLightBoxes(lightBoxes);
    }

    public static void addPicutres(ArrayList<Picture> pictures, Picture ...pics){

        for (Picture p : pics){
            pictures.add(p);
            //System.out.println("zdjęcie dodane " + p.getNumber());
        }
    }

    public static void addLightBoxes(ArrayList<LightBox> lightBoxes, LightBox ...lightBox){

        for (LightBox l : lightBox){
            lightBoxes.add(l);
        }
        System.out.println();
    }

    public static void setLightBoxesNames(ArrayList<LightBox> lightBoxes1, String[] args){
        int argsIndex = 0;

        for (LightBox l : lightBoxes1){
            /*Scanner sc = new Scanner(System.in);
            System.out.println("Podaj imię dla nowego lightBoxa:");
            String name = sc.next();*/
            l.setName(args[argsIndex]);
            ++argsIndex;
        }
    }

    public static void displayLightBoxes(ArrayList<LightBox> lightBoxes1){
        int lineNumber = 1;
        final char UNAVAILABE_PICTURE = 'X';

        for (LightBox l : lightBoxes1){
            System.out.println("=========================");
            System.out.println(lineNumber + ". " +
                    l.getName() + " - " + (l.getOwner()).getName());

            for (Product product : l.getItems()){
                if (!product.isAvailable()){
                    System.out.print(UNAVAILABE_PICTURE + " | ");
                }
                else {
                    System.out.print("  | ");
                }
                System.out.println(product.getNumber() + " | " + product.getPrice());
            }
            /*if (!((lightBoxes1.size()) == lineNumber)){
                System.out.println("=========================");
            }*/ ++lineNumber;

        }
    }
}