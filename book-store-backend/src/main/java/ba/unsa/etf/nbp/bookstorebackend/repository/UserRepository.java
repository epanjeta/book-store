package ba.unsa.etf.nbp.bookstorebackend.repository;

import ba.unsa.etf.nbp.bookstorebackend.Role;
import ba.unsa.etf.nbp.bookstorebackend.builder.SelectQueryBuilder;
import ba.unsa.etf.nbp.bookstorebackend.constants.RoleFields;
import ba.unsa.etf.nbp.bookstorebackend.constants.UserFields;
import ba.unsa.etf.nbp.bookstorebackend.database.DatabaseService;
import ba.unsa.etf.nbp.bookstorebackend.mapper.UserMapper;
import ba.unsa.etf.nbp.bookstorebackend.projection.UserProjection;
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

    /**
     * Fetches all basic user data
     *
     * @return the list of all users
     */
    public List<UserProjection> findAll(Role role) {
        Connection connection = databaseService.getConnection();

        try {
            Statement statement = connection.createStatement();
            String selectAllUsers = SelectQueryBuilder.create()
                    .select(UserFields.FIRST_NAME,
                            UserFields.LAST_NAME,
                            UserFields.EMAIL,
                            UserFields.USERNAME,
                            UserFields.BIRTH_DATE,
                            RoleFields.NAME)
                    .from(UserFields.TABLE_NAME,
                            "INNER JOIN " + RoleFields.TABLE_NAME + " ON "
                                    + UserFields.ROLE_ID + " = " + RoleFields.ID)
                    .where(UserFields.ROLE_ID + " = " + role.getDatabaseId())
                    .build();
            ResultSet rs = statement.executeQuery(selectAllUsers);
            List<UserProjection> userProjections = new ArrayList<>();
            while (rs.next()) {
                userProjections.add(
                        UserMapper.createUserFromResultSet(rs, rs.getObject(6, Role.class)));
            }

            return userProjections;
        } catch (SQLException e) {
            LOGGER.error("Exception when acquiring JDBC connection for class: "
                    + UserRepository.class);
            throw new RuntimeException(e);
        }

    }
}
