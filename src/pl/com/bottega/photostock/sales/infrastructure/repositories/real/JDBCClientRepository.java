package pl.com.bottega.photostock.sales.infrastructure.repositories.real;

import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ClientRepository;
import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.ClientStatus;
import pl.com.bottega.photostock.sales.model.Money;
import pl.com.bottega.photostock.sales.model.exceptions.DataAccessException;
import pl.com.bottega.photostock.sales.model.products.Picture;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Beata Iłowiecka on 02.06.16.
 */
public class JDBCClientRepository implements ClientRepository {

    private final String url;
    private final String login;
    private final String pwd;

    public JDBCClientRepository(String url, String login, String pwd) {
        this.url = url;
        this.login = login;
        this.pwd = pwd;
    }

    @Override
    public Client load(String clientNumber) {
        try (Connection c = DriverManager.getConnection(url, login, pwd)) {
            PreparedStatement statement = c.prepareStatement(
                    "SELECT number, name, address, active, amount, currency, status FROM Clients WHERE number = ?" );
            statement.setString(1, clientNumber);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new Client(
                        rs.getString("name"),
                        rs.getString("address"),
                        ClientStatus.valueOf(rs.getString("status").toUpperCase()),
                        rs.getDouble("amount"),
                        rs.getString("currency"),
                        rs.getBoolean("active"),
                        rs.getString("number"));
            }
        }
        catch (Exception e) {
            throw new DataAccessException(e);
        }
        return null;
    }

    @Override
    public void save(Client client) {

    }
}
