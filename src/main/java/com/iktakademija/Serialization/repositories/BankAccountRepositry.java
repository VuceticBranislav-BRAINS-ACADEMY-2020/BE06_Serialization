package com.iktakademija.Serialization.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.iktakademija.Serialization.entities.BankAccount;

public interface BankAccountRepositry extends CrudRepository<BankAccount, Integer>{

	@Query("FROM account AS a WHERE a.user.id=:id")
	List<BankAccount> findAllByUserId(@Param("id") Integer id);
}
