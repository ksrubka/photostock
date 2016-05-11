package pl.com.bottega.photostock.sales.api;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Beata Iłowiecka on 08.05.16.
 */
public class PurchaseProcessTest {

    private static final String STANDARD_USER_NR = "nr1";
    private static final String AVAILABLE_PRODUCT_NR = "nr1";
    private static final String UNAVAILABLE_PRODUCT_NR = "nr2";

    @Test
    public void shouldCreateEmptyReservationForStandardClient(){
        //given
        PurchaseProcess purchaseProcess = new PurchaseProcess();
        //dodajemy usera i produkty
        //when
        String reservationNr = purchaseProcess.createReservation(STANDARD_USER_NR);
        //then
        Assert.assertNotEquals(reservationNr, "");
    }

    @Test
    public void shouldAddAvailableProduct(){
        //given
        PurchaseProcess purchaseProcess = new PurchaseProcess();
        String reservationNr = purchaseProcess.createReservation(STANDARD_USER_NR);
        //when
        purchaseProcess.add(reservationNr, AVAILABLE_PRODUCT_NR);
        //then
    }

    @Test
    public void shouldNotAddUnavailableProduct(){

    }

    @Test()
    public void canNotAddAlreadyAddedPRoduct(){
        //given
        PurchaseProcess purchaseProcess = new PurchaseProcess();
        String reservationNr = purchaseProcess.createReservation(STANDARD_USER_NR);
        purchaseProcess.add(reservationNr, AVAILABLE_PRODUCT_NR);
        //when
        try {
            purchaseProcess.add(reservationNr, AVAILABLE_PRODUCT_NR);
            Assert.fail();
        }
        catch(IllegalArgumentException ex){
            //expected
        }
    }

}
