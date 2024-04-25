package ba.unsa.etf.nbp.bookstorebackend.repository;

import ba.unsa.etf.nbp.bookstorebackend.Role;
import ba.unsa.etf.nbp.bookstorebackend.database.DatabaseService;
import ba.unsa.etf.nbp.bookstorebackend.mapper.AuthorMapper;
import ba.unsa.etf.nbp.bookstorebackend.mapper.PublisherMapper;
import ba.unsa.etf.nbp.bookstorebackend.projection.AddressProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.AuthorProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.PublisherProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.UserProjection;
import ba.unsa.etf.nbp.bookstorebackend.statements.AddressStatements;
import ba.unsa.etf.nbp.bookstorebackend.statements.AuthorStatements;
import ba.unsa.etf.nbp.bookstorebackend.statements.PublisherStatements;
import ba.unsa.etf.nbp.bookstorebackend.statements.UserStatements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PublisherRepository {
    private static Logger LOGGER = LoggerFactory.getLogger(PublisherRepository.class);
    @Autowired
    protected DatabaseService databaseService;
    @Autowired
    protected AddressRepository addressRepository;

    public List<PublisherProjection> findAll() {
        Connection connection = databaseService.getConnection();
        ResultSet rs = PublisherStatements.findAllPublishers(connection);
        if (rs == null) {
            return new ArrayList<>();
        }

        List<PublisherProjection> publisherProjections = new ArrayList<>();
        try {
            while (rs.next()) {
                publisherProjections.add(PublisherMapper.createPublisherFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return publisherProjections;

    }

    public List<PublisherProjection> findAllWithAddress() {
        Connection connection = databaseService.getConnection();
        ResultSet rs = PublisherStatements.findAllPublishersWithAddresses(connection);
        if (rs == null) {
            return new ArrayList<>();
        }

        List<PublisherProjection> publisherProjections = new ArrayList<>();
        try {
            while (rs.next()) {
                publisherProjections.add(PublisherMapper.createPublisherFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return publisherProjections;

    }

    public PublisherProjection createPublisher(PublisherProjection publisherProjection) {
        try {
            int publisherId = PublisherStatements.createPublisher(
                    databaseService.getConnection(),
                    publisherProjection.getName(),
                    publisherProjection.getEmail(),
                    publisherProjection.getPhone(),
                    publisherProjection.getAddressProjection().getAddressId()
            );
            publisherProjection.setId(publisherId);
            databaseService.getConnection().commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return publisherProjection;
    }

    public PublisherProjection createPublisherWithAddress(PublisherProjection publisherProjection) {
        try {
            AddressProjection address = publisherProjection.getAddressProjection();
            if (publisherProjection.getAddressProjection().getAddressId() == null ) {
                address = addressRepository.createAddress(publisherProjection.getAddressProjection());
            }

            int publisherId = PublisherStatements.createPublisher(
                    databaseService.getConnection(),
                    publisherProjection.getName(),
                    publisherProjection.getEmail(),
                    publisherProjection.getPhone(),
                    address.getAddressId()
            );
            publisherProjection.setId(publisherId);
            databaseService.getConnection().commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return publisherProjection;
    }

    public PublisherProjection updatePublisher(PublisherProjection publisherProjection) {
        try {
            AddressProjection address = publisherProjection.getAddressProjection();
            if (publisherProjection.getAddressProjection().getAddressId() == null ) {
                address = addressRepository.createAddress(publisherProjection.getAddressProjection());
            }

            int publisherId = PublisherStatements.updatePublisher(
                    databaseService.getConnection(),
                    publisherProjection.getName(),
                    publisherProjection.getEmail(),
                    publisherProjection.getPhone(),
                    address.getAddressId(),
                    publisherProjection.getId()
            );
            publisherProjection.setId(publisherId);
            databaseService.getConnection().commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return publisherProjection;
    }
}
