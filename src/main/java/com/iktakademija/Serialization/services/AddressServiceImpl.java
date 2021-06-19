package com.iktakademija.Serialization.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktakademija.Serialization.controllers.dto.AddressDTO;
import com.iktakademija.Serialization.entities.AddressEntity;
import com.iktakademija.Serialization.repositories.AddressRepository;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressRepository addressRepository;
	
	@Override
	public AddressDTO convertEntityToDTO(AddressEntity address) {
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setStreet(address.getStreet());
		addressDTO.setCity(address.getCity());
		addressDTO.setCountry(address.getCountry());
		return addressDTO;
	}

	@Override
	public AddressEntity createAddress(AddressEntity address) {
		AddressEntity newAddress = new AddressEntity();
		if (address.getStreet() != null) {
			newAddress.setStreet(address.getStreet());
		}
		if (address.getCity() != null) {
			newAddress.setCity(address.getCity());
		}
		if (address.getCountry() != null) {
			newAddress.setCountry(address.getCountry());
		}
		if (address.getUsers() != null) {
			newAddress.setUsers(address.getUsers());
		}
		return addressRepository.save(newAddress);
	}

	@Override
	public AddressEntity updateAddress(Integer addressID, AddressEntity address) {
		Optional<AddressEntity> addressFromDB = addressRepository.findById(addressID);

		if (addressFromDB.isPresent()) {
			if (address.getCity() != null) {
				addressFromDB.get().setCity(address.getCity());
			}
			if (address.getCountry() != null) {
				addressFromDB.get().setCountry(address.getCountry());
			}
			if (address.getStreet() != null) {
				addressFromDB.get().setStreet(address.getStreet());
			}
			if (address.getUsers() != null) {
				addressFromDB.get().setUsers(address.getUsers());
			}

			addressRepository.save(addressFromDB.get());
		}
		return null;
	}

}
