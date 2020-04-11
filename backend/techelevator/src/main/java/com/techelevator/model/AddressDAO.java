package com.techelevator.model;

public interface AddressDAO {
	
	public void createAddress(Address address);
	public Address getAddressById(String addressId);
	public void updateAddressById(String addressId, Address updatedAddress);
	public void deleteAddressById(String addressId);

}
