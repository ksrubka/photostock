package pl.com.bottega.photostock.sales.infrastructure.repositories.real;

import org.junit.Before;
import org.junit.Test;
import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.Money;
import pl.com.bottega.photostock.sales.model.Product;
import pl.com.bottega.photostock.sales.model.Purchase;
import pl.com.bottega.photostock.sales.model.products.Picture;
import pl.com.bottega.photostock.sales.model.ClientStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Beata Iłowiecka on 10.06.16.
 */
public class JDBCPurchaseRepositoryTest {

    private final static String STANDARD_CLIENT_NR = "nr1";
    private final static String FIRST_PRODUCT_NR = "nr1";
    private final static String SECOND_PRODUCT_NR = "nr2";
    private final static String PURCHASE_NR = "nr1";

    JDBCPurchaseRepository purchaseRepo;

    @Before
    public void setUp() throws Exception {
        Connection c = createConnection();
        dropTables(c);
        createTables(c);
        insertValues(c);
        purchaseRepo = new JDBCPurchaseRepository("jdbc:hsqldb:mem:stock", "SA", "");
        c.close();
    }

    private void dropTables(Connection c) throws Exception {
        c.createStatement().executeUpdate("DROP TABLE Clients IF EXISTS");
        c.createStatement().executeUpdate("DROP TABLE PurchaseProducts IF EXISTS");
        c.createStatement().executeUpdate("DROP TABLE Products IF EXISTS");
        c.createStatement().executeUpdate("DROP TABLE Purchases IF EXISTS");
    }

    private void createTables(Connection c) throws Exception {
        createProductsTable(c);
        createClientsTable(c);
        createPurchaseTable(c);
        createPurchasesProductsTable(c);
    }

    private void createProductsTable(Connection c) throws Exception {
        c.createStatement().executeUpdate("CREATE TABLE Products (\n" +
                "  id INTEGER IDENTITY PRIMARY KEY,\n" +
                "  number VARCHAR(20) NOT NULL,\n" +
                "  type VARCHAR(20) NOT NULL,\n" +
                "  name VARCHAR(255) NOT NULL,\n" +
                "  available BOOLEAN DEFAULT true NOT NULL,\n" +
                "  priceCents INTEGER DEFAULT 0 NOT NULL,\n" +
                "  priceCurrency CHAR(3) DEFAULT 'PLN' NOT NULL,\n" +
                "  length BIGINT\n" +
                ");");
    }

    private void createClientsTable(Connection c) throws Exception {
        c.createStatement().executeUpdate("CREATE TABLE Clients (\n" +
                "  id INTEGER IDENTITY PRIMARY KEY,\n" +
                "  number VARCHAR(20) NOT NULL,\n" +
                "  name VARCHAR(255) NOT NULL,\n" +
                "  address VARCHAR(255) NOT NULL,\n" +
                "  amount INTEGER DEFAULT 0 NOT NULL,\n" +
                "  currency CHAR(3) DEFAULT 'PLN' NOT NULL,\n" +
                "  active BOOLEAN DEFAULT true NOT NULL,\n" +
                "  status VARCHAR(20) DEFAULT 'standard' NOT NULL,\n" +
                ");");
    }

    private void createPurchaseTable(Connection c) throws Exception {
        c.createStatement().executeUpdate("CREATE TABLE Purchases (\n" +
                " id INTEGER IDENTITY PRIMARY KEY,\n" +
                " clientId INTEGER FOREIGN KEY REFERENCES Clients(id),\n" +
                " createDate TIMESTAMP NOT NULL\n" +
                ");");

    }

    private void createPurchasesProductsTable(Connection c) throws Exception {
        c.createStatement().executeUpdate("CREATE TABLE PurchasesProducts (\n" +
                " purchaseId INTEGER FOREIGN KEY REFERENCES Purchases(id),\n" +
                " productId INTEGER FOREIGN KEY REFERENCES Products(id),\n" +
                " PRIMARY KEY (purchaseId, productId)\n" +
                ");");
    }

