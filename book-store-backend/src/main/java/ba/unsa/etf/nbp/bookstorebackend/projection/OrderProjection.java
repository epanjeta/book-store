package ba.unsa.etf.nbp.bookstorebackend.projection;

import ba.unsa.etf.nbp.bookstorebackend.constants.PaymentMethod;
import ba.unsa.etf.nbp.bookstorebackend.constants.Status;

import java.time.LocalDate;
import java.util.List;

public class OrderProjection {
    private int id;
    private LocalDate createdAt;
    private LocalDate orderDate;
    private Double total;
    private Status status;
    private PaymentMethod paymentMethod;
    private int CustomerId;
    private List<CartItem> booksForOrder;

    private AddressProjection shippingAddress;

    public OrderProjection() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public List<CartItem> getBooksForOrder() {
        return booksForOrder;
    }

    public void setBooksForOrder(List<CartItem> booksForOrder) {
        this.booksForOrder = booksForOrder;
    }

    public AddressProjection getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(AddressProjection shippingAdress) {
        this.shippingAddress = shippingAdress;
    }
}
