package ba.unsa.etf.nbp.bookstorebackend.builder;

import ba.unsa.etf.nbp.bookstorebackend.Role;
import ba.unsa.etf.nbp.bookstorebackend.constants.PublisherFields;
import ba.unsa.etf.nbp.bookstorebackend.constants.UserFields;
import ba.unsa.etf.nbp.bookstorebackend.projection.AddressProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.PublisherProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.UserProjection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class PublisherProjectionBuilder {
    private static Logger LOGGER = LoggerFactory.getLogger(UserProjectionBuilder.class);
    private static final String WARNING_MESSAGE = "Field %s is not present in the result set for publisher projection";

    private static PublisherProjectionBuilder builder_;
    private static PublisherProjection publisherProjection_;
    private static ResultSet resultSet_;

    public PublisherProjectionBuilder() {
    }

    public static PublisherProjectionBuilder create(ResultSet resultSet) {
        builder_ = new PublisherProjectionBuilder();
        publisherProjection_ = new PublisherProjection();
        resultSet_ = resultSet;

        return builder_;
    }

    public PublisherProjectionBuilder setId() {
        try {
            publisherProjection_.setId(resultSet_.getInt(PublisherFields.ID));
        } catch (SQLException e) {
            publisherProjection_.setId(-1);
            LOGGER.warn(String.format(WARNING_MESSAGE, PublisherFields.ID));
        }

        return builder_;
    }

    public PublisherProjectionBuilder setName() {
        try {
            publisherProjection_.setName(resultSet_.getString(PublisherFields.NAME));
        } catch (SQLException e) {
            publisherProjection_.setName(null);
            LOGGER.warn(String.format(WARNING_MESSAGE, PublisherFields.NAME));
        }

        return builder_;
    }

    public PublisherProjectionBuilder setEmail() {
        try {
            publisherProjection_.setEmail(resultSet_.getString(PublisherFields.EMAIL));
        } catch (SQLException e) {
            publisherProjection_.setEmail(null);
            LOGGER.warn(String.format(WARNING_MESSAGE, PublisherFields.EMAIL));
        }

        return builder_;
    }

    public PublisherProjectionBuilder setPhone() {
        try {
            publisherProjection_.setPhone(resultSet_.getString(PublisherFields.PHONE));
        } catch (SQLException e) {
            publisherProjection_.setPhone(null);
            LOGGER.warn(String.format(WARNING_MESSAGE, PublisherFields.PHONE));
        }

        return builder_;
    }

    public PublisherProjectionBuilder setAddress(AddressProjection address) {
        publisherProjection_.setAddressProjection(address);
        return builder_;
    }

    public PublisherProjection build() {
        return publisherProjection_;
    }
}
