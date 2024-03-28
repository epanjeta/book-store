package ba.unsa.etf.nbp.bookstorebackend.statements;

import ba.unsa.etf.nbp.bookstorebackend.database.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Repository
public class UserStatements {

    @Autowired
    protected DatabaseService databaseService;

    public UserStatements() {
    }

    public ResultSet findAllUsers(int id){
        String sql = "SELECT usr.ID, usr.FIRST_NAME, usr.LAST_NAME, usr.EMAIL, usr.USERNAME, usr.BIRTH_DATE, rle.NAME, usr.ADDRESS_ID FROM NBP.NBP_USER usr INNER JOIN NBP.NBP_ROLE rle ON usr.ROLE_ID = rle.ID WHERE usr.ROLE_ID =?;";
        try {
            Connection connection = databaseService.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
