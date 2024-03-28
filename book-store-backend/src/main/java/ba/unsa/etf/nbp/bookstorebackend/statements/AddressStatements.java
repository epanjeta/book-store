package ba.unsa.etf.nbp.bookstorebackend.statements;

import ba.unsa.etf.nbp.bookstorebackend.database.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Repository
public class AddressStatements {

    @Autowired
    protected DatabaseService databaseService;

    public AddressStatements() {
    }

    public ResultSet findById(int id){
        String sql = "SELECT ID, STREET, ZIP_CODE, CITY_ID FROM NBP_ADDRESS WHERE ID =?";
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
