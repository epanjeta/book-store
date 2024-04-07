package ba.unsa.etf.nbp.bookstorebackend.builder;

import ba.unsa.etf.nbp.bookstorebackend.constants.OrderFields;
import ba.unsa.etf.nbp.bookstorebackend.projection.BookProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.CartItem;
import ba.unsa.etf.nbp.bookstorebackend.projection.OrderProjection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CartItemBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressProjectionBuilder.class);
    private static final String WARNING_MESSAGE = "Field %s is not present in the result set for book projection";

    private static CartItemBuilder builder_;
    private static CartItem cartItem_;
    private static ResultSet resultSet_;

    public CartItemBuilder() {
    }
    public static CartItemBuilder create(ResultSet resultSet){
        builder_ = new CartItemBuilder();
        cartItem_ = new CartItem();
        resultSet_ = resultSet;

        return builder_;

    }

    public CartItemBuilder setId(){
        try {
            cartItem_.setId(resultSet_.getInt(1));
        } catch (SQLException e) {
            cartItem_.setId(-1);
            LOGGER.warn(String.format(WARNING_MESSAGE, "ID"));
        }

        return builder_;
    }
    public CartItemBuilder setQuantity(){
        try {
            cartItem_.setQuantity(resultSet_.getInt("QUANTITY"));
        } catch (SQLException e) {
            cartItem_.setId(-1);
            LOGGER.warn(String.format(WARNING_MESSAGE, "QUANTITY"));
        }

        return builder_;
    }

    public CartItemBuilder setBook(BookProjection book){
        cartItem_.setBook(book);
        return builder_;
    }

    public CartItem build(){
        return cartItem_;
    }
}
