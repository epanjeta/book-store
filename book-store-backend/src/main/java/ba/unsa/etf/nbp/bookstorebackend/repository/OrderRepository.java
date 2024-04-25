package ba.unsa.etf.nbp.bookstorebackend.repository;

import ba.unsa.etf.nbp.bookstorebackend.Role;
import ba.unsa.etf.nbp.bookstorebackend.constants.BookFields;
import ba.unsa.etf.nbp.bookstorebackend.constants.OrderFields;
import ba.unsa.etf.nbp.bookstorebackend.constants.OrderForm;
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

    public int createOrderForUser(OrderForm orderForm){
        Connection connection = databaseService.getConnection();
        ResultSet user = UserStatements.findUserWithId(connection, orderForm.getUserId());
        if(user ==null){
            throw new RuntimeException("User eith given id doen't exist");
        }
        try{
            int createdOrderId = OrderStatements.createNewOrder(connection, orderForm);
            for(Integer bookId: orderForm.getBookQuantity().keySet()){
                ResultSet book = BookStatements.findBookWithId(connection, bookId);
                if(book == null){
                    throw new RuntimeException("Book with given id doesnt exist");
                }
                if(orderForm.getBookQuantity().get(bookId) <= 0){
                    throw new RuntimeException("Quantity can't be zero or less");
                }
            }
            int rowsAffected = OrderStatements.createBookToOrder(connection,createdOrderId, orderForm.getBookQuantity());
            return rowsAffected;
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
