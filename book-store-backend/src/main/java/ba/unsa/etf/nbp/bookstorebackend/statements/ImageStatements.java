package ba.unsa.etf.nbp.bookstorebackend.statements;

import ba.unsa.etf.nbp.bookstorebackend.constants.ImageFields;
import ba.unsa.etf.nbp.bookstorebackend.constants.PublisherFields;

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
}
