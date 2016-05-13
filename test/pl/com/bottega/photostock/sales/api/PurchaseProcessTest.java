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

import java.util.List;

/**
 * Created by Beata Iłowiecka on 08.05.16.
 */
public class PurchaseProcessTest {

    private static final String STANDARD_USER_NR = "nr1";
    private static final String VIP_USER_NR = "nr2";
    private static final String GOLD_USER_NR = "nr3";
    private static final String POOR_USER_NR = "nr4";
    private static final String SECOND_VIP_USER_NR = "nr5";

    private static final String AVAILABLE_PRODUCT_NR = "nr1";
    private static final String SECOND_AVAILABLE_PRODUCT_NR = "nr3";
    private static final String UNAVAILABLE_PRODUCT_NR = "nr2";
    private static final String NOT_EXISTING_PRODUCT_NR = "nr7";

    private PurchaseProcess purchaseProcess;

    @Before
    public void initPurchaseProcess() {
        purchaseProcess = new PurchaseProcess();
    }

    @After
    public void cleanUp() {
        unreserve(AVAILABLE_PRODUCT_NR);
        purchaseProcess.reservationRepository.destroyReservations();
        cancelPurchase(AVAILABLE_PRODUCT_NR);
    }

    @Test
    public void reservationNumbersShouldBeDifferent() {
        Client standardClient = getClient(STANDARD_USER_NR);
        Client vipClient = getClient(VIP_USER_NR);
        purchaseProcess.add(STANDARD_USER_NR, AVAILABLE_PRODUCT_NR);
        purchaseProcess.add(VIP_USER_NR, SECOND_AVAILABLE_PRODUCT_NR);
        Reservation standardReservation = purchaseProcess.reservationRepository.load(standardClient);
        Reservation vipReservation = purchaseProcess.reservationRepository.load(vipClient);
        Assert.assertNotEquals(standardReservation, vipReservation);
    }

    @Test
    public void shouldAddAvailableProduct() {
        purchaseProcess.add(STANDARD_USER_NR, AVAILABLE_PRODUCT_NR);
        Reservation reservation = getReservationBy(STANDARD_USER_NR);
        boolean productIsReserved = checkIfProductIsInsideReservation(reservation, AVAILABLE_PRODUCT_NR);
        Assert.assertTrue(productIsReserved);
    }

    @Test(expected = ProductNotAvailableException.class)
    public void shouldNotAddUnavailableProduct() {
        purchaseProcess.add(STANDARD_USER_NR, UNAVAILABLE_PRODUCT_NR);
    }

    @Test(expected = ProductNotAvailableException.class)
    public void shouldNotAddNotExistingProduct() {
        purchaseProcess.add(STANDARD_USER_NR, NOT_EXISTING_PRODUCT_NR);
    }

    @Test
    public void shouldNotAddProductReservedByVip() {
        failAddProductForTwoClients(VIP_USER_NR, STANDARD_USER_NR);
    }

    @Test
    public void shouldAddProductReservedByStandardClientToVipClientReservation() {
        successAddProductForTwoClients(STANDARD_USER_NR, VIP_USER_NR);
    }

    @Test
    public void shouldNotAddProductReservedByVipForAnotherVip() {
        failAddProductForTwoClients(VIP_USER_NR, SECOND_VIP_USER_NR);
    }

    @Test
    public void shouldNotAddAlreadyAddedProduct() {
        failAddProductForTwoClients(STANDARD_USER_NR, STANDARD_USER_NR);
    }

    @Test
    public void shouldAddProductForSecondClient() {
        successAddProductForTwoClients(STANDARD_USER_NR, GOLD_USER_NR);
    }

    @Test(expected = ProductNotAvailableException.class)
    public void shouldNotReserveProductIfClientHasNoMoney(){
        purchaseProcess.add(POOR_USER_NR, AVAILABLE_PRODUCT_NR);
    }