    private void insertValues(Connection c) throws Exception {
        insertTestClient(c);
        insertFirstTestProduct(c);
        insertSecondTestProduct(c);
        insertTestPurchase(c);
        insertTestProductsPurchases(c);
    }

    private void insertTestClient(Connection c) throws Exception {
        PreparedStatement s = c.prepareStatement("INSERT INTO Clients " +
                "(number, name, address, amount, currency, active) " +
                "VALUES (?, 'Jan Nowak', 'ul. Koralowa 10', 10000, 'PLN', true);");
        s.setString(1, STANDARD_CLIENT_NR);
    }

    private void insertFirstTestProduct(Connection c) throws Exception {
        PreparedStatement s = c.prepareStatement("INSERT INTO Products " +
                "(number, name, available, priceCents, priceCurrency, length, type) " +
                "VALUES (?,'Mazda 3', true, 200, 'USD', NULL, 'Picture');\n");
        s.setString(1, FIRST_PRODUCT_NR);
    }

    private void insertSecondTestProduct(Connection c) throws Exception {
        PreparedStatement s = c.prepareStatement("INSERT INTO Products " +
                "(number, name, available, priceCents, priceCurrency, length, type) " +
                "VALUES (?, 'Mazda 6', true, 250, 'USD', NULL, 'Picture');");
        s.setString(1, SECOND_PRODUCT_NR);
    }

    private void insertTestPurchase(Connection c) throws Exception {
        c.createStatement().executeUpdate("INSERT INTO Purchases (clientId, createDate) VALUES (0, '2016-01-12 10:00:00');");
    }

    private void insertTestProductsPurchases(Connection c) throws Exception {
        c.createStatement().executeUpdate("INSERT INTO PurchasesProducts (purchaseId, productId) VALUES (0, 0)");
        c.createStatement().executeUpdate("INSERT INTO PurchasesProducts (purchaseId, productId) VALUES (0, 1)");
    }

    private Connection createConnection() throws Exception {
        return DriverManager.getConnection("jdbc:hsqldb:mem:stock", "SA", "");
    }

    @Test
    public void shouldLoadPurchase() {
        try (Connection c = createConnection()) {
            List<Product> items = createItemList(c);
            Client client = createClient(c, STANDARD_CLIENT_NR);
            Purchase testPurchase = new Purchase(client, items);
            Purchase loadedPurchase = purchaseRepo.load("nr1");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Product> createItemList(Connection c) throws Exception {
        List<Product> items = new ArrayList<>();
        Product pic1 = getPicture(c, FIRST_PRODUCT_NR);
        Product pic2 = getPicture(c, SECOND_PRODUCT_NR);
        items.add(pic1);
        items.add(pic2);
        return items;
    }

    private Product getPicture(Connection c, String productNr) throws Exception {
        PreparedStatement s = c.prepareStatement("SELECT * FROM Products WHERE number=?;");
        s.setString(1, productNr);
        ResultSet rs = s.executeQuery();
        String number = rs.getString("number");
        int cents = rs.getInt("priceCents");
        String currency = rs.getString("priceCurrency");
        Money amount = new Money((cents / 100), currency);
        boolean active = rs.getBoolean("available");
        return new Picture(number, amount, active);
    }

    private Client createClient(Connection c, String clientNr) throws Exception {
        PreparedStatement s = c.prepareStatement("SELECT * FROM Clients WHERE number=?;");
        s.setString(1, clientNr);
        ResultSet rs = s.executeQuery();

        String name = rs.getString("name");
        String address = rs.getString("address");
        String statusName = rs.getString("status");
        ClientStatus status = ClientStatus.valueOf(statusName.toUpperCase());
        double amount = rs.getInt("amount")/100;
        String currency = rs.getString("currency");
        Boolean active = rs.getBoolean("active");
        String number = rs.getString("number");
        return new Client(name, address, status, amount, currency, active, number);

    }
}