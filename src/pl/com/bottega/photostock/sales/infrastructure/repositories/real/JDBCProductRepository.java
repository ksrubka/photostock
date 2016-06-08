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
                    "SELECT number, priceCents, priceCurrency, available FROM Products WHERE number = ?;" );
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
            String insertQuery = "INSERT INTO Products (number, name, available, priceCents, priceCurrency, type) " +
                            "VALUES (?, ?, ?, ?, ?, 'Picture');";
            String updateQuery = "UPDATE Products SET number=?, name=?, available=?, priceCents=?, priceCurrency=?, type='Picture'" +
                    "WHERE number=?;";
            Boolean needToInsertNewProduct = shouldInsert(product);
            String query = (needToInsertNewProduct) ? insertQuery : updateQuery;
            PreparedStatement s = c.prepareStatement(query);
            if (needToInsertNewProduct)
                setValues1(s, product);
            else
                setValues2(s, product);
            s.execute();
            insertTags(c, product);
        }
        catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    private Boolean shouldInsert(Product product) {
        Product dbProduct = load(product.getNumber());
        return  !(product.equals(dbProduct)) || dbProduct == null;
    }

    private void setValues1(PreparedStatement s, Product product) throws Exception {
        s.setString(1, product.getNumber());
        s.setString(2, "jakieś imię");
        s.setBoolean(3, product.isAvailable());
        s.setInt(4, product.getPrice().cents());
        s.setString(5, String.valueOf(product.getPrice().getCurrency()));
    }

    private void setValues2(PreparedStatement s, Product product) throws Exception {
        setValues1(s, product);
        s.setString(6, product.getNumber());
    }

    //insert
    private void insertTags(Connection c, Product product) throws Exception {
        if (product instanceof Picture) {
            Picture picture = (Picture) product;
            String[] pictureTags = picture.getTags();
            if (pictureTags.length==0)
                return;
            //wszystkie tagi zdjęcia wyszukujemy w bazie danych i wrzucamy do tabeli rs
            ResultSet rs = queryTags(c, pictureTags);
            //??
            Set<String> existingTags = new HashSet<>();
            //do existingTags dodajemy tagi z powyższej tabeli (czyli wszystkie tagi zdjęcia które są już i w bazie i w zdjęciu)
            while(rs.next())
                existingTags.add(rs.getString("name"));
            for (String tag : pictureTags) {
                //jeśli któryś z tagów zdjęcia nie jest jeszcze w bazie
                if (!existingTags.contains(tag))
                    //dodaj go do bazy
                    insertTag(c, tag);
            }
            linkTags(c, (Picture) product);
        }
    }

    private ResultSet queryTags(Connection c, String[] tags) throws Exception {
        String questionMarksConcat = createQuestionMarksForQuery(tags.length);
        PreparedStatement s = c.prepareStatement(
                "SELECT id, name FROM Tags " +
                        "WHERE name IN (" + questionMarksConcat +");");
        setValuesForQuery(s, tags);
        return s.executeQuery();
    }

        private String createQuestionMarksForQuery(int nrOfTags) {
            String[] questionMarks =
                    new String[nrOfTags];
            for(int i = 0; i < questionMarks.length; i++)
                questionMarks[i] = "?";
            return String.join(",", questionMarks);
        }

        private void setValuesForQuery(PreparedStatement s, String[] tags) throws Exception {
            for (int i = 1; i<=tags.length; i++)
                s.setString(i, tags[i-1]);
        }

    //wstaw nowy tag do tabeli Tags
    private void insertTag(Connection c, String tag) throws Exception {
        PreparedStatement s = c.prepareStatement("INSERT INTO Tags (name) VALUES (?);");
        s.setString(1, tag);
        s.executeUpdate();
    }

    private void linkTags(Connection c, Picture picture) throws Exception {
        int pictureId = getPictureId(c, picture);
        ResultSet rs = queryTags(c, picture.getTags());
        Set<Integer> pictureTagIds = new HashSet<>();
        while (rs.next())
            pictureTagIds.add(rs.getInt("id"));

        //select który wyciąga istniejące połączenia z productTags
        rs = queryProductsTags(c, pictureId);
        Set<Integer> currentTagIds = new HashSet<>();
        while (rs.next())
            currentTagIds.add(rs.getInt("tagId"));

        //iterowanie po wyniku żeby stwierdzić...
        for (Integer newTagId : pictureTagIds)
            if (!currentTagIds.contains(newTagId))
                //...co trzeba dodać
                linkTag(c, pictureId,  newTagId);

        for (Integer oldTagId : currentTagIds)
            if (!pictureTagIds.contains(oldTagId))
                //...a co usunąć
                unlinkTag(c, pictureId,  oldTagId);
    }

        private int getPictureId(Connection c, Picture picture) throws Exception {
            PreparedStatement s = c.prepareStatement("SELECT id FROM Products WHERE number=?;");
            s.setString(1, picture.getNumber());
            ResultSet rs = s.executeQuery();
            rs.next();
            return rs.getInt("id");
        }

        private ResultSet queryProductsTags(Connection c, int productId) throws Exception {
            PreparedStatement s = c.prepareStatement(
                    "SELECT tagId FROM ProductsTags " +
                            "WHERE productId=?;");
            s.setInt(1, productId);
            return s.executeQuery();
        }

        private void linkTag(Connection c, int productId, Integer tagId) throws Exception {
            PreparedStatement s = c.prepareStatement("INSERT INTO ProductsTags (productId, tagId) VALUES (?, ?);");
            s.setInt(1, productId);
            s.setInt(2, tagId);
            s.executeUpdate();
        }

        private void unlinkTag(Connection c, int productId, Integer oldTagId) throws Exception {
            PreparedStatement s = c.prepareStatement("DELETE FROM ProductsTags WHERE productId=? AND tagId=?;");
            s.setInt(1, productId);
            s.setInt(2, oldTagId);
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