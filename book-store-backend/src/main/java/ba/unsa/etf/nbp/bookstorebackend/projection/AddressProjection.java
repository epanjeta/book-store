package ba.unsa.etf.nbp.bookstorebackend.projection;

public class AddressProjection {

    private int id;
    private String street;
    private String zipCode;

    public AddressProjection() {
    }

    public AddressProjection(int id, String street, String zipCode) {
        this.id = id;
        this.street = street;
        this.zipCode = zipCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
