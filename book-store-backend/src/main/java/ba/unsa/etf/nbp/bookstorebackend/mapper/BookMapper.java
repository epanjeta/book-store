package ba.unsa.etf.nbp.bookstorebackend.mapper;

import ba.unsa.etf.nbp.bookstorebackend.builder.AuthorProjectionBuilder;
import ba.unsa.etf.nbp.bookstorebackend.builder.BookProjectionBuilder;
import ba.unsa.etf.nbp.bookstorebackend.projection.AuthorProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.AutocompleteProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.BookProjection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;

public class BookMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressMapper.class);
    private static final String WARNING_MESSAGE = "Field %s is not present in the result set.";

    public BookMapper() {
    }

    public static BookProjection createBookFromResultSet(ResultSet resultSet) {
        return BookProjectionBuilder.create(resultSet)
                .setId()
                .setISBN()
                .setTitle()
                .setLanguageCode()
                .setDescription()
                .setPublicationDate()
                .setPrice()
                .setStock()
                .setImage(ImageMapper.createImage(resultSet))
                .setAuthors(AuthorMapper.createAuthorFromResultSet(resultSet))
                .setGenres()
                .build();
    }
}
