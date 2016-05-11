package pl.com.bottega.photostock.sales.api;

import org.junit.Assert;
import org.junit.Test;
import pl.com.bottega.photostock.sales.model.Purchase;
import pl.com.bottega.photostock.sales.model.exceptions.ProductNotAvailableException;

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

    @Test(expected=ProductNotAvailableException.class)
    public void shouldNotAddUnavailableProduct(){
        PurchaseProcess purchaseProcess = new PurchaseProcess();
        String reservationNr = purchaseProcess.createReservation(STANDARD_USER_NR);
        purchaseProcess.add(reservationNr, UNAVAILABLE_PRODUCT_NR);
    }

    @Test
    public void shouldNotAddProductReservedByVip(){
    }


    @Test
    public void shouldNotConfirmPurchase(){

    }

    @Test()
    public void canNotAddAlreadyAddedProduct(){
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
