package ba.unsa.etf.nbp.bookstorebackend.constants;

import java.util.Date;

public class BookForm {
    private String ISBN;
    private String title;
    private  String languageCode;
    private String description;
    private Date publicationDate;
    private Double price;
    private Double stock;
    private Double publisherId;

    public BookForm() {
    }

    public BookForm(String ISBN, String title, String languageCode, String description, Date publicationDate, Double price, Double stock, Double publisherId) {
        this.ISBN = ISBN;
        this.title = title;
        this.languageCode = languageCode;
        this.description = description;
        this.publicationDate = publicationDate;
        this.price = price;
        this.stock = stock;
        this.publisherId = publisherId;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getStock() {
        return stock;
    }

    public void setStock(Double stock) {
        this.stock = stock;
    }

    public Double getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Double publisherId) {
        this.publisherId = publisherId;
    }
}
