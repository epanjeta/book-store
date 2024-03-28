package ba.unsa.etf.nbp.bookstorebackend.mapper;

import ba.unsa.etf.nbp.bookstorebackend.projection.AddressProjection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressMapper {

    public AddressMapper() {
    }

    public static AddressProjection createAddressFromResultSet(ResultSet resultSet){

        try{
            int id = resultSet.getInt("ID");
            String street = resultSet.getString("STREET");
            String zipCode = resultSet.getString("ZIP_CODE");
            //Ostao je još city_id koji se za sad ne mappira

            AddressProjection addressProjection = new AddressProjection(id, street, zipCode);
            return addressProjection;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
