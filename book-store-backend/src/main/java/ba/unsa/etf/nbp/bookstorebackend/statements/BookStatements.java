package ba.unsa.etf.nbp.bookstorebackend.statements;

import ba.unsa.etf.nbp.bookstorebackend.constants.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BookStatements  {
    public BookStatements() {
    }

    public static ResultSet findAllBooks(Connection connection) {
        String sql =
                "SELECT b.ID         AS " + BookFields.ID + ",\n" +
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
                        "       g.GENRE        AS " + BookFields.GENRE + "\n" +
                        "FROM NBP24T3.NBP_BOOK b\n" +
                        "         INNER JOIN NBP24T3.NBP_PUBLISHER pub on pub.ID = b.PUBLISHERID" +
                        "         INNER JOIN NBP24T3.NBP_IMAGE img on img.ID = b.IMAGE_ID"+
                        "         INNER JOIN NBP24T3.NBP_BOOK2AUTHOR ba on ba.BOOK_ID = b.ID"+
                        "         INNER JOIN NBP24T3.NBP_AUTHOR auth on ba.AUTHOR_ID = auth.ID"+
                        "         INNER JOIN NBP24T3.NBP_BOOK2GENRE bg on bg.BOOK_ID = b.ID"+
                        "         INNER JOIN NBP24T3.NBP_GENRE g on bg.GENRE = g.GENRE";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            return preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static ResultSet findAllBooksForOrder(Connection connection, int orderId) {
        String sql =
                "SELECT b.ID         AS " + BookFields.ID + ",\n" +
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
                        "       b2o.QUANTITY \n" +
                        "FROM NBP24T3.NBP_BOOK b\n" +
                        "         INNER JOIN NBP24T3.NBP_PUBLISHER pub on pub.ID = b.PUBLISHERID" +
                        "         INNER JOIN NBP24T3.NBP_IMAGE img on img.ID = b.IMAGE_ID"+
                        "         INNER JOIN NBP24T3.NBP_BOOK2AUTHOR ba on ba.BOOK_ID = b.ID"+
                        "         INNER JOIN NBP24T3.NBP_AUTHOR auth on ba.AUTHOR_ID = auth.ID"+
                        "         INNER JOIN NBP24T3.NBP_BOOK2GENRE bg on bg.BOOK_ID = b.ID"+
                        "         INNER JOIN NBP24T3.NBP_GENRE g on bg.GENRE = g.GENRE" +
                        "         INNER JOIN NBP24T3.NBP_BOOK2ORDER b2o on b2o.BOOK_ID = B.ID"+
                        "         WHERE b2o.ORDER_ID = " + orderId;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            return preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
