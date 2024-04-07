package ba.unsa.etf.nbp.bookstorebackend.statements;

import ba.unsa.etf.nbp.bookstorebackend.constants.*;
import ba.unsa.etf.nbp.bookstorebackend.projection.OrderProjection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

}
