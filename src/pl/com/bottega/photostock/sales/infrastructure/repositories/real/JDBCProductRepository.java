package pl.com.bottega.photostock.sales.infrastructure.repositories.real;

import pl.com.bottega.photostock.sales.infrastructure.repositories.interfaces.ProductRepository;
import pl.com.bottega.photostock.sales.model.Money;
import pl.com.bottega.photostock.sales.model.Product;
import pl.com.bottega.photostock.sales.model.exceptions.DataAccessException;
import pl.com.bottega.photostock.sales.model.products.Picture;

import java.sql.*;
import java.util.HashSet;
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
                        new Money((rs.getInt("priceCents") / 100), rs.getString("priceCurrency")),
                        loadTags(c, nr),
                        rs.getBoolean("available"));
            }
        }
        catch (Exception e) {
            throw new DataAccessException(e);
        }
        return null;
    }

    private String[] loadTags(Connection c, String productNr) throws Exception {
        PreparedStatement s = c.prepareStatement(
                "SELECT  Tags.name FROM Tags\n" +
                        "JOIN ProductsTags ON Tags.id = ProductsTags.tagId\n" +
                        "JOIN  Products ON ProductsTags.productId = Products.id\n" +
                        "WHERE Products.number=?;");
        s.setString(1, productNr);
        ResultSet rs = s.executeQuery();
        Set<String> tags = new HashSet<>();
        while (rs.next())
            tags.add(rs.getString("name"));
        return tags.toArray(new String[0]);
    }

    @Override
    public void save(Product product) {
        try (Connection c = DriverManager.getConnection(url, login, pwd)) {
            PreparedStatement statement = c.prepareStatement(
                    "INSERT INTO Products (number, name, available, priceCents, priceCurrency, type) " +
                            "VALUES (?, ?, ?, ?, ?, 'Picture')");
            statement.setString(1, product.getNumber());
            statement.setString(2, "jakieś imię");
            statement.setBoolean(3, product.isAvailable());
            statement.setInt(4, product.getPrice().cents());
            statement.setString(5, String.valueOf(product.getPrice().getCurrency()));
            statement.executeUpdate();
            insertTags(c, product);
        }
        catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    private void insertTags(Connection c, Product product) throws Exception {
        if (product instanceof Picture) {
            Picture picture = (Picture) product;
            String[] tags = picture.getTags();
            if (tags.length==0)
                return;
            ResultSet rs = queryTags(c, tags);
            Set<String> existingTags = new HashSet<>();
            while(rs.next())
                existingTags.add(rs.getString("name"));
            for (String tag : tags) {
                if (!existingTags.contains(tag))
                    insertTag(c, tag);
            }
            linkTags(c, (Picture) product);
        }
    }

    private ResultSet queryTags(Connection c, String[] tags) throws Exception {
        String[] questionMarks = new String[tags.length];
        for(int i = 0; i < questionMarks.length; i++)
            questionMarks[i] = "?";
        String questionMarksConcat = String.join(",", questionMarks);
        PreparedStatement s = c.prepareStatement(
                "SELECT id, name FROM Tags " +
                        "WHERE name IN (" + questionMarksConcat +");");
        for (int i = 1; i<=tags.length; i++)
            s.setString(i, tags[i-1]);
        return s.executeQuery();
    }

    private void insertTag(Connection c, String tag) throws Exception {
        PreparedStatement s = c.prepareStatement("INSERT INTO Tags (name) VALUES (?)");
        s.setString(1, tag);
        s.executeUpdate();
    }
    private void linkTags(Connection c, Picture product) throws Exception {
        PreparedStatement s = c.prepareStatement("SELECT id FROM Products WHERE number=?");
        s.setString(1, product.getNumber());
        ResultSet rs = s.executeQuery();
        rs.next();
        int productId = rs.getInt("id");
        rs = queryTags(c, product.getTags());
        Set<Integer> tagIds = new HashSet<>();
        while (rs.next()) {
            tagIds.add(rs.getInt("id"));
        }
        //todo sprawdzić istniejące połączenia i dodać tylko nowe albo usunąć niepotrzebne stare połączenia
        //todo select ktory wyciąga istniejące połączenia z productTags
        //todo iterowanie po wyniku żeby stwierdzić co trzeba dodać a co usunąć

        //todo usunąć niepotrzebne połączenia
        for (Integer tagId : tagIds)
        //if połącznie nie istnieje
            linkTag(c, productId, tagId);
    }

    private void linkTag(Connection c, int productId, Integer tagId) throws Exception {
        PreparedStatement s = c.prepareStatement("INSERT INTO ProductsTags (productId, tagId) VALUES (?, ?)");
        s.setInt(1, productId);
        s.setInt(2, tagId);
        s.executeUpdate();
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