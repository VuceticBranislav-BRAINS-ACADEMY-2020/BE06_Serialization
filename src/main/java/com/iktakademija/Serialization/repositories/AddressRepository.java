package com.iktakademija.Serialization.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.iktakademija.Serialization.entities.AddressEntity;

public interface AddressRepository extends CrudRepository<AddressEntity, Integer>{
	
	// Find random record from table
	@Query(nativeQuery = true, value = "SELECT * FROM address ORDER BY rand() LIMIT 1")
	AddressEntity findRandom();
}
