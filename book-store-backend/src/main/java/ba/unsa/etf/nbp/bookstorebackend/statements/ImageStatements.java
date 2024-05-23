package ba.unsa.etf.nbp.bookstorebackend.statements;

import ba.unsa.etf.nbp.bookstorebackend.constants.CityFields;
import ba.unsa.etf.nbp.bookstorebackend.constants.ImageFields;
import ba.unsa.etf.nbp.bookstorebackend.constants.PublisherFields;
import ba.unsa.etf.nbp.bookstorebackend.projection.ImageProjection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ImageStatements {
    public ImageStatements() {
    }

    public static ResultSet findAllImages(Connection connection) {
        try {
            String sql = "SELECT ID   as " + ImageFields.ID + ",\n" +
                    "       NAME as " + ImageFields.NAME + ",\n" +
                    "       PHOTO as " + ImageFields.PHOTO + "\n" +
                    "FROM NBP24T3.NBP_IMAGE";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            return preparedStatement.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static int uploadImage(Connection connection, byte[] bytes) {
        try {
            String sql = "INSERT INTO NBP24T3.NBP_IMAGE (NAME, PHOTO)" +
                    "VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "Name");
            preparedStatement.setObject(2, bytes);
            preparedStatement.executeUpdate();

            return CommonStatements.getCurrval(connection, ImageFields.CURR_VAL);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
