package ba.unsa.etf.nbp.bookstorebackend.repository;

import ba.unsa.etf.nbp.bookstorebackend.constants.BookFields;
import ba.unsa.etf.nbp.bookstorebackend.database.DatabaseService;
import ba.unsa.etf.nbp.bookstorebackend.mapper.AuthorMapper;
import ba.unsa.etf.nbp.bookstorebackend.mapper.BookMapper;
import ba.unsa.etf.nbp.bookstorebackend.mapper.CartItemMapper;
import ba.unsa.etf.nbp.bookstorebackend.projection.AuthorProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.BookProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.CartItem;
import ba.unsa.etf.nbp.bookstorebackend.projection.OrderProjection;
import ba.unsa.etf.nbp.bookstorebackend.statements.BookStatements;
import ba.unsa.etf.nbp.bookstorebackend.statements.CartItemStatements;
import ba.unsa.etf.nbp.bookstorebackend.statements.UserStatements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CartItemRepository {
    private static Logger LOGGER = LoggerFactory.getLogger(CartItemRepository.class);
    @Autowired
    protected DatabaseService databaseService;

    public List<CartItem> getCartForUser(int userId)  {
        Connection connection = databaseService.getConnection();
        ResultSet resultSet = CartItemStatements.getCartForUser(connection, userId);
        if(resultSet == null){
            return new ArrayList<CartItem>();
        }
        try{
        Map<Integer, CartItem> cartItemMap = new HashMap<>();
        while (resultSet.next()) {
           int currentBookId = resultSet.getInt(BookFields.ID);
           cartItemMap.put(currentBookId, CartItemMapper.createCartItemFromResultSet(resultSet));
            //fali za listu genre i lista autora
 //           if(cartItemMap.get(currentBookId) != null){
//                AuthorProjection ap = AuthorMapper.createAuthorFromResultSet(resultSet);
//                cartItemMap.get(currentBookId).getBook().getAuthors().add(ap);
//                String g = resultSet.getString(BookFields.GENRE);
//                BookProjection pom = cartItemMap.get(currentBookId).getBook();
//                List<String> genres = pom.getGenres();
//                genres.add(g);
//                cartItemMap.get(currentBookId).getBook().setGenres(genres);
//            }

        }
            return new ArrayList<CartItem>(cartItemMap.values());
        }
        catch (SQLException e){
            LOGGER.error("Exception when acquiring JDBC connection for class: " + CartItem.class);
            throw new RuntimeException(e);
        }

    }

    public int addToCart(int userId, int bookId, int quantity){
        Connection connection = databaseService.getConnection();
        ResultSet user = UserStatements.findUserWithId(connection, userId);
        if(user == null){
            throw new RuntimeException("User with given id doesn't exist");
        }
        ResultSet book = BookStatements.findBookWithId(connection, bookId);
        if(book == null){
            throw new RuntimeException("Book with given id doesn't exist");
        }
        if(quantity <= 0){
            throw new RuntimeException("Quantity can't be zero or less");
        }
        int rowsAffected = CartItemStatements.addToCart(connection, userId, bookId, quantity);
        if(rowsAffected==0){
            throw new RuntimeException("Zero rows affected");
        }
        else return rowsAffected;
    }
}
