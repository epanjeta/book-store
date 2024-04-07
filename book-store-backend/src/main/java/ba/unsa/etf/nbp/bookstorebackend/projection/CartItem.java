package ba.unsa.etf.nbp.bookstorebackend.projection;

public class CartItem {
    private int id;
    private Integer quantity;
    private BookProjection book;

    public CartItem(Integer quantity, UserProjection user, BookProjection book) {
        this.quantity = quantity;
        this.book = book;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CartItem() {
        quantity = 1; //ovako treba li
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    public BookProjection getBook() {
        return book;
    }

    public void setBook(BookProjection book) {
        this.book = book;
    }
}
