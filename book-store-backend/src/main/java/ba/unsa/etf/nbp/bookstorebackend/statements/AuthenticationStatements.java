package ba.unsa.etf.nbp.bookstorebackend.statements;

import ba.unsa.etf.nbp.bookstorebackend.Role;
import ba.unsa.etf.nbp.bookstorebackend.constants.AddressFields;
import ba.unsa.etf.nbp.bookstorebackend.constants.RoleFields;
import ba.unsa.etf.nbp.bookstorebackend.constants.UserFields;
import ba.unsa.etf.nbp.bookstorebackend.projection.UserProjection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AuthenticationStatements {

    private AuthenticationStatements(){

    }

    public static ResultSet authenticate(Connection connection, String email){
        String sql = "SELECT usr.ID as " + UserFields.ID + ",\n" +
                "       usr.EMAIL as " + UserFields.EMAIL + ",\n" +
                "       usr.USERNAME as " + UserFields.USERNAME + ",\n" +
                "       usr.PASSWORD as " + UserFields.PASSWORD + ",\n" +
                "       rle.NAME as " + RoleFields.NAME + "\n" +
                "FROM NBP.NBP_USER usr\n" +
                "INNER JOIN NBP.NBP_ROLE rle ON usr.ROLE_ID = rle.ID\n" +
                "WHERE usr.EMAIL = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            return preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet getUserEmail(Connection connection, String email){
        String sql = "SELECT usr.ID as " + UserFields.ID + ",\n" +
                "       usr.EMAIL as " + UserFields.EMAIL + ",\n" +
                "       usr.USERNAME as " + UserFields.USERNAME + "\n" +
                "FROM NBP.NBP_USER usr\n" +
                "WHERE usr.EMAIL = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            return preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet getUserUserName(Connection connection, String username){
        String sql = "SELECT usr.ID as " + UserFields.ID + ",\n" +
                "       usr.EMAIL as " + UserFields.EMAIL + ",\n" +
                "       usr.USERNAME as " + UserFields.USERNAME + "\n" +
                "FROM NBP.NBP_USER usr\n" +
                "WHERE usr.USERNAME = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            return preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int createUser(Connection connection, UserProjection userProjection, Role role){
        String sql = "INSERT INTO NBP.NBP_USER (FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, USERNAME, PHONE_NUMBER, BIRTH_DATE, ROLE_ID)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userProjection.getFirstName());
            preparedStatement.setString(2, userProjection.getLastName());
            preparedStatement.setString(3, userProjection.getEmail());
            preparedStatement.setString(4, userProjection.getPassword());
            preparedStatement.setString(5, userProjection.getUserName());
            preparedStatement.setString(6, userProjection.getPhoneNumber());
            if(userProjection.getBirthDay() != null)
                preparedStatement.setDate(7, Date.valueOf(userProjection.getBirthDay()));
            else
                preparedStatement.setDate(7, null);
            preparedStatement.setInt(8, role.getDatabaseId());
            preparedStatement.executeUpdate();

            return CommonStatements.getCurrval(connection, UserFields.CURR_VAL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
