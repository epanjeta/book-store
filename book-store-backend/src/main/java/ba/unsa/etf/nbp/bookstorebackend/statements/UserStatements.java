package ba.unsa.etf.nbp.bookstorebackend.statements;

import ba.unsa.etf.nbp.bookstorebackend.Role;
import ba.unsa.etf.nbp.bookstorebackend.constants.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class UserStatements {

    private UserStatements() {
    }

    public static ResultSet findAllUsers(Connection connection) {
        try {
            String sql = "SELECT usr.ID   as " + UserFields.ID + ",\n" +
                    "       usr.FIRST_NAME as " + UserFields.FIRST_NAME + ",\n" +
                    "       usr.LAST_NAME as " + UserFields.LAST_NAME + ",\n" +
                    "       usr.EMAIL as " + UserFields.EMAIL + ",\n" +
                    "       usr.USERNAME as " + UserFields.USERNAME + ",\n" +
                    "       usr.BIRTH_DATE as " + UserFields.BIRTH_DATE + ",\n" +
                    "       rle.NAME as " + RoleFields.NAME + "\n" +
                    "FROM NBP.NBP_USER usr\n" +
                    "         INNER JOIN NBP.NBP_ROLE rle ON usr.ROLE_ID = rle.ID\n" +
                    "WHERE rle.NAME = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Role.BOOK_BUYER.name());
            return preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static ResultSet findAllUsersWithAddresses(Connection connection, Role role) {
        try {
            String sql = "SELECT usr.ID   as " + UserFields.ID + ",\n" +
                    "       usr.FIRST_NAME as " + UserFields.FIRST_NAME + ",\n" +
                    "       usr.LAST_NAME as " + UserFields.LAST_NAME + ",\n" +
                    "       usr.EMAIL as " + UserFields.EMAIL + ",\n" +
                    "       usr.USERNAME as " + UserFields.USERNAME + ",\n" +
                    "       usr.BIRTH_DATE as " + UserFields.BIRTH_DATE + ",\n" +
                    "       rle.NAME as " + RoleFields.NAME + ",\n" +
                    "       ADDS.ID as " + AddressFields.ID + ",\n" +
                    "       ADDS.STREET as " + AddressFields.STREET + ",\n" +
                    "       ADDS.ZIP_CODE as " + AddressFields.ZIP_CODE + ",\n" +
                    "       CITY.NAME as " + CityFields.NAME + ",\n" +
                    "       COUNTRY.NAME as " + CountryFields.NAME + "\n" +
                    "FROM NBP.NBP_USER usr\n" +
                    "         INNER JOIN NBP.NBP_ROLE rle ON usr.ROLE_ID = rle.ID\n" +
                    "         LEFT JOIN NBP24T3.NBP_ADDRESS ADDS ON ADDS.ID = usr.ID\n" +
                    "         LEFT JOIN NBP24T3.NBP_CITY CITY ON ADDS.CITY_ID = CITY.ID\n" +
                    "         LEFT JOIN NBP24T3.NBP_COUNTRY COUNTRY ON CITY.COUNTRY_ID = COUNTRY.ID\n" +
                    "WHERE rle.NAME = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Role.BOOK_BUYER.name());
            return preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static ResultSet findUserWithId(Connection connection, int userId){
        try{
            String sql = "SELECT usr.ID   as " + UserFields.ID + ",\n" +
                    "       usr.FIRST_NAME as " + UserFields.FIRST_NAME + ",\n" +
                    "       usr.LAST_NAME as " + UserFields.LAST_NAME + "\n" +
                    "FROM NBP.NBP_USER usr\n" +
                    "WHERE usr.ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userId);
            return preparedStatement.executeQuery();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

}
