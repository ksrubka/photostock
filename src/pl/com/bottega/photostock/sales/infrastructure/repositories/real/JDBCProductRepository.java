package pl.com.bottega.photostock.sales.infrastructure.repositories.real;

import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ProductRepository;
import pl.com.bottega.photostock.sales.model.Money;
import pl.com.bottega.photostock.sales.model.Product;
import pl.com.bottega.photostock.sales.model.exceptions.DataAccessException;
import pl.com.bottega.photostock.sales.model.products.Picture;

import java.sql.*;
import java.util.List;
import java.util.Set;

/**
 * Created by Beata Iłowiecka on 29.05.2016.
 */
public class JDBCProductRepository implements ProductRepository {

    private final String url;
    private final String login;
    private final String pwd;

    public JDBCProductRepository(String url, String login, String pwd) {
        this.url = url;
        this.login = login;
        this.pwd = pwd;
    }

    @Override
    public Product load(String nr) {
        try (Connection c = DriverManager.getConnection(url, login, pwd)) {
            PreparedStatement statement = c.prepareStatement(
                    "SELECT number, priceCents, priceCurrency, available FROM Products WHERE number = ?" );
            statement.setString(1, nr);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new Picture(
                        rs.getString("number"),
                        new Money(rs.getInt("priceCents") / 100),
                        new String[]{},
                        rs.getBoolean("available"));
            }
        }
        catch (Exception e) {
            throw new DataAccessException(e);
        }
        return null;
    }

    @Override
    public void save(Product product) {
        try (Connection c = DriverManager.getConnection(url, login, pwd)) {
            PreparedStatement statement = c.prepareStatement(
                    "INSERT INTO Products (number, available, priceCents, priceCurrency, type) " +
                            "VALUES (?, ?, ?, ?, 'Picture')");

            statement.setString(1, product.getNumber());
            statement.setBoolean(2, product.isAvailable());
            statement.setDouble(3, product.getPrice().getDoubleValue());
            statement.setString(4, String.valueOf(product.getPrice().getCurrency()));


            ResultSet rs = statement.executeQuery();
        }
        catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public Set<Product> getProducts() {
        return null;
    }

    @Override
    public List<Product> find(String nameFragment, String[] tags, Money priceMin, Money priceMax, boolean acceptNotAvailable) {
        return null;
    }
}
