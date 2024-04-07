package ba.unsa.etf.nbp.bookstorebackend.statements;

import ba.unsa.etf.nbp.bookstorebackend.constants.AddressFields;
import ba.unsa.etf.nbp.bookstorebackend.constants.CityFields;
import ba.unsa.etf.nbp.bookstorebackend.constants.CountryFields;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CommonStatements {

    private CommonStatements() {}

    public static Integer getCurrval(Connection connection, String name) {
        String sql = "select " + name + " from DUAL" ;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

            throw new RuntimeException("NO CURVALL DEFINED FOR: " + name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
