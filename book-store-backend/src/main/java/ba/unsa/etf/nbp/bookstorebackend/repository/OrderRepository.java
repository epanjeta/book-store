package ba.unsa.etf.nbp.bookstorebackend.repository;

import ba.unsa.etf.nbp.bookstorebackend.Role;
import ba.unsa.etf.nbp.bookstorebackend.constants.BookFields;
import ba.unsa.etf.nbp.bookstorebackend.constants.OrderFields;
import ba.unsa.etf.nbp.bookstorebackend.database.DatabaseService;
import ba.unsa.etf.nbp.bookstorebackend.mapper.AuthorMapper;
import ba.unsa.etf.nbp.bookstorebackend.mapper.CartItemMapper;
import ba.unsa.etf.nbp.bookstorebackend.mapper.OrderMapper;
import ba.unsa.etf.nbp.bookstorebackend.mapper.UserMapper;
import ba.unsa.etf.nbp.bookstorebackend.projection.AuthorProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.CartItem;
import ba.unsa.etf.nbp.bookstorebackend.projection.OrderProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.UserProjection;
import ba.unsa.etf.nbp.bookstorebackend.statements.BookStatements;
import ba.unsa.etf.nbp.bookstorebackend.statements.OrderStatements;
import ba.unsa.etf.nbp.bookstorebackend.statements.UserStatements;
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
public class OrderRepository {
    private static Logger LOGGER = LoggerFactory.getLogger(OrderRepository.class);

    @Autowired
    protected DatabaseService databaseService;
    public List<OrderProjection> findAll(int id) {
        Connection connection = databaseService.getConnection();

        Map<Integer, OrderProjection> orderProjectionListMap = new HashMap<>();

        try {
            ResultSet rs = OrderStatements.findAllOrdersForUser(connection, id);

            while(rs.next()){
                int currentOrderId = rs.getInt(OrderFields.ID);

                if(!orderProjectionListMap.containsKey(currentOrderId)){
                    List<CartItem> booksForOrder = findAllBooksForOrder(currentOrderId);
                    OrderProjection orderProjection = OrderMapper.createOrderFromResultSet(rs, booksForOrder);
                    orderProjectionListMap.put(currentOrderId, orderProjection);
                }

            }

            return new ArrayList<OrderProjection>(orderProjectionListMap.values());

        } catch (SQLException e) {
            LOGGER.error("Exception when acquiring JDBC connection for class: " + OrderProjection.class);
            throw new RuntimeException(e);
        }
    }

    private List<CartItem> findAllBooksForOrder(int orderId) {
        Connection connection = databaseService.getConnection();
        ResultSet allBooksForOrder = BookStatements.findAllBooksForOrder(connection, orderId);
        if (allBooksForOrder == null) {
            return new ArrayList<>();
        }

        List<CartItem> cartItems = new ArrayList<>();
        try {
            Map<Long, CartItem> cartMap = new HashMap<>();
            while (allBooksForOrder.next()) {
                int bookId = allBooksForOrder.getInt(BookFields.ID);
                int quantity = allBooksForOrder.getInt("quantity");
                CartItem bp = cartMap.get(bookId);
                if (bp == null) {
                    cartMap.put(Long.valueOf(bookId), CartItemMapper.createCartItemFromResultSet(allBooksForOrder));
                } else {
                    AuthorProjection ap = AuthorMapper.createAuthorFromResultSet(allBooksForOrder);
                    bp.getBook().getAuthors().add(ap);
                    String genre = allBooksForOrder.getString(BookFields.GENRE);
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
