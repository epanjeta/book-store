package ba.unsa.etf.nbp.bookstorebackend.mapper;

import ba.unsa.etf.nbp.bookstorebackend.builder.AddressProjectionBuilder;
import ba.unsa.etf.nbp.bookstorebackend.constants.AddressFields;
import ba.unsa.etf.nbp.bookstorebackend.constants.CityFields;
import ba.unsa.etf.nbp.bookstorebackend.constants.CountryFields;
import ba.unsa.etf.nbp.bookstorebackend.projection.AddressProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.AutocompleteProjection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressMapper.class);
    private static final String WARNING_MESSAGE = "Field %s is not present in the result set.";

    private AddressMapper() {}

    public static AddressProjection createAddressFromResultSet(ResultSet resultSet) {
                return AddressProjectionBuilder.create(resultSet)
                .setId()
                .setStreet()
                .setZipCode()
                .setCityId()
                .setCity()
                .setCountryId()
                .setCountry()
                .build();
    }

    public static List<AutocompleteProjection> mapCities(ResultSet rs) {
        List<AutocompleteProjection> projections = new ArrayList<>();
        try {
            while (rs.next()) {
                AutocompleteProjection projection = new AutocompleteProjection();
                try {
                    projection.setId(rs.getInt(CityFields.ID));
                } catch (SQLException e) {
                    LOGGER.warn(String.format(WARNING_MESSAGE, CityFields.ID));
                }
                try {
                    projection.setName(rs.getString(CityFields.NAME));
                } catch (SQLException e) {
                    LOGGER.warn(String.format(WARNING_MESSAGE, CityFields.NAME));
                }

                projections.add(projection);
            }
            return projections;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<AutocompleteProjection> mapCountries(ResultSet rs) {
        List<AutocompleteProjection> projections = new ArrayList<>();
        try {
            while (rs.next()) {
                AutocompleteProjection projection = new AutocompleteProjection();
                try {
                    projection.setId(rs.getInt(CountryFields.ID));
                } catch (SQLException e) {
                    LOGGER.warn(String.format(WARNING_MESSAGE, CountryFields.ID));
                }
                try {
                    projection.setName(rs.getString(CountryFields.NAME));
                } catch (SQLException e) {
                    LOGGER.warn(String.format(WARNING_MESSAGE, CountryFields.NAME));
                }

                projections.add(projection);
            }
            return projections;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
