package ba.unsa.etf.nbp.bookstorebackend.projection;

import jakarta.annotation.Nullable;

import java.sql.Blob;

public class ImageProjection {
    private int id;
    private String name;
    private byte[] photo;

    public ImageProjection() {
    }

    @Nullable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
