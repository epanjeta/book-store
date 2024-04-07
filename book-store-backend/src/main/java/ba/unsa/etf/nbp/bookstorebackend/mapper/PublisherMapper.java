package ba.unsa.etf.nbp.bookstorebackend.mapper;

import ba.unsa.etf.nbp.bookstorebackend.Role;
import ba.unsa.etf.nbp.bookstorebackend.builder.PublisherProjectionBuilder;
import ba.unsa.etf.nbp.bookstorebackend.builder.UserProjectionBuilder;
import ba.unsa.etf.nbp.bookstorebackend.projection.PublisherProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.UserProjection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;

public class PublisherMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressMapper.class);
    private static final String WARNING_MESSAGE = "Field %s is not present in the result set.";

    public PublisherMapper() {
    }

    public static PublisherProjection createPublisherFromResultSet(ResultSet resultSet) {
        return createPublisher(resultSet)
                .setAddress(AddressMapper.createAddressFromResultSet(resultSet))
                .build();
    }

    private static PublisherProjectionBuilder createPublisher(ResultSet resultSet) {
        return PublisherProjectionBuilder
                .create(resultSet)
                .setId()
                .setName()
                .setEmail()
                .setPhone();
    }
}
