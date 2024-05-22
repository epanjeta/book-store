package ba.unsa.etf.nbp.bookstorebackend.repository;

import ba.unsa.etf.nbp.bookstorebackend.constants.BookFields;
import ba.unsa.etf.nbp.bookstorebackend.database.DatabaseService;
import ba.unsa.etf.nbp.bookstorebackend.mapper.CartItemMapper;
import ba.unsa.etf.nbp.bookstorebackend.projection.AutocompleteProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.CartItem;
import ba.unsa.etf.nbp.bookstorebackend.statements.CartItemStatements;
import ba.unsa.etf.nbp.bookstorebackend.statements.CommonStatements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CommonRepository {

    private static Logger LOGGER = LoggerFactory.getLogger(CommonRepository.class);
    @Autowired
    protected DatabaseService databaseService;
    public List<AutocompleteProjection> getGenres()  {
        Connection connection = databaseService.getConnection();
        String sql = "select GENRE from NBP24T3.NBP_GENRE" ;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<AutocompleteProjection> autocompleteProjections
                    = new ArrayList<>();
            while (resultSet.next()) {
                String genre = resultSet.getString(1);
                autocompleteProjections.add(
                        new AutocompleteProjection(genre)
                );
            }
            return autocompleteProjections;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public List<AutocompleteProjection> getLanguages()  {
        Connection connection = databaseService.getConnection();
        String sql = "select CODE, NAME from NBP24T3.NBP_LANGUAGE" ;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<AutocompleteProjection> autocompleteProjections
                    = new ArrayList<>();
            while (resultSet.next()) {
                String code = resultSet.getString(1);
                String name = resultSet.getString(2);
                autocompleteProjections.add(
                        new AutocompleteProjection(code, name)
                );
            }
            return autocompleteProjections;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
