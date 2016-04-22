package pl.com.bottega.photostock.sales.application.servlet;

import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.Money;
import pl.com.bottega.photostock.sales.model.Offer;
import pl.com.bottega.photostock.sales.model.Reservation;
import pl.com.bottega.photostock.sales.process.PurchaseComponent;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Created by Slawek on 19/04/16.
 */
@WebServlet(name="Purchase", displayName="Purchase Servlet", urlPatterns = {"/purchase"}, loadOnStartup=1)
public class PurchaseServlet extends HttpServlet {

    //private PurchaseComponent purchaseComponent = new PurchaseComponent();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        String prodNr = req.getParameter("prodNr");
        String reservationNr = req.getParameter("resNr");

        //purchaseComponent.addProduct(prodNr, reservationNr);
        //Offer offer = purchaseComponent.generateOffer(reservationNr);


        response.setContentType("text/plain");
        response.setStatus(HttpServletResponse.SC_OK);

        PrintWriter out = response.getWriter();
        //out.println(offer.getTotalCost().toString());
        out.println(prodNr + " " + reservationNr);
        out.close();
    }
}
