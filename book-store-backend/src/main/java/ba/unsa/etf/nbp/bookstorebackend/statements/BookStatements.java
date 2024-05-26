package ba.unsa.etf.nbp.bookstorebackend.statements;

import ba.unsa.etf.nbp.bookstorebackend.constants.*;
import ba.unsa.etf.nbp.bookstorebackend.projection.AuthorProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.BookProjection;

import java.sql.*;
import java.util.List;

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
                        "       img.PHOTO        AS " + ImageFields.PHOTO+ ",\n" +
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

    public static ResultSet findBookWithId(Connection connection, int bookId){

        try{
            String sql =
                "SELECT b.ID         AS " + BookFields.ID + ",\n" +
                        "       b.TITLE  AS " + BookFields.TITLE + "\n" +
                        "FROM NBP24T3.NBP_BOOK b\n" +
                        "WHERE b.ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bookId);
            return preparedStatement.executeQuery();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet findBookWithIdAllInfo(Connection connection, int bookId){
        try{
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
                            "       img.PHOTO        AS " + ImageFields.PHOTO+ ",\n" +
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
                            "         INNER JOIN NBP24T3.NBP_GENRE g on bg.GENRE = g.GENRE " +
                            "WHERE b.ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bookId);
            return preparedStatement.executeQuery();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static int createNewBook(Connection connection, BookProjection book) {
        try{

            //for(String genre : book.getGenres())
                String sql = "INSERT INTO NBP24T3.NBP_BOOK (ISBN, TITLE, LANGUAGE_CODE, DESCRIPTION, PUBLICATION_DATE, PRICE, IMAGE_ID, STOCK, PUBLISHERID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, book.getISBN());
                preparedStatement.setString(2, book.getTitle());
                preparedStatement.setString(3, book.getLanguageCode());
                preparedStatement.setString(4, book.getDescription());
                preparedStatement.setDate(5, new Date(book.getPublicationDate().getYear(), book.getPublicationDate().getMonthValue(), book.getPublicationDate().getDayOfMonth()));
                preparedStatement.setDouble(6, book.getPrice());
                preparedStatement.setInt(7, book.getImageProjection().getId());
                preparedStatement.setDouble(8, book.getStock());
                preparedStatement.setInt(9, book.getPublisherProjection().getId());

                int rowsAffected = preparedStatement.executeUpdate();

            return CommonStatements.getCurrval(connection, BookFields.CURR_VAL);

        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static int fillBook2Author(Connection connection, List<AuthorProjection> authors, int bookId) {
        try{
            int rowsAffected = 0;
            for(AuthorProjection author : authors){
                String sql = "INSERT INTO NBP24T3.NBP_BOOK2AUTHOR(AUTHOR_ID, BOOK_ID) VALUES(?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, author.getId());
                preparedStatement.setInt(2, bookId);
                rowsAffected += preparedStatement.executeUpdate();

            }
            return rowsAffected;

        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static int fillBook2Genre(Connection connection, List<String> genres, int bookId) {
        try{
            int rowsAffected = 0;
            for(String genre: genres){
                String sql = "INSERT INTO NBP24T3.NBP_BOOK2GENRE(GENRE, BOOK_ID) VALUES(?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, genre);
                preparedStatement.setInt(2, bookId);
                rowsAffected += preparedStatement.executeUpdate();

            }
           return rowsAffected;

        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}
