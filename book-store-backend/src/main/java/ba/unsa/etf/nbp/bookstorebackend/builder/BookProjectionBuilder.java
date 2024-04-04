package ba.unsa.etf.nbp.bookstorebackend.builder;

import ba.unsa.etf.nbp.bookstorebackend.constants.AuthorFields;
import ba.unsa.etf.nbp.bookstorebackend.constants.BookFields;
import ba.unsa.etf.nbp.bookstorebackend.projection.AddressProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.AuthorProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.BookProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.ImageProjection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class BookProjectionBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressProjectionBuilder.class);
    private static final String WARNING_MESSAGE = "Field %s is not present in the result set for book projection";

    private static BookProjectionBuilder builder_;
    private static BookProjection bookProjection_;
    private static ResultSet resultSet_;

    public BookProjectionBuilder() {
    }



    public static BookProjectionBuilder create(ResultSet resultSet) {
        builder_ = new BookProjectionBuilder();
        bookProjection_ = new BookProjection();
        resultSet_ = resultSet;

        return builder_;
    }

    public BookProjectionBuilder setId() {
        try {
            bookProjection_.setId(resultSet_.getInt(BookFields.ID));
        } catch (SQLException e) {
            bookProjection_.setId(-1);
            LOGGER.warn(String.format(WARNING_MESSAGE, AuthorFields.ID));
        }

        return builder_;
    }

    public BookProjectionBuilder setISBN() {
        try {
            bookProjection_.setISBN(resultSet_.getString(BookFields.ISBN));
        } catch (SQLException e) {
            bookProjection_.setISBN(null);
            LOGGER.warn(String.format(WARNING_MESSAGE, BookFields.ISBN));
        }

        return builder_;
    }

    public BookProjectionBuilder setTitle() {
        try {
            bookProjection_.setTitle(resultSet_.getString(BookFields.TITLE));
        } catch (SQLException e) {
            bookProjection_.setTitle(null);
            LOGGER.warn(String.format(WARNING_MESSAGE, BookFields.TITLE));
        }

        return builder_;
    }

    public BookProjectionBuilder setLanguageCode() {
        try {
            bookProjection_.setLanguageCode(resultSet_.getString(BookFields.LANGUAGE_CODE));
        } catch (SQLException e) {
            bookProjection_.setLanguageCode(null);
            LOGGER.warn(String.format(WARNING_MESSAGE, BookFields.LANGUAGE_CODE));
        }

        return builder_;
    }

    public BookProjectionBuilder setDescription() {
        try {
            bookProjection_.setDescription(resultSet_.getString(BookFields.DESCRIPTION));
        } catch (SQLException e) {
            bookProjection_.setDescription(null);
            LOGGER.warn(String.format(WARNING_MESSAGE, BookFields.DESCRIPTION));
        }

        return builder_;
    }

    public BookProjectionBuilder setPublicationDate() {
        try {
            bookProjection_.setPublicationDate(resultSet_.getObject(BookFields.PUBLICATION_DATE, LocalDate.class));
        } catch (SQLException e) {
            bookProjection_.setPublicationDate(null);
            LOGGER.warn(String.format(WARNING_MESSAGE, BookFields.PUBLICATION_DATE));
        }

        return builder_;
    }

    public BookProjectionBuilder setPrice() {
        try {
            bookProjection_.setPrice(resultSet_.getDouble(BookFields.PRICE));
        } catch (SQLException e) {
            bookProjection_.setPrice(null);
            LOGGER.warn(String.format(WARNING_MESSAGE, BookFields.PRICE));
        }

        return builder_;
    }

    public BookProjectionBuilder setStock() {
        try {
            bookProjection_.setStock(resultSet_.getDouble(BookFields.STOCK));
        } catch (SQLException e) {
            bookProjection_.setStock(null);
            LOGGER.warn(String.format(WARNING_MESSAGE, BookFields.STOCK));
        }

        return builder_;
    }

    public BookProjectionBuilder setImage(ImageProjection image) {
        bookProjection_.setImageProjection(image);
        return builder_;
    }

    public BookProjectionBuilder setAuthors(AuthorProjection author) {
        List<AuthorProjection> authors = List.of(author);
        bookProjection_.setAuthors(authors);
        return builder_;
    }

    public BookProjectionBuilder setGenres() {
        try {
            String genre = resultSet_.getString(BookFields.GENRE);
            List<String> genres = List.of(genre);
            bookProjection_.setGenres(genres);
        } catch (SQLException e) {
            bookProjection_.setGenres(null);
            LOGGER.warn(String.format(WARNING_MESSAGE, BookFields.GENRE));
        }

        return builder_;
    }

    public BookProjection build() {
        return bookProjection_;
    }
}
