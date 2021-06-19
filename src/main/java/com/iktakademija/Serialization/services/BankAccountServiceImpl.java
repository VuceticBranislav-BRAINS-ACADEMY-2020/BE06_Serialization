package com.iktakademija.Serialization.services;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktakademija.Serialization.entities.BankAccount;
import com.iktakademija.Serialization.repositories.BankAccountRepositry;
import com.iktakademija.Serialization.repositories.UserRepository;

@Service
public class BankAccountServiceImpl implements BankAccountService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BankAccountRepositry accountRepository;

	//utility method for generating random account numbers
	public String generateRandomAcoountNumber() {
		Random rand = new Random();
		String card = "SRB";
		for (int i = 0; i < 14; i++) {
			int n = rand.nextInt(10) + 0;
			card += Integer.toString(n);
		}
		return card;
	}

	/**
	 * makes a new account and assigns to user, saves account and user in db
	 * @return AccountEntity
	 * @param Integer userID
	 * 
	 */
	@Override
	public BankAccount createAccount(Integer userID) {

		//check for valid input and user in db
		if (userRepository.findById(userID).isPresent()) {
			// create user
			BankAccount account = new BankAccount();
			account.setIban(generateRandomAcoountNumber());
			account.setBalance(0.00);
			account.setUser(userRepository.findById(userID).get());
			userRepository.save(userRepository.findById(userID).get());
			return accountRepository.save(account);
		}
		return null;
	}

	@Override
	public BankAccount updateAccount(Integer accountID, BankAccount account) {
		Optional<BankAccount> accountFromDB = accountRepository.findById(accountID);

		if (accountFromDB.isPresent()) {
			if (account.getIban() != null) {
				accountFromDB.get().setIban(account.getIban());
			}
			if (account.getBalance() != null) {
				accountFromDB.get().setBalance(account.getBalance());
			}
			if (account.getUser() != null) {
				accountFromDB.get().setUser(account.getUser());
			}
			accountRepository.save(accountFromDB.get());
		}
		return null;
	}

}
