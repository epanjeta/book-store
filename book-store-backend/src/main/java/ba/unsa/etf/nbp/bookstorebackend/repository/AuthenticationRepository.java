package ba.unsa.etf.nbp.bookstorebackend.repository;

import ba.unsa.etf.nbp.bookstorebackend.Role;
import ba.unsa.etf.nbp.bookstorebackend.database.DatabaseService;
import ba.unsa.etf.nbp.bookstorebackend.projection.AuthenticationRequest;
import ba.unsa.etf.nbp.bookstorebackend.projection.AuthenticationResponse;
import ba.unsa.etf.nbp.bookstorebackend.projection.MessageResponse;
import ba.unsa.etf.nbp.bookstorebackend.projection.UserProjection;
import ba.unsa.etf.nbp.bookstorebackend.statements.AuthenticationStatements;
import ba.unsa.etf.nbp.bookstorebackend.util.AuthenticationUtil;
import ba.unsa.etf.nbp.bookstorebackend.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class AuthenticationRepository {

    private static Logger LOGGER = LoggerFactory.getLogger(AuthorRepository.class);

    @Autowired JwtUtil jwtUtil;
    @Autowired
    protected DatabaseService databaseService;

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest){
        String email = authenticationRequest.getEmail();
        String password = authenticationRequest.getPassword();
        ResultSet rs = AuthenticationStatements.authenticate(databaseService.getConnection(), email);

        try{
            if(rs.next()){
                if (AuthenticationUtil.comparePasswords(password, rs.getString("PASSWORD"))){
                    String jwt = jwtUtil.generateToken(rs.getInt("USER_ID"), rs.getString("ROLE_NAME"));
                    return new AuthenticationResponse(rs.getInt("USER_ID"), jwt, rs.getString("ROLE_NAME"), rs.getString("USERNAME"));
                }
                else return new AuthenticationResponse("Invalid email or password");
            }
            else return new AuthenticationResponse("User not found");

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public MessageResponse register(UserProjection userProjection) {

        // Provjera polja
        MessageResponse validation = validateFields(userProjection);
        if(!validation.getMessage().equals("OK")) return validation;

        // Provjera da li email ili username postoje
        MessageResponse existFields = existFields(userProjection);
        if(!existFields.getMessage().equals("OK")) return existFields;

        //heshiranje passworda
        String hashedPassword = AuthenticationUtil.hashPassword(userProjection.getPassword());
        userProjection.setPassword(hashedPassword);

        //insert u bazu
        int userId = AuthenticationStatements.createUser(databaseService.getConnection(), userProjection, Role.BOOK_BUYER);
        try{
            databaseService.getConnection().commit();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new MessageResponse("Successfully added user with ID = " + userId);

    }

    public MessageResponse validateFields(UserProjection userProjection){

        if(userProjection.getEmail() == null || userProjection.getEmail().isBlank())
            return new MessageResponse("E-mail not provided");
        if(userProjection.getUserName() == null || userProjection.getUserName().isBlank())
            return new MessageResponse("Username not provided");
        if(userProjection.getFirstName() == null || userProjection.getFirstName().isBlank())
            return new MessageResponse("First name not provided");
        if(userProjection.getLastName() == null || userProjection.getLastName().isBlank())
            return new MessageResponse("Last name not provided");
        if(userProjection.getPassword() == null || userProjection.getPassword().isBlank())
            return new MessageResponse("Password not provided");
        if(userProjection.getPassword().length() < 8)
            return new MessageResponse("Password must be at least 8 characters long");

        return new MessageResponse("OK");
    }

    public MessageResponse existFields(UserProjection userProjection){
        ResultSet rs1 = AuthenticationStatements.getUserEmail(databaseService.getConnection(), userProjection.getEmail());
        try{
            if(rs1.next()){
                return new MessageResponse("User with that e-mail already exists");
            }
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        ResultSet rs2 = AuthenticationStatements.getUserUserName(databaseService.getConnection(), userProjection.getUserName());
        try{
            if(rs2.next()){
                return new MessageResponse("User with that username already exists");
            }
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
        return new MessageResponse("OK");
    }

    public boolean emptyHeader(String authorizationHeader){
        if(authorizationHeader == null) return true;
        if(authorizationHeader.isEmpty() || authorizationHeader.isBlank()) return true;
        else return false;
    }

    public boolean isAdminRole(String authorizationHeader) {
        if(emptyHeader(authorizationHeader)) return false;
        String token = authorizationHeader.replace("Bearer ", "");
        Claims claims = jwtUtil.extractClaims(token);
        String userType = (String) claims.get("role");
        if(userType.equals("ADMINISTRATOR")) return true;
        else return false;
    }

    public boolean isUserRole(String authorizationHeader) {
        if(emptyHeader(authorizationHeader)) return false;
        String token = authorizationHeader.replace("Bearer ", "");
        Claims claims = jwtUtil.extractClaims(token);
        String userType = (String) claims.get("role");
        if(userType.equals("BOOK_BUYER")) return true;
        else return false;
    }

}
