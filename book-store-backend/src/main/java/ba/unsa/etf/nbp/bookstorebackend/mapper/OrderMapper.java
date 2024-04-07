package ba.unsa.etf.nbp.bookstorebackend.mapper;

import ba.unsa.etf.nbp.bookstorebackend.builder.OrderProjectionBuilder;
import ba.unsa.etf.nbp.bookstorebackend.projection.CartItem;
import ba.unsa.etf.nbp.bookstorebackend.projection.OrderProjection;

import java.sql.ResultSet;
import java.util.List;

public class OrderMapper {
    public OrderMapper() {
    }

    public static OrderProjection createOrderFromResultSet(ResultSet resultSet, List<CartItem> books){
        return createOrder(resultSet)
                .setBooksForOrder(books)
                .setShippingAddress(AddressMapper.createAddressFromResultSet(resultSet))
                .build();
    }

    private  static OrderProjectionBuilder createOrder(ResultSet resultSet){
        return OrderProjectionBuilder.create(resultSet)
                .setId()
                .setOrderDate()
                .setCreatedAt()
                .setTotal()
                .setStatus()
                .setPaymentMethod()
                .setCustomerId();

    }

}
