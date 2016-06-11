package pl.com.bottega.photostock.sales.infrastructure.repositories.real;

import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.PurchaseRepository;
import pl.com.bottega.photostock.sales.model.Purchase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

/**
 * Created by Beata Iłowiecka on 10.06.16.
 */
public class JDBCPurchaseRepository implements PurchaseRepository {

    private final String url;
    private final String login;
    private final String pwd;

    public JDBCPurchaseRepository(String url, String login, String pwd) {
        this.url = url;
        this.login = login;
        this.pwd = pwd;
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, login, pwd);
    }

    @Override
    public Purchase load(String number) {
        return null;
    }

    @Override
    public void save(Purchase purchase) {

    }

    @Override
    public Set<Purchase> getPurchases() {
        return null;
    }

    @Override
    public List<Purchase> find(String clientNr) {
        return null;
    }
}
