package ba.unsa.etf.nbp.bookstorebackend.repository;

import ba.unsa.etf.nbp.bookstorebackend.constants.BookFields;
import ba.unsa.etf.nbp.bookstorebackend.database.DatabaseService;
import ba.unsa.etf.nbp.bookstorebackend.mapper.AuthorMapper;
import ba.unsa.etf.nbp.bookstorebackend.mapper.BookMapper;
import ba.unsa.etf.nbp.bookstorebackend.mapper.CartItemMapper;
import ba.unsa.etf.nbp.bookstorebackend.mapper.PublisherMapper;
import ba.unsa.etf.nbp.bookstorebackend.projection.AuthorProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.BookProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.CartItem;
import ba.unsa.etf.nbp.bookstorebackend.projection.PublisherProjection;
import ba.unsa.etf.nbp.bookstorebackend.statements.BookStatements;
import ba.unsa.etf.nbp.bookstorebackend.statements.PublisherStatements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookRepository {
    private static Logger LOGGER = LoggerFactory.getLogger(PublisherRepository.class);
    @Autowired
    protected DatabaseService databaseService;


    public List<BookProjection> findAllBooks() {
        Connection connection = databaseService.getConnection();
        ResultSet rs = BookStatements.findAllBooks(connection);
        if (rs == null) {
            return new ArrayList<>();
        }

        List<BookProjection> bookProjections = new ArrayList<>();
        try {
            Map<Long, BookProjection> bookMap = new HashMap<>();
            while (rs.next()) {
                int bookId = rs.getInt(BookFields.ID);
                BookProjection bp = bookMap.get(bookId);
                if (bp == null) {
                    bookMap.put(Long.valueOf(bookId), BookMapper.createBookFromResultSet(rs));
                } else {
                    AuthorProjection ap = AuthorMapper.createAuthorFromResultSet(rs);
                    bp.getAuthors().add(ap);
                    String genre = rs.getString(BookFields.GENRE);
                    bp.getGenres().add(genre);
                }
            }
            bookProjections.addAll(bookMap.values());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookProjections;
    }
    public List<CartItem> findAllBooksForOrder(int orderId) {
        Connection connection = databaseService.getConnection();
        ResultSet rs = BookStatements.findAllBooksForOrder(connection, orderId);
        if (rs == null) {
            return new ArrayList<>();
        }

        List<CartItem> cartItems = new ArrayList<>();
        try {
            Map<Long, CartItem> cartMap = new HashMap<>();
            while (rs.next()) {
                int bookId = rs.getInt(BookFields.ID);
                int quantity = rs.getInt("quantity");
                CartItem bp = cartMap.get(bookId);
                if (bp == null) {
                    cartMap.put(Long.valueOf(bookId), CartItemMapper.createCartItemFromResultSet(rs));
                } else {
                    AuthorProjection ap = AuthorMapper.createAuthorFromResultSet(rs);
                    bp.getBook().getAuthors().add(ap);
                    String genre = rs.getString(BookFields.GENRE);
                    bp.getBook().getGenres().add(genre);
                }
            }
            cartItems.addAll(cartMap.values());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cartItems;
    }
}
