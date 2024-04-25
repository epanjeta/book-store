package ba.unsa.etf.nbp.bookstorebackend.statements;

import ba.unsa.etf.nbp.bookstorebackend.constants.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CartItemStatements {
    public CartItemStatements() {
    }

    public static ResultSet getCartForUser(Connection connection, int userId){
        try{
            String sql = "SELECT b.ID         AS " + BookFields.ID + ",\n" +
                    "       b.ISBN AS " + BookFields.ISBN + ",\n" +
                    "       b.TITLE  AS " + BookFields.TITLE + ",\n" +
                    "       b.DESCRIPTION AS " + BookFields.DESCRIPTION + ",\n" +
                    "       b.PUBLICATION_DATE AS " + BookFields.PUBLICATION_DATE + ",\n" +
                    "       b.PRICE          AS " + BookFields.PRICE + ",\n" +
                    "       b.STOCK        AS " + BookFields.STOCK + ",\n" +
                    "       pub.ID        AS " + PublisherFields.ID + ",\n" +
                    "       pub.NAME        AS " + PublisherFields.NAME+ ",\n" +
                    "       b.LANGUAGE_CODE        AS " + BookFields.LANGUAGE_CODE + ",\n" +
                    "       img.ID        AS " + ImageFields.ID + ",\n" +
                    "       img.NAME        AS " + ImageFields.NAME+ ",\n" +
                    "       auth.ID        AS " + AuthorFields.ID + ",\n" +
                    "       auth.FIRST_NAME        AS " + AuthorFields.FIRST_NAME + ",\n" +
                    "       auth.LAST_NAME        AS " + AuthorFields.LAST_NAME + ",\n" +
                    "       g.GENRE        AS " + BookFields.GENRE + ",\n" +
                    "       crt.QUANTITY AS " + CartItemFields.QUANTITY + "\n"+
                    "FROM NBP24T3.NBP_BOOK b\n" +
                    "         INNER JOIN NBP24T3.NBP_CART crt on crt.BOOK_ID = B.ID"+
                    "         INNER JOIN NBP24T3.NBP_PUBLISHER pub on pub.ID = b.PUBLISHERID" +
                    "         INNER JOIN NBP24T3.NBP_IMAGE img on img.ID = b.IMAGE_ID"+
                    "         INNER JOIN NBP24T3.NBP_BOOK2AUTHOR ba on ba.BOOK_ID = b.ID"+
                    "         INNER JOIN NBP24T3.NBP_AUTHOR auth on ba.AUTHOR_ID = auth.ID"+
                    "         INNER JOIN NBP24T3.NBP_BOOK2GENRE bg on bg.BOOK_ID = b.ID"+
                    "         INNER JOIN NBP24T3.NBP_GENRE g on bg.GENRE = g.GENRE"+
                    "         WHERE crt.USER_ID = " + userId;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            return preparedStatement.executeQuery();
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static int addToCart(Connection connection, int userId, int bookId, int quantity) {

        try {
            String insertSql = "INSERT INTO NBP24T3.NBP_CART (BOOK_ID, USER_ID, QUANTITY) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setInt(1, bookId);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, quantity);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}