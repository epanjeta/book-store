package ba.unsa.etf.nbp.bookstorebackend.builder;

import ba.unsa.etf.nbp.bookstorebackend.Role;
import ba.unsa.etf.nbp.bookstorebackend.constants.UserFields;
import ba.unsa.etf.nbp.bookstorebackend.projection.AddressProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.UserProjection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserProjectionBuilder {
    private static Logger LOGGER = LoggerFactory.getLogger(UserProjectionBuilder.class);
    private static final String WARNING_MESSAGE = "Field %s is not present in the result set for user projection";

    private static UserProjectionBuilder builder_;
    private static UserProjection userProjection_;
    private static ResultSet resultSet_;

    private UserProjectionBuilder() {

    }

    public static UserProjectionBuilder create(ResultSet resultSet) {
        builder_ = new UserProjectionBuilder();
        userProjection_ = new UserProjection();
        resultSet_ = resultSet;

        return builder_;
    }

    public UserProjectionBuilder setId() {
        try {
            userProjection_.setId(resultSet_.getInt(UserFields.ID));
        } catch (SQLException e) {
            LOGGER.warn(String.format(WARNING_MESSAGE, UserFields.ID));
        }

        return builder_;
    }

    public UserProjectionBuilder setFirstName() {
        try {
            userProjection_.setFirstName(resultSet_.getString(UserFields.FIRST_NAME));
        } catch (SQLException e) {
            LOGGER.warn(String.format(WARNING_MESSAGE, UserFields.FIRST_NAME));
        }

        return builder_;
    }

    public UserProjectionBuilder setLastName() {
        try {
            userProjection_.setLastName(resultSet_.getString(UserFields.LAST_NAME));
        } catch (SQLException e) {
            LOGGER.warn(String.format(WARNING_MESSAGE, UserFields.LAST_NAME));
        }

        return builder_;
    }

    public UserProjectionBuilder setEmail() {
        try {
            userProjection_.setEmail(resultSet_.getString(UserFields.EMAIL));
        } catch (SQLException e) {
            LOGGER.warn(String.format(WARNING_MESSAGE, UserFields.EMAIL));
        }

        return builder_;
    }

    public UserProjectionBuilder setUsername() {
        try {
            userProjection_.setUserName(resultSet_.getString(UserFields.USERNAME));
        } catch (SQLException e) {
            LOGGER.warn(String.format(WARNING_MESSAGE, UserFields.USERNAME));
        }

        return builder_;
    }


    public UserProjectionBuilder setPassword() {
        try {
            userProjection_.setPassword(resultSet_.getString(UserFields.PASSWORD));
        } catch (SQLException e) {
            LOGGER.warn(String.format(WARNING_MESSAGE, UserFields.PASSWORD));
        }

        return builder_;
    }

    public UserProjectionBuilder setPhoneNumber() {
        try {
            userProjection_.setPhoneNumber(resultSet_.getString(UserFields.PHONE_NUMBER));
        } catch (SQLException e) {
            LOGGER.warn(String.format(WARNING_MESSAGE, UserFields.PHONE_NUMBER));
        }

        return builder_;
    }

    public UserProjectionBuilder setBirthDay() {
        try {
            userProjection_.setBirthDay(resultSet_.getObject(UserFields.BIRTH_DATE, LocalDate.class));
        } catch (SQLException e) {
            LOGGER.warn(String.format(WARNING_MESSAGE, UserFields.BIRTH_DATE));
        }

        return builder_;
    }

    public UserProjectionBuilder setAddress(AddressProjection address) {
        userProjection_.setAddressProjection(address);
        return builder_;
    }

    public UserProjectionBuilder setRole(Role role) {
        userProjection_.setRole(role);
        return builder_;
    }

    public UserProjection build() {
        return userProjection_;
    }
}

