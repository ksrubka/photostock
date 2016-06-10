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
    public Product load(String nr) throws DataAccessException {
        try (Connection c = createConnection()) {
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
        Set<String> tags = getTags(rs);
        return tags.toArray(new String[0]);
    }

    private Set<String> getTags(ResultSet rs) throws SQLException {
        Set<String> tags = new HashSet<>();
        while (rs.next())
            tags.add(rs.getString("name"));
        return tags;
    }

    @Override
    public void save(Product product) throws DataAccessException {
        try (Connection c = createConnection()) {
            Boolean needToInsertNewProduct = shouldInsert(product);
            if (needToInsertNewProduct)
                insert(c, product);
            else
                update(c, product);
            insertTags(c, product);
        }
        catch (Exception e) {
            throw new DataAccessException(e);
        }
    }

    private Boolean shouldInsert(Product product) {
        Product dbProduct = load(product.getNumber());
        return  product.equals(dbProduct) || dbProduct == null;
    }

    private void insert(Connection c, Product product) throws Exception {
        String insertQuery = "INSERT INTO Products (number, name, available, priceCents, priceCurrency, type) " +
                "VALUES (?, ?, ?, ?, ?, 'Picture');";
        PreparedStatement s = c.prepareStatement(insertQuery);
        setInsertValues(s, product);
        s.execute();
    }

    private void setInsertValues(PreparedStatement s, Product product) throws Exception {
        s.setString(1, product.getNumber());
        s.setString(2, "jakieś imię");
        s.setBoolean(3, product.isAvailable());
        s.setInt(4, product.getPrice().cents());
        s.setString(5, String.valueOf(product.getPrice().getCurrency()));
    }

    private void update(Connection c, Product product) throws Exception {
        String updateQuery = "UPDATE Products SET number=?, name=?, available=?, priceCents=?, priceCurrency=?, type='Picture'" +
                "WHERE number=?;";
        PreparedStatement s = c.prepareStatement(updateQuery);
        setUpdateValues(s, product);
        s.execute();
    }

    private void setUpdateValues(PreparedStatement s, Product product) throws Exception {
        setInsertValues(s, product);
        s.setString(6, product.getNumber());
    }

    private void insertTags(Connection c, Product product) throws Exception {
        if (product instanceof Picture) {
            Picture picture = (Picture) product;
            String[] pictureTags = picture.getTags();
            if (pictureTags.length==0)
                return;
            shouldInsertTag(c, pictureTags);
            linkTags(c, (Picture) product);
        }
    }

    private void shouldInsertTag(Connection c, String[] pictureTags) throws Exception {
        Set<String> existingTags = getExistingTags(c, pictureTags);
        for (String tag : pictureTags)
            if (!existingTags.contains(tag))
                insertTag(c, tag);
    }

    private Set<String> getExistingTags(Connection c, String[] pictureTags) throws Exception {
        ResultSet rs = queryTags(c, pictureTags);
        Set<String> existingTags = new HashSet<>();
        while(rs.next())
            existingTags.add(rs.getString("name"));
        return existingTags;
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
        for (int i = 1; i <= tags.length; i++)
            s.setString(i, tags[i-1]);
    }

    private void insertTag(Connection c, String tag) throws Exception {
        PreparedStatement s = c.prepareStatement("INSERT INTO Tags (name) VALUES (?);");
        s.setString(1, tag);
        s.executeUpdate();
    }

    private void linkTags(Connection c, Picture picture) throws Exception {
        int pictureId = getPictureId(c, picture);
        Set<Integer> pictureTagIds = getPictureTagIds(c, pictureId, picture);
        Set<Integer> currentTagIds = getCurrentTagIds(c, pictureId);
        addNewTags(c, pictureTagIds,currentTagIds, pictureId);
        deleteOldTags(c, pictureTagIds,currentTagIds, pictureId);
    }

    private int getPictureId(Connection c, Picture picture) throws Exception {
        PreparedStatement s = c.prepareStatement("SELECT id FROM Products WHERE number=?;");
        s.setString(1, picture.getNumber());
        ResultSet rs = s.executeQuery();
        rs.next();
        return rs.getInt("id");
    }

    private Set<Integer> getPictureTagIds(Connection c, int pictureId, Picture picture) throws Exception  {
        ResultSet rs = queryTags(c, picture.getTags());
        Set<Integer> pictureTagIds = new HashSet<>();
        while (rs.next())
            pictureTagIds.add(rs.getInt("id"));
        return pictureTagIds;
    }

    private Set<Integer> getCurrentTagIds(Connection c, int pictureId) throws Exception {
        ResultSet rs = queryProductsTags(c, pictureId);
        Set<Integer> currentTagIds = new HashSet<>();
        while (rs.next())
            currentTagIds.add(rs.getInt("tagId"));
        return currentTagIds;
    }

    private void addNewTags(Connection c,
                            Set<Integer> pictureTagIds,
                            Set<Integer> currentTagIds,
                            int pictureId) throws Exception {
        for (Integer newTagId : pictureTagIds)
            if (!currentTagIds.contains(newTagId))
                linkTag(c, pictureId,  newTagId);
    }

    private void deleteOldTags(Connection c,
                               Set<Integer> pictureTagIds,
                               Set<Integer> currentTagIds,
                               int pictureId) throws Exception {
        for (Integer oldTagId : currentTagIds)
            if (!pictureTagIds.contains(oldTagId))
                unlinkTag(c, pictureId,  oldTagId);
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

    public Connection createConnection() throws SQLException {
        return DriverManager.getConnection(url, login, pwd);
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