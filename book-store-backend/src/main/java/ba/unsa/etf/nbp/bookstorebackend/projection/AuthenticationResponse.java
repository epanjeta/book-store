package ba.unsa.etf.nbp.bookstorebackend.projection;

public class AuthenticationResponse {

    private int id;

    private String jwt;

    private String role;

    private String userName;

    private String errorMessage;

    public AuthenticationResponse(int id, String jwt, String role, String userName) {
        this.id = id;
        this.jwt = jwt;
        this.role = role;
        this.userName = userName;
    }

    public AuthenticationResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
