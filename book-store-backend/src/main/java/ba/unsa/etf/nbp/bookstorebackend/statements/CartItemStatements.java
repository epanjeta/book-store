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
            String bookAlreadyInCartSql = "SELECT cart.BOOK_ID AS "+ BookFields.ID +",\n"+
                    "cart.USER_ID AS "+ UserFields.ID + ",\n"+
                    "cart.QUANTITY AS "+ CartItemFields.QUANTITY +"\n"+
                    "FROM NBP24T3.NBP_CART cart\n"+
                    "WHERE cart.BOOK_ID = ? AND cart.USER_ID = ?";
            PreparedStatement preparedStatementBookExist = connection.prepareStatement(bookAlreadyInCartSql);
            preparedStatementBookExist.setInt(1, bookId);
            preparedStatementBookExist.setInt(2, userId);
            ResultSet bookInCart =  preparedStatementBookExist.executeQuery();
            if(bookInCart.next()){
                //knjiga vec ima u cartu
                String updateCart = "UPDATE NBP24T3.NBP_CART SET QUANTITY = ? WHERE BOOK_ID = ? AND USER_ID = ?";
                PreparedStatement updateCartps = connection.prepareStatement(updateCart);
                updateCartps.setInt(1, quantity + bookInCart.getInt(3));
                updateCartps.setInt(2,bookId);
                updateCartps.setInt(3, userId);
               int rowsAffected =  updateCartps.executeUpdate();
               decreaseBookStock(connection, bookId, quantity + bookInCart.getInt(3));
               return rowsAffected;
            }
            else{
                //knjiga nema u cartu
                String insertSql = "INSERT INTO NBP24T3.NBP_CART (BOOK_ID, USER_ID, QUANTITY) VALUES (?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
                preparedStatement.setInt(1, bookId);
                preparedStatement.setInt(2, userId);
                preparedStatement.setInt(3, quantity);
                int rowsAffected =  preparedStatement.executeUpdate();
                decreaseBookStock(connection, bookId, quantity);
                return rowsAffected;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int deleteFromCart(Connection connection, int userId, int bookId){
        try{
            String quantitySql = "SELECT QUANTITY FROM NBP24T3.NBP_CART WHERE BOOK_ID = ? AND USER_ID = ?";
            PreparedStatement preparedStatementQuantity = connection.prepareStatement(quantitySql);
            preparedStatementQuantity.setInt(1, bookId);
            preparedStatementQuantity.setInt(2,userId);
            ResultSet resultSet = preparedStatementQuantity.executeQuery();
            resultSet.next();
            int quantity = resultSet.getInt("QUANTITY");
            increaseStock(connection, bookId,quantity);
            String sql = "DELETE FROM NBP24T3.NBP_CART WHERE BOOK_ID = ? AND USER_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bookId);
            preparedStatement.setInt(2, userId);
            return preparedStatement.executeUpdate();
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static int emptyCart(Connection connection, int userId){
        try{
            //fali dodavanje stock
            String cartSql = "SELECT QUANTITY, BOOK_ID FROM NBP24T3.NBP_CART WHERE USER_ID = ?";
            PreparedStatement preparedStatementcart = connection.prepareStatement(cartSql);
            preparedStatementcart.setInt(1, userId);
            ResultSet resultSetCart = preparedStatementcart.executeQuery();
            while(resultSetCart.next()){
                int bookId = resultSetCart.getInt("BOOK_ID");
                int quantity = resultSetCart.getInt("QUANTITY");
                increaseStock(connection, bookId, quantity);
            }

            String sql = "DELETE FROM NBP24T3.NBP_CART WHERE USER_ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            return preparedStatement.executeUpdate();
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    private static int decreaseBookStock(Connection connection, int bookId, int quantity){

        try {
            String sql = "SELECT book.STOCK AS " + BookFields.STOCK + "\n" +
                    " FROM NBP24T3.NBP_BOOK book\n" +
                    "WHERE book.ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int stock = resultSet.getInt("STOCK");
            String updateStockSql = "UPDATE NBP24T3.NBP_BOOK SET STOCK = ? WHERE ID = ?";
            PreparedStatement updateStock = connection.prepareStatement(updateStockSql);
            updateStock.setInt(1,stock-quantity);
            updateStock.setInt(2, bookId);
            return  updateStock.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }

    }
    private static int increaseStock(Connection connection, int bookId, int quantity){
        try {
            String sql = "SELECT book.STOCK AS " + BookFields.STOCK + "\n" +
                    " FROM NBP24T3.NBP_BOOK book\n" +
                    "WHERE book.ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int stock = resultSet.getInt("STOCK");
            String updateStockSql = "UPDATE NBP24T3.NBP_BOOK SET STOCK = ? WHERE ID = ?";
            PreparedStatement updateStock = connection.prepareStatement(updateStockSql);
            updateStock.setInt(1,stock+quantity);
            updateStock.setInt(2, bookId);
            return  updateStock.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}