    //when standard client was first to reserve the product
    @Test
    public void shouldAddProductToVipReservation() {
        purchaseProcess.add(STANDARD_USER_NR, AVAILABLE_PRODUCT_NR);
        purchaseProcess.add(VIP_USER_NR, AVAILABLE_PRODUCT_NR);
        Reservation vipReservation = getReservationBy(VIP_USER_NR);
        Assert.assertTrue(checkIfProductIsInsideReservation(vipReservation, AVAILABLE_PRODUCT_NR));
    }

    //while vip meanwhile reserved the product
    @Test
    public void shouldNotConfirmStandardClientPurchase() {
        purchaseProcess.add(STANDARD_USER_NR, AVAILABLE_PRODUCT_NR);
        Client standardClient = getClient(STANDARD_USER_NR);
        Reservation standardReservation = purchaseProcess.reservationRepository.load(standardClient);
        String standardReservationNr = standardReservation.getNumber();
        purchaseProcess.calculateOffer(standardReservationNr);
        purchaseProcess.add(VIP_USER_NR, AVAILABLE_PRODUCT_NR);
        try {
            purchaseProcess.confirm(standardReservationNr);
            Assert.fail();
        }
        catch (ProductNotAvailableException ex) {
            ex.getMessage();
        }
    }

    @Test
    public void shouldConfirmVipClientPurchase() {
        purchaseProcess.add(VIP_USER_NR, AVAILABLE_PRODUCT_NR);
        String vipReservationNr = getReservationNrBy(VIP_USER_NR);
        purchaseProcess.calculateOffer(vipReservationNr);
        purchaseProcess.confirm(vipReservationNr);
    }

    @Test
    public void shouldNotDisturbVipConfirmation() {
        generateOfferFor(VIP_USER_NR);
        try {
            purchaseProcess.add(STANDARD_USER_NR, AVAILABLE_PRODUCT_NR);
            Assert.fail();
        }
        catch (ProductNotAvailableException ex){
            ex.getMessage();
        }
        String reservationNr = getReservationNrBy(VIP_USER_NR);
        purchaseProcess.confirm(reservationNr);
    }

    private void generateOfferFor(String userNr) {
        purchaseProcess.add(userNr, AVAILABLE_PRODUCT_NR);
        String reservationNr = getReservationNrBy(userNr);
        purchaseProcess.calculateOffer(reservationNr);
    }

    //test using this needs expected
    private void successAddProductForTwoClients(String firstClientNr, String secondClientNr)  {
        purchaseProcess.add(firstClientNr, AVAILABLE_PRODUCT_NR);
        purchaseProcess.add(secondClientNr, AVAILABLE_PRODUCT_NR);
    }

    private void failAddProductForTwoClients(String firstClientNr, String secondClientNr)  {
        purchaseProcess.add(firstClientNr, AVAILABLE_PRODUCT_NR);
        try {
            purchaseProcess.add(secondClientNr, AVAILABLE_PRODUCT_NR);
            Assert.fail();
        }
        catch (ProductNotAvailableException ex) {
            ex.getMessage();
        }
    }

    private boolean hasConfirmedPurchase(String userNr){
        return false;
    }

    private Product getProduct(String productNr) {
        return purchaseProcess.productRepository.load(productNr);
    }

    private Client getClient(String clientNr) {
        return purchaseProcess.clientRepository.load(clientNr);
    }

    private Reservation getReservationBy(String clientNr) {
        Client client = getClient(clientNr);
        return purchaseProcess.reservationRepository.getReservationByOwner(client);
    }

    private String getReservationNrBy(String userNr) {
        Reservation reservation = getReservationBy(userNr);
        return reservation.getNumber();
    }

    private void unreserve(String productNr) {
        getProduct(productNr).unreserve();
    }

    private void cancelPurchase(String productNr) {
        getProduct(productNr).undoPurchase();
    }

    private boolean checkIfProductIsInsideReservation(Reservation reservation, String productNr) {
        Product product = getProduct(productNr);
        return reservation.checkWhetherReservationContainsProduct(product);
    }
}