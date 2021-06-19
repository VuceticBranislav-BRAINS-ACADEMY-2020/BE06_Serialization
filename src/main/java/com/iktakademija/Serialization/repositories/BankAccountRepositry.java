package com.iktakademija.Serialization.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktakademija.Serialization.entities.BankAccount;

public interface BankAccountRepositry extends CrudRepository<BankAccount, Integer>{

}
