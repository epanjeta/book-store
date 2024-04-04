package ba.unsa.etf.nbp.bookstorebackend.builder;

import ba.unsa.etf.nbp.bookstorebackend.constants.ImageFields;
import ba.unsa.etf.nbp.bookstorebackend.constants.PublisherFields;
import ba.unsa.etf.nbp.bookstorebackend.constants.UserFields;
import ba.unsa.etf.nbp.bookstorebackend.projection.ImageProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.PublisherProjection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ImageProjectionBuilder {
    private static Logger LOGGER = LoggerFactory.getLogger(ImageProjectionBuilder.class);
    private static final String WARNING_MESSAGE = "Field %s is not present in the result set for image projection";

    private static ImageProjectionBuilder builder_;
    private static ImageProjection imageProjection_;
    private static ResultSet resultSet_;

    public ImageProjectionBuilder() {
    }

    public static ImageProjectionBuilder create(ResultSet resultSet) {
        builder_ = new ImageProjectionBuilder();
        imageProjection_ = new ImageProjection();
        resultSet_ = resultSet;

        return builder_;
    }

    public ImageProjectionBuilder setId() {
        try {
            imageProjection_.setId(resultSet_.getInt(ImageFields.ID));
        } catch (SQLException e) {
            imageProjection_.setId(-1);
            LOGGER.warn(String.format(WARNING_MESSAGE, ImageFields.ID));
        }

        return builder_;
    }

    public ImageProjectionBuilder setName() {
        try {
            imageProjection_.setName(resultSet_.getString(ImageFields.NAME));
        } catch (SQLException e) {
            imageProjection_.setName(null);
            LOGGER.warn(String.format(WARNING_MESSAGE, ImageFields.NAME));
        }

        return builder_;
    }

    public ImageProjectionBuilder setPhoto() {
        try {
            imageProjection_.setPhoto(resultSet_.getBytes(ImageFields.PHOTO));
        } catch (SQLException e) {
            imageProjection_.setPhoto(null);
            LOGGER.warn(String.format(WARNING_MESSAGE, ImageFields.PHOTO));
        }

        return builder_;
    }

    public ImageProjection build() {
        return imageProjection_;
    }
}
