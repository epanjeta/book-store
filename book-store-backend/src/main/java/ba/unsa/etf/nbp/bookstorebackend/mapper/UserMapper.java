package ba.unsa.etf.nbp.bookstorebackend.mapper;

import ba.unsa.etf.nbp.bookstorebackend.Role;
import ba.unsa.etf.nbp.bookstorebackend.constants.UserFields;
import ba.unsa.etf.nbp.bookstorebackend.projection.AddressProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.UserProjection;
import ba.unsa.etf.nbp.bookstorebackend.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class UserMapper {

    private UserMapper() {}



    public static UserProjection createUserFromResultSet(ResultSet resultSet, Role role) {
        try {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            UserProjection userProjection = new UserProjection();
            userProjection.setRole(role);
            for (int i = 1; i < resultSetMetaData.getColumnCount() + 1; i++) {
                switch (resultSetMetaData.getColumnName(i)) {
                    case UserFields.ID:
                        userProjection.setId(resultSet.getInt(i));
                        break;
                    case UserFields.FIRST_NAME:
                        userProjection.setFirstName(resultSet.getString(i));
                        break;
                    case UserFields.LAST_NAME:
                        userProjection.setLastName(resultSet.getString(i));
                        break;
                    case UserFields.EMAIL:
                        userProjection.setEmail(resultSet.getString(i));
                        break;
                    case UserFields.PASSWORD:
                        userProjection.setPassword(resultSet.getString(i));
                        break;
                    case UserFields.USERNAME:
                        userProjection.setUserName(resultSet.getString(i));
                        break;
                    case UserFields.PHONE_NUMBER:
                        userProjection.setPhoneNumber(resultSet.getString(i));
                        break;
                    case UserFields.BIRTH_DATE:
                        userProjection.setBirthDay(resultSet.getObject(i, LocalDate.class));
                        break;
                }
            }

            return userProjection;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public static UserProjection createUserFromResultSet(ResultSet resultSet, Role role, AddressProjection addressProjection){
        try {
            int id = resultSet.getInt("ID");
            String firstName = resultSet.getString("FIRST_NAME");
            String lastName = resultSet.getString("LAST_NAME");
            String email = resultSet.getString("EMAIL");
            String password = resultSet.getString("PASSWORD");
            String username = resultSet.getString("USERNAME");
            String phoneNumber = resultSet.getString("PHONE_NUMBER");
            LocalDate birthDate = resultSet.getObject("BIRTH_DATE", LocalDate.class);

            UserProjection user = new UserProjection(id, firstName, lastName, email, username, password, phoneNumber, birthDate, role, addressProjection);

            return user;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }
}
