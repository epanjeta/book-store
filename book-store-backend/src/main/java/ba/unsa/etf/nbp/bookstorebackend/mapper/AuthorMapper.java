package ba.unsa.etf.nbp.bookstorebackend.mapper;

import ba.unsa.etf.nbp.bookstorebackend.builder.AddressProjectionBuilder;
import ba.unsa.etf.nbp.bookstorebackend.builder.AuthorProjectionBuilder;
import ba.unsa.etf.nbp.bookstorebackend.constants.CityFields;
import ba.unsa.etf.nbp.bookstorebackend.constants.NationalityFields;
import ba.unsa.etf.nbp.bookstorebackend.projection.AddressProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.AuthorProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.AutocompleteProjection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressMapper.class);
    private static final String WARNING_MESSAGE = "Field %s is not present in the result set.";

    public AuthorMapper() {
    }

    public static AuthorProjection createAuthorFromResultSet(ResultSet resultSet) {
        return AuthorProjectionBuilder.create(resultSet)
                .setId()
                .setFirstName()
                .setLastName()
                .setBirthDate()
                .setDeathDate()
                .setNationalityId()
                .setNationalityName()
                .setBio()
                .build();
    }

    public static List<AutocompleteProjection> mapNationalities(ResultSet rs) {
        List<AutocompleteProjection> projections = new ArrayList<>();
        try {
            while (rs.next()) {
                AutocompleteProjection projection = new AutocompleteProjection();
                try {
                    projection.setId(rs.getInt(NationalityFields.ID));
                } catch (SQLException e) {
                    LOGGER.warn(String.format(WARNING_MESSAGE, NationalityFields.ID));
                }
                try {
                    projection.setName(rs.getString(NationalityFields.NAME));
                } catch (SQLException e) {
                    LOGGER.warn(String.format(WARNING_MESSAGE, NationalityFields.NAME));
                }

                projections.add(projection);
            }
            return projections;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
