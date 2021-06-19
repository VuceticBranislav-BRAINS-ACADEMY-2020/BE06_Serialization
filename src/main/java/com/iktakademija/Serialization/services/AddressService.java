package com.iktakademija.Serialization.services;

import com.iktakademija.Serialization.controllers.dto.AddressDTO;
import com.iktakademija.Serialization.entities.AddressEntity;

public interface AddressService {	

	public AddressEntity createAddress(AddressEntity address);
	public AddressEntity updateAddress(Integer id, AddressEntity address);
	public AddressDTO convertEntityToDTO(AddressEntity address);
}
