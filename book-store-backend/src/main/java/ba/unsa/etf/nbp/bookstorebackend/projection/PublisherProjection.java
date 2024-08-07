package ba.unsa.etf.nbp.bookstorebackend.projection;

import jakarta.annotation.Nullable;

public class PublisherProjection {
    private int id;
    private String name;
    private String phone;
    private String email;
    private AddressProjection addressProjection;

    public PublisherProjection() {
    }

    @Nullable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Nullable
    public AddressProjection getAddressProjection() {
        return addressProjection;
    }

    public void setAddressProjection(AddressProjection addressProjection) {
        this.addressProjection = addressProjection;
    }
}
