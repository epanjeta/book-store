package ba.unsa.etf.nbp.bookstorebackend.constants;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;

public class OrderForm {
    private int userId;
    private Date createdAt;
    private Date orderAt;
    private Double total;
    private String status;
    private String paymentMethod;
    private int shippingAddressId;

    private Map<Integer, Integer> bookQuantity;

    public OrderForm() {
    }

    public OrderForm(int userId, Date createdAt, Date orderAt, Double total, String status, String paymentMethod, int shippingAddressId, Map<Integer, Integer> bookQuantity) {
        this.userId = userId;
        this.createdAt = createdAt;
        this.orderAt = orderAt;
        this.total = total;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.shippingAddressId = shippingAddressId;
        this.bookQuantity = bookQuantity;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getOrderAt() {
        return orderAt;
    }

    public void setOrderAt(Date orderAt) {
        this.orderAt = orderAt;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(int shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public Map<Integer, Integer> getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(Map<Integer, Integer> bookQuantity) {
        this.bookQuantity = bookQuantity;
    }
}
