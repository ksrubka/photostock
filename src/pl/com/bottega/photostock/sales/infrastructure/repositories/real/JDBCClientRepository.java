package pl.com.bottega.photostock.sales.infrastructure.repositories.real;

import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ClientRepository;
import pl.com.bottega.photostock.sales.model.Client;
import pl.com.bottega.photostock.sales.model.ClientStatus;
import pl.com.bottega.photostock.sales.model.exceptions.DataAccessException;
import java.sql.*;

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

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, login, pwd);
    }

    @Override
    public Client load(String clientNumber) {
        try (Connection c = getConnection()) {
            PreparedStatement statement = c.prepareStatement(
                    "SELECT number, name, address, active, amount, currency, status FROM Clients WHERE number = ?");
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
        try (Connection c = getConnection()) {
            String insertQuery = "INSERT INTO Clients (number, name, address, active, amount, currency, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            String updateQuery = "UPDATE Clients SET number=?, name=?, address=?, active=?, amount=?, currency=?, status=?" +
                    " WHERE number=?";
            String  query = load(client.getNumber()) == null ? insertQuery : updateQuery;
            PreparedStatement s = c.prepareStatement(query);
            if (query.equals(insertQuery))
                setValues1(s, client);
            else
                setValues2(s, client);
            s.execute();
        }
        catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    private void setValues1(PreparedStatement statement, Client client) throws SQLException {
        statement.setString(1, client.getNumber());
        statement.setString(2, client.getName());
        statement.setString(3, client.getAddress());
        statement.setBoolean(4, client.isActive());
        statement.setInt(5, client.getSaldo().cents()/100);
        statement.setString(6, String.valueOf(client.getSaldo().getCurrency()));
        statement.setString(7,String.valueOf(client.getStatus()).toLowerCase());
    }

    private void setValues2(PreparedStatement statement, Client client) throws SQLException {
        setValues1(statement, client);
        statement.setString(8, client.getNumber());
    }
}