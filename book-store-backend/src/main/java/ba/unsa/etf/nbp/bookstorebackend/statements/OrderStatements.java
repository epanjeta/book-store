package ba.unsa.etf.nbp.bookstorebackend.statements;

import ba.unsa.etf.nbp.bookstorebackend.constants.*;
import ba.unsa.etf.nbp.bookstorebackend.projection.OrderProjection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class OrderStatements {
    public OrderStatements() {
    }

    public static ResultSet findAllOrdersForUser(Connection connection, int userId){
        try{
            String sql = "SELECT DISTINCT ordr.ID AS " + OrderFields.ID + ",\n"+
                    "ordr.USER_ID AS " + OrderFields.CUSTOMER_ID + ",\n"+
                    "ordr.CREATED_AT AS " + OrderFields.CREATED_AT + ",\n"+
                    "ordr.ORDER_DATE AS " + OrderFields.ORDER_DATE + ",\n"+
                    "ordr.TOTAL AS " + OrderFields.TOTAL + ",\n"+
                    "ordr.ORDER_DATE AS " + OrderFields.ORDER_DATE + ",\n"+
                    "ordr.STATUS AS " + OrderFields.STATUS + ",\n"+
                    "ordr.ORDER_DATE AS " + OrderFields.ORDER_DATE + ",\n"+
                    "ordr.PAYMENT_METHOD AS " + OrderFields.PAYMENT_METHOD + ",\n"+
                    "       ADDS.ID as " + AddressFields.ID + ",\n" +
                    "       ADDS.STREET as " + AddressFields.STREET + ",\n" +
                    "       ADDS.ZIP_CODE as " + AddressFields.ZIP_CODE + ",\n" +
                    "       CITY.NAME as " + CityFields.NAME + ",\n" +
                    "       COUNTRY.NAME as " + CountryFields.NAME + ",\n" +
                    "b2o.QUANTITY \n"+
                    "FROM NBP24T3.NBP_ORDER ordr\n" +
                    "INNER JOIN NBP24T3.NBP_BOOK2ORDER b2o on b2o.ORDER_ID = ordr.ID \n"+
                    "INNER JOIN NBP24T3.NBP_ADDRESS ADDS on ordr.SHIPPING_ADDRESS = ADDS.ID \n"+
                    "         Inner JOIN NBP24T3.NBP_CITY CITY ON ADDS.CITY_ID = CITY.ID\n" +
                    "         Inner JOIN NBP24T3.NBP_COUNTRY COUNTRY ON CITY.COUNTRY_ID = COUNTRY.ID\n"+
                    "WHERE ordr.USER_ID = " + userId
                    ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            return preparedStatement.executeQuery();
        }
         catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static int createNewOrder(Connection connection, OrderForm orderForm) {
        try{
            String insertSql = "INSERT INTO NBP_ORDER (USER_ID, CREATED_AT, ORDER_DATE, TOTAL, SHIPPING_ADDRESS, STATUS, PAYMENT_METHOD) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setInt(1, orderForm.getUserId());
            preparedStatement.setDate(2, orderForm.getCreatedAt());
            preparedStatement.setDate(3, orderForm.getOrderAt());
            preparedStatement.setDouble(4, orderForm.getTotal());
            preparedStatement.setInt(5, orderForm.getShippingAddressId());
            preparedStatement.setString(6, orderForm.getStatus()); //da li moze
            preparedStatement.setString(7, orderForm.getPaymentMethod());
            preparedStatement.executeUpdate();

            return CommonStatements.getCurrval(connection, OrderFields.CURR_VAL);

        }

        catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static int createBookToOrder(Connection connection, int createdOrderId, Map<Integer, Integer> bookQuantity) {
        try{
            int rowsAffected = 0;
            for(Integer bookId: bookQuantity.keySet()){
                String sql = "INSERT INTO NBP_BOOK2ORDER (ORDER_ID, BOOK_ID, QUANTITY) VALUES (?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, createdOrderId);
                preparedStatement.setInt(2, bookId);
                preparedStatement.setInt(3, bookQuantity.get(bookId));
                rowsAffected += preparedStatement.executeUpdate();
            }
            return rowsAffected;
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
