package ba.unsa.etf.nbp.bookstorebackend.mapper;

import ba.unsa.etf.nbp.bookstorebackend.builder.ImageProjectionBuilder;
import ba.unsa.etf.nbp.bookstorebackend.builder.PublisherProjectionBuilder;
import ba.unsa.etf.nbp.bookstorebackend.projection.ImageProjection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;

public class ImageMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressMapper.class);
    private static final String WARNING_MESSAGE = "Field %s is not present in the result set.";

    public static ImageProjection createImage(ResultSet resultSet) {
        return ImageProjectionBuilder.create(resultSet)
                .setId()
                .setName()
                .setPhoto()
                .setByte64()
                .build();
    }
}
