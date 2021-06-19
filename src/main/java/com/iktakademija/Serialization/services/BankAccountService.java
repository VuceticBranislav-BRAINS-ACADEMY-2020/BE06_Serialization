package com.iktakademija.Serialization.services;

import com.iktakademija.Serialization.entities.BankAccount;

public interface BankAccountService {
	public BankAccount createAccount(Integer userID);
	public BankAccount updateAccount(Integer accountID, BankAccount account);
}
