package com.techelevator.model;

import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class AddressJDBCDAO implements AddressDAO {
	
	private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AddressJDBCDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    private Address mapRowToAddress(SqlRowSet result) {
    	Address address = new Address();
    	address.setId(result.getString("id"));
    	address.setStreet(result.getString("street"));
    	address.setSuite(result.getString("suite"));
    	address.setCity(result.getString("city"));
    	address.setRegion(result.getString("region"));
    	address.setPostal(result.getString("postal"));
    	address.setCountry(result.getString("country"));
    	return address;
    }
    
    public String generateUUID() {
    	UUID uuid = UUID.randomUUID();
    	return "" + uuid;
    }

	@Override
	public void createAddress(Address address) {
		String sql = "INSERT INTO addresses (id, street, suite, city, region, postal, country) VALUES (?, ?, ?, ?, ?, ?, ?)";
		address.setId(generateUUID());
		jdbcTemplate.update(sql, address.getId(), address.getStreet(), address.getSuite(), address.getCity(), 
				address.getRegion(), address.getPostal(), address.getCountry());
	}

	@Override
	public Address getAddressById(String addressId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateAddressById(String addressId, Address updatedAddress) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAddressById(String addressId) {
		// TODO Auto-generated method stub

	}

}
