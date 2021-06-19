package com.iktakademija.Serialization.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktakademija.Serialization.controllers.dto.AddressDTO;
import com.iktakademija.Serialization.controllers.util.RESTError;
import com.iktakademija.Serialization.entities.AddressEntity;
import com.iktakademija.Serialization.repositories.AddressRepository;
import com.iktakademija.Serialization.security.Views;
import com.iktakademija.Serialization.services.AddressService;

public class AddressController {
	
	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private AddressService addressService;

	@JsonView(Views.Private.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createAddress(@RequestBody AddressEntity address) {
		if (address.getStreet() == null || address.getCity() == null || address.getCountry() == null) {
			return new ResponseEntity<RESTError>(new RESTError("Address not valid!", 1),
					HttpStatus.BAD_REQUEST);
		}
		AddressDTO addressDTO = addressService.convertEntityToDTO(addressService.createAddress(address));
		return new ResponseEntity<AddressDTO>(addressDTO, HttpStatus.CREATED);
	}


	@JsonView(Views.Private.class)
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> findAddressbyID(@PathVariable Integer addressID) {
		if (addressID == null) {
			return new ResponseEntity<RESTError>(new RESTError("Bad request!", 2), HttpStatus.BAD_REQUEST);
		}
		if (addressRepository.findById(addressID).isPresent() == false) {
			return new ResponseEntity<RESTError>(new RESTError("Address not found!", 1), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<AddressEntity>(addressRepository.findById(addressID).get(), HttpStatus.OK);
	}

	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> findAllAddress() {
		List<AddressEntity> addresses = (List<AddressEntity>) addressRepository.findAll();

		if (addresses.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<AddressEntity>>(addresses, HttpStatus.OK);
		}
	}

	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateAddress(@PathVariable Integer addressID, @RequestBody AddressEntity address) {
		if (addressID == null) {
			return new ResponseEntity<RESTError>(new RESTError("Address not found!", 1),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<AddressEntity>(addressService.updateAddress(addressID, address), HttpStatus.OK);
	}

	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> removeAddressbyID(@PathVariable Integer addressID) {
		if (addressID == null) {
			return new ResponseEntity<RESTError>(new RESTError("Address not found!", 1),
					HttpStatus.BAD_REQUEST);
		}
		AddressEntity addressForDeletion = addressRepository.findById(addressID).get();
		addressRepository.delete(addressForDeletion);
		return new ResponseEntity<AddressEntity>(addressForDeletion, HttpStatus.OK);
	}
}
