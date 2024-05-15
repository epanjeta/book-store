package ba.unsa.etf.nbp.bookstorebackend.repository;

import ba.unsa.etf.nbp.bookstorebackend.Role;
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
    @Autowired
    protected DatabaseService databaseService;
    @Autowired
    protected AddressRepository addressRepository;

    /**
     * Fetches all basic user data
     *
     * @return the list of all users
     */
    public List<UserProjection> findAll() {
        Connection connection = databaseService.getConnection();
        try {
            ResultSet rs = UserStatements.findAllUsers(connection);
            return getUserProjections(rs);
        } catch (SQLException e) {
            LOGGER.error("Exception when acquiring JDBC connection for class: " + UserRepository.class);
            throw new RuntimeException(e);
        }

    }

    /**
     * Fetches all basic user data
     *
     * @return the list of all users
     */
    public List<UserProjection> findAllWithAddress(Role role) {
        Connection connection = databaseService.getConnection();
        try {
            ResultSet rs = UserStatements.findAllUsersWithAddresses(connection, role);
            return getUserProjections(rs);
        } catch (SQLException e) {
            LOGGER.error("Exception when acquiring JDBC connection for class: " + UserRepository.class);
            throw new RuntimeException(e);
        }

    }

    public UserProjection findUserWithAddress(Role role, int id) {
        Connection connection = databaseService.getConnection();
        try {
            ResultSet rs = UserStatements.findUserWithAddress(connection, role, id);
            return getUserProjections(rs).get(0);
        } catch (SQLException e) {
            LOGGER.error("Exception when acquiring JDBC connection for class: " + UserRepository.class);
            throw new RuntimeException(e);
        }

    }

    private List<UserProjection> getUserProjections(ResultSet rs) throws SQLException {
        if (rs == null) {
            return new ArrayList<>();
        }
        List<UserProjection> userProjections = new ArrayList<>();
        while (rs.next()) {
            userProjections
                    .add(UserMapper.createUserFromResultSet(rs, rs.getObject(7, Role.class)));
        }

        return userProjections;
    }
}
