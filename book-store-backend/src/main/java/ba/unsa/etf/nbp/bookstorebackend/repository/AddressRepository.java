package ba.unsa.etf.nbp.bookstorebackend.repository;

import ba.unsa.etf.nbp.bookstorebackend.Role;
import ba.unsa.etf.nbp.bookstorebackend.database.DatabaseService;
import ba.unsa.etf.nbp.bookstorebackend.mapper.AddressMapper;
import ba.unsa.etf.nbp.bookstorebackend.mapper.UserMapper;
import ba.unsa.etf.nbp.bookstorebackend.projection.AddressProjection;
import ba.unsa.etf.nbp.bookstorebackend.projection.AutocompleteProjection;
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
    private static Logger LOGGER = LoggerFactory.getLogger(AddressRepository.class);
    @Autowired
    protected DatabaseService databaseService;


    public List<AddressProjection> findAllAddresses() {
        ResultSet rs = AddressStatements.findAllAddresses(databaseService.getConnection());
        if (rs == null) {
            return new ArrayList<>();
        }

        List<AddressProjection> addressProjections = new ArrayList<>();
        try {
            while (rs.next()) {
                addressProjections.add(AddressMapper.createAddressFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return addressProjections;
    }

    public List<AutocompleteProjection> findAllCities() {
        ResultSet rs = AddressStatements.findAllCities(databaseService.getConnection());
        if (rs == null) {
            return new ArrayList<>();
        }

        return AddressMapper.mapCities(rs);
    }

    public List<AutocompleteProjection> findAllCountries() {
        ResultSet rs = AddressStatements.findAllCountries(databaseService.getConnection());
        if (rs == null) {
            return new ArrayList<>();
        }

        return AddressMapper.mapCountries(rs);
    }

    public AddressProjection createAddress(AddressProjection addressProjection) {
        try {
            if (addressProjection.getCountryId() == null) {
                int countryId = AddressStatements.createCountry(
                        databaseService.getConnection(),
                        addressProjection.getCountryName());
                addressProjection.setCountryId(countryId);
                int cityId = AddressStatements.createCity(
                        databaseService.getConnection(),
                        addressProjection.getCityName(),
                        countryId);
                addressProjection.setCityId(cityId);
            } else if (addressProjection.getCityId() == null) {
                int cityId = AddressStatements.createCity(
                        databaseService.getConnection(),
                        addressProjection.getCityName(),
                        addressProjection.getCountryId());
                addressProjection.setCityId(cityId);
            }

            int addressId = AddressStatements.createAddress(
                    databaseService.getConnection(),
                    addressProjection.getStreet(),
                    addressProjection.getZipCode(),
                    addressProjection.getCityId());
            addressProjection.setAddressId(addressId);
            databaseService.getConnection().commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return addressProjection;
    }
}
