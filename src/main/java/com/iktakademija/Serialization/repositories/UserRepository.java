package com.iktakademija.Serialization.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.iktakademija.Serialization.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer>{
	
	List<UserEntity> findAll();
	Optional<UserEntity> findById(Integer id);
	Optional<UserEntity> findByEmail(String email);
}
