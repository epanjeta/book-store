package ba.unsa.etf.nbp.bookstorebackend.builder;

import ba.unsa.etf.nbp.bookstorebackend.constants.AddressFields;
import ba.unsa.etf.nbp.bookstorebackend.constants.CityFields;
import ba.unsa.etf.nbp.bookstorebackend.constants.CountryFields;
import ba.unsa.etf.nbp.bookstorebackend.projection.AddressProjection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressProjectionBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressProjectionBuilder.class);
    private static final String WARNING_MESSAGE = "Field %s is not present in the result set for address projection";

    private static AddressProjectionBuilder builder_;
    private static AddressProjection addressProjection_;
    private static ResultSet resultSet_;

    private AddressProjectionBuilder() {

    }

    public static AddressProjectionBuilder create(ResultSet resultSet) {
        builder_ = new AddressProjectionBuilder();
        addressProjection_ = new AddressProjection();
        resultSet_ = resultSet;

        return builder_;
    }

    public AddressProjectionBuilder setId() {
        try {
            addressProjection_.setAddressId(resultSet_.getInt(AddressFields.ID));
        } catch (SQLException e) {
            LOGGER.warn(String.format(WARNING_MESSAGE, AddressFields.ID));
        }

        return builder_;
    }

    public AddressProjectionBuilder setStreet() {
        try {
            addressProjection_.setStreet(resultSet_.getString(AddressFields.STREET));
        } catch (SQLException e) {
            LOGGER.warn(String.format(WARNING_MESSAGE, AddressFields.STREET));
        }

        return builder_;
    }

    public AddressProjectionBuilder setZipCode() {
        try {
            addressProjection_.setZipCode(resultSet_.getString(AddressFields.ZIP_CODE));
        } catch (SQLException e) {
            LOGGER.warn(String.format(WARNING_MESSAGE, AddressFields.ZIP_CODE));
        }

        return builder_;
    }

    public AddressProjectionBuilder setCityId() {
        try {
            addressProjection_.setCityId(resultSet_.getInt(CityFields.ID));
        } catch (SQLException e) {
            LOGGER.warn(String.format(WARNING_MESSAGE, CityFields.ID));
        }

        return builder_;
    }

    public AddressProjectionBuilder setCity() {
        try {
            addressProjection_.setCityName(resultSet_.getString(CityFields.NAME));
        } catch (SQLException e) {
            LOGGER.warn(String.format(WARNING_MESSAGE, CityFields.NAME));
        }

        return builder_;
    }

    public AddressProjectionBuilder setCountryId() {
        try {
            addressProjection_.setCountryId(resultSet_.getInt(CountryFields.ID));
        } catch (SQLException e) {
            LOGGER.warn(String.format(WARNING_MESSAGE, CountryFields.ID));
        }

        return builder_;
    }

    public AddressProjectionBuilder setCountry() {
        try {
            addressProjection_.setCountryName(resultSet_.getString(CountryFields.NAME));
        } catch (SQLException e) {
            LOGGER.warn(String.format(WARNING_MESSAGE, CountryFields.NAME));
        }

        return builder_;
    }

    public AddressProjection build() {
        return addressProjection_;
    }
}
