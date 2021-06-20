package com.iktakademija.Serialization.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktakademija.Serialization.controllers.util.RESTError;
import com.iktakademija.Serialization.entities.BankAccount;
import com.iktakademija.Serialization.repositories.BankAccountRepositry;
import com.iktakademija.Serialization.security.Views;
import com.iktakademija.Serialization.services.BankAccountService;

@RestController
@RequestMapping(path = "/api/v1/account")
public class BankAccountController {
	
	@Autowired
	private BankAccountService bankAccountService;

	@Autowired
	private BankAccountRepositry bankAccountRepositry;

	@JsonView(Views.Private.class)
	@RequestMapping(method = RequestMethod.POST, value = "/{userID}/private")
	public ResponseEntity<?> createAccount(@PathVariable Integer userID) {
		//check for valid user input
		if (userID == null) {
			return new ResponseEntity<RESTError>(new RESTError("User not found, please check the input!", 1),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<BankAccount>(bankAccountService.createAccount(userID), HttpStatus.CREATED);
	}

	@JsonView(Views.Private.class)
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> getAccountbyID(@PathVariable(name = "id") Integer accountID) {
		//
		if (accountID == null) {
			return new ResponseEntity<RESTError>(new RESTError("Bad request", 2), HttpStatus.BAD_REQUEST);
		}
		if (bankAccountRepositry.findById(accountID).isPresent() == false) {
			return new ResponseEntity<RESTError>(new RESTError("Account not found", 1), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<BankAccount>(bankAccountRepositry.findById(accountID).get(), HttpStatus.OK);
	}
	
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllAccounts() {
		List<BankAccount> accounts = (List<BankAccount>) bankAccountRepositry.findAll();

		if (accounts.size() == 0) {
			// return 404
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			// return 200
			return new ResponseEntity<List<BankAccount>>(accounts, HttpStatus.OK);
		}
	}

	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{accountID}")
	public ResponseEntity<?> updateAccount(@PathVariable Integer accountID, @RequestBody BankAccount account) {
		//check for valid user input
		if (accountID == null) {
			return new ResponseEntity<RESTError>(new RESTError("Account not found, please check the input!", 1),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<BankAccount>(bankAccountService.updateAccount(accountID, account), HttpStatus.OK);
	}

	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> removeAccountbyID(@PathVariable(name = "id") Integer accountID) {
		//check for valid address input
		if (accountID == null) {
			return new ResponseEntity<RESTError>(new RESTError("Account not found, please check the input!", 1),
					HttpStatus.BAD_REQUEST);
		}
		
		Optional<BankAccount> op = bankAccountRepositry.findById(accountID);
		if (op.isPresent() == false) return null;
		BankAccount accountForDeletion = op.get();
		
		bankAccountRepositry.delete(accountForDeletion);
		return new ResponseEntity<BankAccount>(accountForDeletion, HttpStatus.OK);
	}
}
