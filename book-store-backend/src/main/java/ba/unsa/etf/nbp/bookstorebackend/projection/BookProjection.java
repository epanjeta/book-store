package ba.unsa.etf.nbp.bookstorebackend.projection;

import jakarta.annotation.Nullable;

import java.time.LocalDate;
import java.util.List;

public class BookProjection {

    private int id;
    private String ISBN;
    private String title;
    private String languageCode;
    private String description;
    private Double price;
    private Double stock;
    private LocalDate publicationDate;
    private PublisherProjection publisherProjection;
    private ImageProjection imageProjection;
    private List<String> genres;
    private List<AuthorProjection> authors;

    public BookProjection() {
    }

    @Nullable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Nullable
    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Nullable
    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String language_code) {
        this.languageCode = language_code;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Nullable
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Nullable
    public Double getStock() {
        return stock;
    }

    public void setStock(Double stock) {
        this.stock = stock;
    }

    @Nullable
    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    @Nullable
    public PublisherProjection getPublisherProjection() {
        return publisherProjection;
    }

    public void setPublisherProjection(PublisherProjection publisherProjection) {
        this.publisherProjection = publisherProjection;
    }

    @Nullable
    public ImageProjection getImageProjection() {
        return imageProjection;
    }

    public void setImageProjection(ImageProjection imageProjection) {
        this.imageProjection = imageProjection;
    }

    @Nullable
    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    @Nullable
    public List<AuthorProjection> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorProjection> authors) {
        this.authors = authors;
    }
}
