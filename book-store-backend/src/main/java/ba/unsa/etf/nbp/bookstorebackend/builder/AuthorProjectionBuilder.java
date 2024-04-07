package ba.unsa.etf.nbp.bookstorebackend.builder;

import ba.unsa.etf.nbp.bookstorebackend.constants.AddressFields;
import ba.unsa.etf.nbp.bookstorebackend.constants.AuthorFields;
import ba.unsa.etf.nbp.bookstorebackend.constants.NationalityFields;
import ba.unsa.etf.nbp.bookstorebackend.constants.UserFields;
import ba.unsa.etf.nbp.bookstorebackend.projection.AddressProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.AuthorProjection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class AuthorProjectionBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressProjectionBuilder.class);
    private static final String WARNING_MESSAGE = "Field %s is not present in the result set for author projection";

    private static AuthorProjectionBuilder builder_;
    private static AuthorProjection authorProjection_;
    private static ResultSet resultSet_;

    private AuthorProjectionBuilder() {

    }

    public static AuthorProjectionBuilder create(ResultSet resultSet) {
        builder_ = new AuthorProjectionBuilder();
        authorProjection_ = new AuthorProjection();
        resultSet_ = resultSet;

        return builder_;
    }

    public AuthorProjectionBuilder setId() {
        try {
            authorProjection_.setId(resultSet_.getInt(AuthorFields.ID));
        } catch (SQLException e) {
            authorProjection_.setId(-1);
            LOGGER.warn(String.format(WARNING_MESSAGE, AuthorFields.ID));
        }

        return builder_;
    }

    public AuthorProjectionBuilder setFirstName() {
        try {
            authorProjection_.setFirstName(resultSet_.getString(AuthorFields.FIRST_NAME));
        } catch (SQLException e) {
            authorProjection_.setFirstName(null);
            LOGGER.warn(String.format(WARNING_MESSAGE, AuthorFields.FIRST_NAME));
        }

        return builder_;
    }

    public AuthorProjectionBuilder setLastName() {
        try {
            authorProjection_.setLastName(resultSet_.getString(AuthorFields.LAST_NAME));
        } catch (SQLException e) {
            authorProjection_.setLastName(null);
            LOGGER.warn(String.format(WARNING_MESSAGE, AuthorFields.LAST_NAME));
        }

        return builder_;
    }

    public AuthorProjectionBuilder setBirthDate() {
        try {
            authorProjection_.setBirthDay(resultSet_.getObject(AuthorFields.BIRTH_DATE, LocalDate.class));
        } catch (SQLException e) {
            authorProjection_.setBirthDay(null);
            LOGGER.warn(String.format(WARNING_MESSAGE, AuthorFields.BIRTH_DATE));
        }

        return builder_;
    }

    public AuthorProjectionBuilder setDeathDate() {
        try {
            authorProjection_.setDeathDay(resultSet_.getObject(AuthorFields.DEATH_DATE, LocalDate.class));
        } catch (SQLException e) {
            authorProjection_.setDeathDay(null);
            LOGGER.warn(String.format(WARNING_MESSAGE, AuthorFields.DEATH_DATE));
        }

        return builder_;
    }

    public AuthorProjectionBuilder setNationalityId() {
        try {
            authorProjection_.setNationalityId(resultSet_.getInt(NationalityFields.ID));
        } catch (SQLException e) {
            authorProjection_.setNationalityId(null);
            LOGGER.warn(String.format(WARNING_MESSAGE, NationalityFields.ID));
        }

        return builder_;
    }

    public AuthorProjectionBuilder setNationalityName() {
        try {
            authorProjection_.setNationalityName(resultSet_.getString(NationalityFields.NAME));
        } catch (SQLException e) {
            authorProjection_.setNationalityName(null);
            LOGGER.warn(String.format(WARNING_MESSAGE, NationalityFields.NAME));
        }

        return builder_;
    }

    public AuthorProjectionBuilder setBio() {
        try {
            authorProjection_.setBio(resultSet_.getString(AuthorFields.BIO));
        } catch (SQLException e) {
            authorProjection_.setBio(null);
            LOGGER.warn(String.format(WARNING_MESSAGE, AuthorFields.BIO));
        }

        return builder_;
    }

    public AuthorProjection build() {
        return authorProjection_;
    }

}
