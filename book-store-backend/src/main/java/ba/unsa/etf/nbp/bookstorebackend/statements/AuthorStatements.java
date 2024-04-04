package ba.unsa.etf.nbp.bookstorebackend.statements;

import ba.unsa.etf.nbp.bookstorebackend.constants.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AuthorStatements {
    public AuthorStatements() {
    }

    public static ResultSet findAllAuthors(Connection connection) {
        String sql =
                "SELECT AUTH.ID         AS " + AuthorFields.ID + ",\n" +
                "       AUTH.FIRST_NAME AS " + AuthorFields.FIRST_NAME + ",\n" +
                "       AUTH.LAST_NAME  AS " + AuthorFields.LAST_NAME + ",\n" +
                "       AUTH.BIRTH_DATE AS " + AuthorFields.BIRTH_DATE + ",\n" +
                "       AUTH.DEATH_DATE AS " + AuthorFields.DEATH_DATE + ",\n" +
                "       NAT.ID          AS " + NationalityFields.ID + ",\n" +
                "       NAT.NAME        AS " + NationalityFields.NAME + ",\n" +
                "       AUTH.BIO        AS " + AuthorFields.BIO + "\n" +
                "FROM NBP24T3.NBP_AUTHOR AUTH\n" +
                "         INNER JOIN NBP24T3.NBP_NATIONALITY NAT on NAT.ID = AUTH.NATIONALITY_ID";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            return preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResultSet findAllNationalities(Connection connection) {
        String sql =
                "select ID as " + NationalityFields.ID +", NAME as " + NationalityFields.NAME + "\n" +
                        "from NBP24T3.NBP_NATIONALITY";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            return preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int createNationality(Connection connection, String nationality) {
        String sql = "INSERT INTO NBP24T3.NBP_NATIONALITY (NAME)\n" +
                "VALUES (?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nationality);
            preparedStatement.executeUpdate();

            return CommonStatements.getCurrval(connection, NationalityFields.CURR_VAL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int createAuthor(Connection connection, String first_name, String last_name, String birth_date, String death_date, Integer nationalityId, String bio) {
        String sql = "INSERT INTO NBP24T3.NBP_ADDRESS(FIRST_NAME, LAST_NAME, BIRTH_DATE, DEATH_DATE, NATIONALITY_ID, BIO)\n" +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, first_name);
            preparedStatement.setString(2, last_name);
            preparedStatement.setString(3, birth_date);
            preparedStatement.setString(4, death_date);
            preparedStatement.setInt(5, nationalityId);
            preparedStatement.setString(6, bio);
            preparedStatement.executeUpdate();

            return CommonStatements.getCurrval(connection, AuthorFields.CURR_VAL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
