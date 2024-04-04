package ba.unsa.etf.nbp.bookstorebackend.repository;

import ba.unsa.etf.nbp.bookstorebackend.database.DatabaseService;
import ba.unsa.etf.nbp.bookstorebackend.mapper.ImageMapper;
import ba.unsa.etf.nbp.bookstorebackend.mapper.PublisherMapper;
import ba.unsa.etf.nbp.bookstorebackend.projection.ImageProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.PublisherProjection;
import ba.unsa.etf.nbp.bookstorebackend.statements.ImageStatements;
import ba.unsa.etf.nbp.bookstorebackend.statements.PublisherStatements;
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
public class ImageRepository {
    private static Logger LOGGER = LoggerFactory.getLogger(ImageRepository.class);
    @Autowired
    protected DatabaseService databaseService;

    public List<ImageProjection> findAll() {
        Connection connection = databaseService.getConnection();
        ResultSet rs = ImageStatements.findAllImages(connection);
        if (rs == null) {
            return new ArrayList<>();
        }

        List<ImageProjection> imageProjections = new ArrayList<>();
        try {
            while (rs.next()) {
                imageProjections.add(ImageMapper.createImage(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return imageProjections;

    }

}
