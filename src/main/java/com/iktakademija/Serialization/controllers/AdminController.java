package com.iktakademija.Serialization.controllers;

import java.time.LocalDate;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktakademija.Serialization.entities.AddressEntity;
import com.iktakademija.Serialization.entities.BankAccount;
import com.iktakademija.Serialization.entities.UserEntity;
import com.iktakademija.Serialization.repositories.AddressRepository;
import com.iktakademija.Serialization.repositories.BankAccountRepositry;
import com.iktakademija.Serialization.repositories.UserRepository;

import net.andreinc.mockneat.unit.address.Addresses;
import net.andreinc.mockneat.unit.address.Cities;
import net.andreinc.mockneat.unit.address.Countries;
import net.andreinc.mockneat.unit.financial.IBANs;
import net.andreinc.mockneat.unit.time.LocalDates;
import net.andreinc.mockneat.unit.user.Emails;
import net.andreinc.mockneat.unit.user.Names;
import net.andreinc.mockneat.unit.user.Passwords;

@RestController
@RequestMapping(path = "/api/v1/users/admin")
public class AdminController {

	@Autowired
	BankAccountRepositry bankAccountRepositry;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	// Populate base
	@RequestMapping(method = RequestMethod.POST, path = "/add")
	public ResponseEntity<?> poulateDataBase() {
		populateAddresses();
		populateUsers();	
		populateAccount();
		return new ResponseEntity<String>("Database updated.", HttpStatus.OK);
	}
	
	// Generate addresses entries to database.
	// Do not guarantee exact number of entries.
	private void populateAddresses() {
		
		// Declarations
		AddressEntity address;
		
		// Add address to list
		for (int i = 0; i < 15; i++) {
			// Generate data
			address = new AddressEntity();			
			address.setStreet	(Addresses.addresses().get());
			address.setCity		(Cities.cities().capitalsAmerica().get());
			address.setCountry	(Countries.countries().get());
			
			// Add to database
			addressRepository.save(address);
		}
	}
	
	// Generate user entries to database.
	// Do not guarantee exact number of entries.
	private void populateUsers() {
		
		// Declarations
		UserEntity user;
		
		// Add customers to list
		for (int i = 0; i < 15; i++) {		
			
			// Generate data
			user = new UserEntity();			
			user.setName(Names.names().full().get());
			user.setDateOfBirth(LocalDates.localDates().between(LocalDate.of(1918, 1, 1), LocalDate.of(2000, 12, 31)).get());
			user.setEmail(Emails.emails().get());
			user.setPassword(Passwords.passwords().get());
			user.setAddress(addressRepository.findRandom());
			
			// Add to database
			userRepository.save(user);
		}		
	}
	
	// Generate bank acount entries to database.
	// Do not guarantee exact number of entries.
	private void populateAccount() {
		
		// Declarations
		BankAccount account;
		Random rnd = new Random();
		
		// Add customers to list
		for (int i = 0; i < 15; i++) {		

			// Generate data
			account = new BankAccount();				
			account.setBalance(Math.round(rnd.nextDouble()*9000 + 1000 * 100) / 100.0);
			account.setIban(IBANs.ibans().get());
			account.setUser(userRepository.findRandom());
			
			// Add to database
			bankAccountRepositry.save(account);
		}
	}
	
}
