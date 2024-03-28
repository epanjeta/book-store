package ba.unsa.etf.nbp.bookstorebackend.repository;

import ba.unsa.etf.nbp.bookstorebackend.Role;
import ba.unsa.etf.nbp.bookstorebackend.builder.SelectQueryBuilder;
import ba.unsa.etf.nbp.bookstorebackend.constants.RoleFields;
import ba.unsa.etf.nbp.bookstorebackend.constants.UserFields;
import ba.unsa.etf.nbp.bookstorebackend.database.DatabaseService;
import ba.unsa.etf.nbp.bookstorebackend.mapper.UserMapper;
import ba.unsa.etf.nbp.bookstorebackend.projection.AddressProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.UserProjection;
import ba.unsa.etf.nbp.bookstorebackend.statements.UserStatements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** The user repository */
@Repository
public class UserRepository {
    private static Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);
    @Autowired protected DatabaseService databaseService;
    @Autowired protected AddressRepository addressRepository;
    protected UserStatements userStatements = new UserStatements();

    /**
     * Fetches all basic user data
     *
     * @return the list of all users
     */
    public List<UserProjection> findAll(Role role) {
        Connection connection = databaseService.getConnection();
        try {
            ResultSet rs = userStatements.findAllUsers(role.getDatabaseId());
            List<UserProjection> userProjections = new ArrayList<>();
            if (rs.next()) {
                AddressProjection addressProjection = null;
                if(rs.getObject("ADDRESS_ID") != null) {
                    int addressId = rs.getInt("ADDRESS_ID");
                    addressProjection = addressRepository.findById(addressId);
                }
                userProjections.add(
                        UserMapper.createUserFromResultSet(rs, rs.getObject(6, Role.class), addressProjection));
            }

            return userProjections;
        } catch (SQLException e) {
            LOGGER.error("Exception when acquiring JDBC connection for class: "
                    + UserRepository.class);
            throw new RuntimeException(e);
        }

    }
}
