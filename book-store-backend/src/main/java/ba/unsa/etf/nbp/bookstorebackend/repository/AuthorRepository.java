package ba.unsa.etf.nbp.bookstorebackend.repository;

import ba.unsa.etf.nbp.bookstorebackend.database.DatabaseService;
import ba.unsa.etf.nbp.bookstorebackend.mapper.AuthorMapper;
import ba.unsa.etf.nbp.bookstorebackend.projection.AddressProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.AuthorProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.AutocompleteProjection;
import ba.unsa.etf.nbp.bookstorebackend.statements.AddressStatements;
import ba.unsa.etf.nbp.bookstorebackend.statements.AuthorStatements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AuthorRepository {
    private static Logger LOGGER = LoggerFactory.getLogger(AuthorRepository.class);
    @Autowired
    protected DatabaseService databaseService;

    public List<AuthorProjection> findAllAuthors() {
        ResultSet rs = AuthorStatements.findAllAuthors(databaseService.getConnection());
        if (rs == null) {
            return new ArrayList<>();
        }

        List<AuthorProjection> addressProjections = new ArrayList<>();
        try {
            while (rs.next()) {
                addressProjections.add(AuthorMapper.createAuthorFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return addressProjections;
    }

    public List<AutocompleteProjection> findAllNationalities() {
        ResultSet rs = AuthorStatements.findAllNationalities(databaseService.getConnection());
        if (rs == null) {
            return new ArrayList<>();
        }

        return AuthorMapper.mapNationalities(rs);
    }

    public AuthorProjection createAuthor(AuthorProjection authorProjection) {
        try {
            if (authorProjection.getNationalityId() == null) {
                int nationalityId = AuthorStatements.createNationality(
                        databaseService.getConnection(),
                        authorProjection.getNationalityName());
                authorProjection.setNationalityId(nationalityId);
            }

            int authorId = AuthorStatements.createAuthor(
                    databaseService.getConnection(),
                    authorProjection.getFirstName(),
                    authorProjection.getLastName(),
                    authorProjection.getBirthDay().toString(),
                    authorProjection.getDeathDay().toString() ,
                    authorProjection.getNationalityId(),
                    authorProjection.getBio());
            authorProjection.setId(authorId);
            databaseService.getConnection().commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return authorProjection;
    }
}
