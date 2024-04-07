package ba.unsa.etf.nbp.bookstorebackend.mapper;

import ba.unsa.etf.nbp.bookstorebackend.Role;
import ba.unsa.etf.nbp.bookstorebackend.builder.UserProjectionBuilder;
import ba.unsa.etf.nbp.bookstorebackend.constants.UserFields;
import ba.unsa.etf.nbp.bookstorebackend.projection.AddressProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.UserProjection;
import ba.unsa.etf.nbp.bookstorebackend.repository.AddressRepository;
import ba.unsa.etf.nbp.bookstorebackend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class UserMapper {
    private UserMapper() {
    }

    public static UserProjection createUserFromResultSet(ResultSet resultSet, Role role) {
        return createUser(resultSet)
                .setRole(role)
                .setAddress(AddressMapper.createAddressFromResultSet(resultSet))
                .build();
    }

    private static UserProjectionBuilder createUser(ResultSet resultSet) {
        return UserProjectionBuilder
                .create(resultSet)
                .setId()
                .setFirstName()
                .setLastName()
                .setPassword()
                .setEmail()
                .setUsername()
                .setPhoneNumber()
                .setBirthDay();
    }

}
