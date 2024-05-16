package ba.unsa.etf.nbp.bookstorebackend.projection;

public class AutocompleteProjection {
    private Integer id;

    private String code;
    private String name;

    public AutocompleteProjection() {
    }

    public AutocompleteProjection(String name) {
        this.name = name;
    }

    public AutocompleteProjection(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
