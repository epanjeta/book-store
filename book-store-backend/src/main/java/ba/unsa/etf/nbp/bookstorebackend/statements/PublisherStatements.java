package ba.unsa.etf.nbp.bookstorebackend.statements;

import ba.unsa.etf.nbp.bookstorebackend.Role;
import ba.unsa.etf.nbp.bookstorebackend.constants.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PublisherStatements {
    public PublisherStatements() {
    }

    public static ResultSet findAllPublishers(Connection connection) {
        try {
            String sql = "SELECT pbl.ID   as " + PublisherFields.ID + ",\n" +
                    "       pbl.NAME as " + PublisherFields.NAME + ",\n" +
                    "       pbl.EMAIL as " + PublisherFields.EMAIL + ",\n" +
                    "       pbl.PHONE_NUMBER as " + PublisherFields.PHONE + "\n" +
                    "FROM NBP24T3.NBP_PUBLISHER pbl";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            return preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static ResultSet findAllPublishersWithAddresses(Connection connection) {
        try {
            String sql = "SELECT pbl.ID   as " + PublisherFields.ID + ",\n" +
                    "       pbl.NAME as " + PublisherFields.NAME + ",\n" +
                    "       pbl.EMAIL as " + PublisherFields.EMAIL + ",\n" +
                    "       pbl.PHONE_NUMBER as " + PublisherFields.PHONE + ",\n" +
                    "       pbl.ADDRESS_ID as " + PublisherFields.ADDRESS_ID + ",\n" +
                    "       ADDS.STREET as " + AddressFields.STREET + ",\n" +
                    "       ADDS.ZIP_CODE as " + AddressFields.ZIP_CODE + ",\n" +
                    "       CITY.NAME as " + CityFields.NAME + ",\n" +
                    "       COUNTRY.NAME as " + CountryFields.NAME + "\n" +
                    "FROM NBP24T3.NBP_PUBLISHER pbl\n" +
                    "         LEFT JOIN NBP24T3.NBP_ADDRESS ADDS ON ADDS.ID = pbl.ADDRESS_ID\n" +
                    "         LEFT JOIN NBP24T3.NBP_CITY CITY ON ADDS.CITY_ID = CITY.ID\n" +
                    "         LEFT JOIN NBP24T3.NBP_COUNTRY COUNTRY ON CITY.COUNTRY_ID = COUNTRY.ID\n";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            return preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int createPublisher(Connection connection, String name, String email, String phoneNumber, Integer addressId) {
        String sql = "INSERT INTO NBP24T3.NBP_PUBLISHER (NAME, ADDRESS_ID, EMAIL, PHONE_NUMBER)\n" +
                "VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, addressId);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, phoneNumber);
            preparedStatement.executeUpdate();

            return CommonStatements.getCurrval(connection, PublisherFields.CURR_VAL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int updatePublisher(Connection connection, String name, String email, String phoneNumber, Integer addressId, Integer id) {
        String sql = "UPDATE NBP24T3.NBP_PUBLISHER " +
                "SET NAME = ?, PHONE_NUMBER = ?, EMAIL = ?, ADDRESS_ID = ? " +
                "WHERE ID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, phoneNumber);
            preparedStatement.setString(3, email);
            preparedStatement.setInt(4, addressId);
            preparedStatement.setInt(5, id);
            preparedStatement.executeUpdate();

            return id;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
