package ba.unsa.etf.nbp.bookstorebackend.statements;

import ba.unsa.etf.nbp.bookstorebackend.constants.AddressFields;
import ba.unsa.etf.nbp.bookstorebackend.constants.CityFields;
import ba.unsa.etf.nbp.bookstorebackend.constants.CountryFields;
import org.springframework.data.relational.core.sql.In;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AddressStatements {
    private AddressStatements() {
    }

    public static ResultSet findAllAddresses(Connection connection) {
        String sql = "select ADDS.ID as " + AddressFields.ID + ",\n" +
                "       ADDS.STREET as " + AddressFields.STREET + ",\n" +
                "       ADDS.ZIP_CODE as " + AddressFields.ZIP_CODE + ",\n" +
                "       CITY.ID as " + CityFields.ID + ",\n" +
                "       CITY.NAME as " + CityFields.NAME + ",\n" +
                "       COUNTRY.ID as " + CountryFields.ID + ",\n" +
                "       COUNTRY.NAME as " + CountryFields.NAME + "\n" +
                "FROM  NBP24T3.NBP_ADDRESS ADDS\n" +
                "         INNER JOIN NBP24T3.NBP_CITY CITY ON ADDS.CITY_ID = CITY.ID\n" +
                "         INNER JOIN NBP24T3.NBP_COUNTRY COUNTRY ON CITY.COUNTRY_ID = COUNTRY.ID\n";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            return preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet findAllCities(Connection connection) {
        String sql = "select ID as " + CityFields.ID + "," +
                "NAME as " + CityFields.NAME + "\n" +
                "from NBP24T3.NBP_CITY";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            return preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet findAllCountries(Connection connection) {
        String sql = "select ID as " + CountryFields.ID + "," +
                "NAME as " + CountryFields.NAME + "\n" +
                "from NBP24T3.NBP_COUNTRY";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            return preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int createCountry(Connection connection, String country) {
        String sql = "INSERT INTO NBP24T3.NBP_COUNTRY (NAME)\n" +
                "VALUES (?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, country);
            preparedStatement.executeUpdate();

            return CommonStatements.getCurrval(connection, CountryFields.CURR_VAL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int createCity(Connection connection, String city, int countryId) {
        String sql = "INSERT INTO NBP24T3.NBP_CITY (NAME, COUNTRY_ID)\n" +
                "VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, city);
            preparedStatement.setInt(2, countryId);
            preparedStatement.executeUpdate();

            return CommonStatements.getCurrval(connection, CityFields.CURR_VAL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int createAddress(Connection connection, String street, String zipCode, Integer cityId) {
        String sql = "INSERT INTO NBP24T3.NBP_ADDRESS (STREET, ZIP_CODE, CITY_ID)\n" +
                "VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, street);
            preparedStatement.setString(2, zipCode);
            preparedStatement.setInt(3, cityId);
            preparedStatement.executeUpdate();

            return CommonStatements.getCurrval(connection, AddressFields.CURR_VAL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int updateAddress(Connection connection, String street, String zipCode, Integer cityId, Integer id) {
        String sql = "UPDATE NBP24T3.NBP_ADDRESS SET STREET = ?, ZIP_CODE = ?, CITY_ID = ? \n" +
                "WHERE ID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, street);
            preparedStatement.setString(2, zipCode);
            preparedStatement.setInt(3, cityId);
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();

            return id;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
