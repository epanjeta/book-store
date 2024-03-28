package ba.unsa.etf.nbp.bookstorebackend.repository;

import ba.unsa.etf.nbp.bookstorebackend.Role;
import ba.unsa.etf.nbp.bookstorebackend.database.DatabaseService;
import ba.unsa.etf.nbp.bookstorebackend.mapper.AddressMapper;
import ba.unsa.etf.nbp.bookstorebackend.mapper.UserMapper;
import ba.unsa.etf.nbp.bookstorebackend.projection.AddressProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.UserProjection;
import ba.unsa.etf.nbp.bookstorebackend.statements.AddressStatements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AddressRepository {

    private static Logger LOGGER = LoggerFactory.getLogger(UserRepository.class);
    @Autowired
    protected DatabaseService databaseService;
    protected AddressStatements addressStatements = new AddressStatements();

    public AddressProjection findById(int id){
        try {
            ResultSet rs = addressStatements.findById(id);

            if (rs.next()) {
                return AddressMapper.createAddressFromResultSet(rs);
            }
            else return null;
        } catch (SQLException e) {
            LOGGER.error("Exception when acquiring JDBC connection for class: "
                    + UserRepository.class);
            throw new RuntimeException(e);
        }
    }
}
