package pl.com.bottega.photostock.sales.api;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.com.bottega.photostock.sales.infrastructure.repositories.*;
import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.Product;
import pl.com.bottega.photostock.sales.model.Purchase;
import pl.com.bottega.photostock.sales.model.Reservation;
import pl.com.bottega.photostock.sales.model.exceptions.ProductNotAvailableException;

/**
 * Created by Beata Iłowiecka on 08.05.16.
 */
public class PurchaseProcessTest {

    private static final String STANDARD_USER_NR = "nr1";
    private static final String VIP_USER_NR = "nr2";
    private static final String AVAILABLE_PRODUCT_NR = "nr1";
    private static final String UNAVAILABLE_PRODUCT_NR = "nr2";
    private static final String NOT_EXISTING_PRODUCT_NR = "nr7";

    private PurchaseProcess purchaseProcess;
    private ProductRepository productRepository;
    private ClientRepository clientRepository;
    private ReservationRepository reservationRepository;

    @Before
    public void initPurchaseProcess(){
        purchaseProcess = new PurchaseProcess();
        productRepository = new FakeProductRepository();
        clientRepository = new FakeClientRepository();
        reservationRepository = new FakeReservationRepository();
    }

    @After
    public void cleanUp(){
        unreserve(AVAILABLE_PRODUCT_NR);
    }

   /* @Test
    public void shouldCreateEmptyReservationForStandardClient(){
        //given
        PurchaseProcess purchaseProcess = new PurchaseProcess();
        //dodajemy usera i produkty
        //when
        String reservationNr = purchaseProcess.createReservation(STANDARD_USER_NR);
        //then
        Assert.assertNotEquals(reservationNr, "");
    }*/

    @Test
    public void shouldAddAvailableProduct(){
        purchaseProcess.add(STANDARD_USER_NR, AVAILABLE_PRODUCT_NR);
        Reservation reservation = getReservationBy(STANDARD_USER_NR);
        boolean productIsReserved = checkIfProductIsInsideReservation(reservation, AVAILABLE_PRODUCT_NR);
        Assert.assertTrue(productIsReserved);
    }

    @Test(expected = ProductNotAvailableException.class)
    public void shouldNotAddUnavailableProduct(){
        purchaseProcess.add(STANDARD_USER_NR, UNAVAILABLE_PRODUCT_NR);
    }

    @Test(expected = ProductNotAvailableException.class)
    public void shouldNotAddNotExistingProduct(){
        purchaseProcess.add(STANDARD_USER_NR, NOT_EXISTING_PRODUCT_NR);
    }

    @Test(expected = ProductNotAvailableException.class)
    public void shouldNotAddProductReservedByVip(){
        purchaseProcess.add(VIP_USER_NR, AVAILABLE_PRODUCT_NR);
        try {
            purchaseProcess.add(STANDARD_USER_NR, AVAILABLE_PRODUCT_NR);
            Assert.fail();
        }
        catch(ProductNotAvailableException ex){
            ex.getMessage();
        }
    }

    //while vip reserved product
    @Test
    public void shouldNotConfirmPurchase(){
    }

    @Test(expected = ProductNotAvailableException.class)
    public void canNotAddAlreadyAddedProduct(){
        purchaseProcess.add(STANDARD_USER_NR, AVAILABLE_PRODUCT_NR);
        try {
            purchaseProcess.add(STANDARD_USER_NR, AVAILABLE_PRODUCT_NR);
            Assert.fail();
        }
        catch(ProductNotAvailableException ex){
            ex.getMessage();
        }
    }

    private Product getProduct(String productNr){
        return productRepository.load(productNr);
    }

    private Client getClient(String clientNr){
        return clientRepository.load(clientNr);
    }

    private Reservation getReservationBy(String clientNr){
        Client client = getClient(clientNr);
        return reservationRepository.getReservationByOwner(client);
    }

    private void unreserve(String productNr){
        getProduct(productNr).unreserve();
    }

    private void close(Reservation reservation){
        reservation.close();
    }

    private boolean checkIfProductIsInsideReservation(Reservation reservation, String productNr){
        Product product = getProduct(productNr);
        return reservation.checkWhetherReservationContainsProduct(product);
    }
}
