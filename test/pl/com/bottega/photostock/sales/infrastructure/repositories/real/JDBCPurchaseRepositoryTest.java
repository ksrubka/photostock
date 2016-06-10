package pl.com.bottega.photostock.sales.infrastructure.repositories.real;

import org.junit.Before;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Beata Iłowiecka on 10.06.16.
 */
public class JDBCPurchaseRepositoryTest {

    JDBCPurchaseRepository purchaseRepo;

    @Before
    public void setUp() throws Exception {
        Connection c = createConnection() ;
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

    private void createClientsTable(Connection c)  throws Exception  {
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
        insertTestProducts(c);
        insertTestPurchase(c);
        insertTestProductsPurchases(c);
    }

    private void insertTestClient(Connection c) throws Exception {
        c.createStatement().executeUpdate("INSERT INTO Clients " +
                "(number, name, address, amountCents, amountCurrency, active) " +
                "VALUES ('nr1', 'Jan Nowak', 'ul. Koralowa 10', 10000, 'PLN', true);");
    }

    private void insertTestProducts(Connection c) throws Exception {
        c.createStatement().executeUpdate("INSERT INTO Products " +
                "(number, name, available, priceCents, priceCurrency, length, type) " +
                "VALUES ('nr1','Mazda 3', true, 200, 'USD', NULL, 'Picture');\n"
                );
        c.createStatement().executeUpdate("INSERT INTO Products " +
                "(number, name, available, priceCents, priceCurrency, length, type) " +
                "VALUES ('nr2', 'Mazda 6', true, 250, 'USD', NULL, 'Picture');");
    }

    private void insertTestPurchase(Connection c) throws Exception {
        c.createStatement().executeUpdate("INSERT INTO Purchases (clientId, createDate) VALUES (0, '2016-01-12 10:00:00');");
    }

    private void insertTestProductsPurchases(Connection c) throws Exception  {
        c.createStatement().executeUpdate("INSERT INTO PurchasesProducts (purchaseId, productId) VALUES (0, 0)");
        c.createStatement().executeUpdate("INSERT INTO PurchasesProducts (purchaseId, productId) VALUES (0, 1)");
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:mem:stock", "SA", "");
    }
}