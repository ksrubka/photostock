package pl.com.bottega.photostock.sales.application.servlet;

import pl.com.bottega.photostock.sales.model.Offer;
import pl.com.bottega.photostock.sales.process.PurchaseComponent;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Slawek on 19/04/16.
 */
@WebServlet(name="Create", displayName="Create Servlet", urlPatterns = {"/create"}, loadOnStartup=1)
public class CreateServlet extends HttpServlet {

    //private PurchaseComponent purchaseComponent = new PurchaseComponent();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        String clientNr = req.getParameter("clientNr");

        //String resNr = purchaseComponent.createReservation(clientNr);

        response.setContentType("text/plain");
        response.setStatus(HttpServletResponse.SC_OK);

        PrintWriter out = response.getWriter();
        //out.println(resNr);
        out.close();
    }
}
