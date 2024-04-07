package ba.unsa.etf.nbp.bookstorebackend.builder;

import ba.unsa.etf.nbp.bookstorebackend.constants.*;
import ba.unsa.etf.nbp.bookstorebackend.projection.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class OrderProjectionBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressProjectionBuilder.class);
    private static final String WARNING_MESSAGE = "Field %s is not present in the result set for book projection";

    private static OrderProjectionBuilder builder_;
    private static OrderProjection orderProjection_;
    private static ResultSet resultSet_;

    public OrderProjectionBuilder() {
    }

    public static OrderProjectionBuilder create(ResultSet resultSet){
        builder_ = new OrderProjectionBuilder();
        orderProjection_ = new OrderProjection();
        resultSet_ = resultSet;

        return builder_;

    }

    public OrderProjectionBuilder setId(){
        try {
            orderProjection_.setId(resultSet_.getInt(OrderFields.ID));
        } catch (SQLException e) {
            orderProjection_.setId(-1);
            LOGGER.warn(String.format(WARNING_MESSAGE, OrderFields.ID));
        }

        return builder_;
    }
    public OrderProjectionBuilder setCreatedAt(){
        try{
            orderProjection_.setCreatedAt(resultSet_.getObject(OrderFields.CREATED_AT, LocalDate.class));
        }
        catch(SQLException e){
            orderProjection_.setCreatedAt(null);
            LOGGER.warn(String.format(WARNING_MESSAGE, OrderFields.CREATED_AT));
        }
        return builder_;
    }

    public OrderProjectionBuilder setOrderDate(){
        try{
            orderProjection_.setOrderDate(resultSet_.getObject(OrderFields.ORDER_DATE, LocalDate.class));
        }
        catch(SQLException e){
            orderProjection_.setOrderDate(null);
            LOGGER.warn(String.format(WARNING_MESSAGE, OrderFields.ORDER_DATE));
        }
        return builder_;
    }

    public OrderProjectionBuilder setTotal(){
        try{
            orderProjection_.setTotal(resultSet_.getDouble(OrderFields.TOTAL));
        }
        catch (SQLException e){
            orderProjection_.setTotal(-1.);
            LOGGER.warn(String.format(WARNING_MESSAGE, OrderFields.TOTAL));
        }
        return builder_;
    }

    public OrderProjectionBuilder setStatus(){
        try{
            orderProjection_.setStatus(resultSet_.getObject(OrderFields.STATUS, Status.class));
        }
        catch(SQLException e){
            orderProjection_.setStatus(null);
            LOGGER.warn(String.format(WARNING_MESSAGE, OrderFields.STATUS));
        }
        return builder_;
    }
    public OrderProjectionBuilder setPaymentMethod(){
        try{
            orderProjection_.setPaymentMethod(resultSet_.getObject(OrderFields.PAYMENT_METHOD, PaymentMethod.class));

        }catch(SQLException e){
            orderProjection_.setPaymentMethod(null);
            LOGGER.warn(String.format(WARNING_MESSAGE, OrderFields.PAYMENT_METHOD));
        }
        return builder_;
    }

    public OrderProjectionBuilder setCustomerId(){
        try{
            orderProjection_.setCustomerId(resultSet_.getInt(OrderFields.CUSTOMER_ID));

        }catch(SQLException e){
            orderProjection_.setCustomerId(-1);
            LOGGER.warn(String.format(WARNING_MESSAGE, OrderFields.CUSTOMER_ID));
        }
        return builder_;
    }

    public OrderProjectionBuilder setShippingAddress(AddressProjection address){
       orderProjection_.setShippingAddress(address);
        return builder_;
    }
    public OrderProjectionBuilder setBooksForOrder(List<CartItem> books){
        orderProjection_.setBooksForOrder(books);
        return builder_;
    }

    public OrderProjection build() {
        return orderProjection_;
    }

}
