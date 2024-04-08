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
import ba.unsa.etf.nbp.bookstorebackend.statements.CartItemStatements;
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


        }
            return new ArrayList<CartItem>(cartItemMap.values());
        }
        catch (SQLException e){
            LOGGER.error("Exception when acquiring JDBC connection for class: " + CartItem.class);
            throw new RuntimeException(e);
        }

    }
}
