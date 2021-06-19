package com.iktakademija.Serialization.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.iktakademija.Serialization.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer>{
	
	List<UserEntity> findAll();
	List<UserEntity> findAllByEmail(String email);
	Optional<UserEntity> findById(Integer id);
	Optional<UserEntity> findByEmail(String email);
	
	// Find random record from table
	@Query(nativeQuery = true, value = "SELECT * FROM user ORDER BY rand() LIMIT 1")
	UserEntity findRandom();
}
