package ba.unsa.etf.nbp.bookstorebackend.mapper;

import ba.unsa.etf.nbp.bookstorebackend.builder.CartItemBuilder;
import ba.unsa.etf.nbp.bookstorebackend.builder.OrderProjectionBuilder;
import ba.unsa.etf.nbp.bookstorebackend.projection.BookProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.CartItem;
import ba.unsa.etf.nbp.bookstorebackend.projection.OrderProjection;

import java.sql.ResultSet;
import java.util.List;

public class CartItemMapper {
    public CartItemMapper() {
    }

    public static CartItem createCartItemFromResultSet(ResultSet resultSet){
        return createCartItem(resultSet)
                .setBook(BookMapper.createBookFromResultSet(resultSet))
                .build();
    }

    private  static CartItemBuilder createCartItem(ResultSet resultSet){
        return CartItemBuilder.create(resultSet)
                .setId()
                .setQuantity();


    }
}
