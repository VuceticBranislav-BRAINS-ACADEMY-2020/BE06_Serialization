package com.iktakademija.Serialization.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktakademija.Serialization.entities.UserEntity;
import com.iktakademija.Serialization.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserEntity updateUser(Integer userID, UserEntity user) {

		// check for existance in db
		Optional<UserEntity> userFromDB = userRepository.findById(userID);
		if (userFromDB.isPresent()) {
			// update user where parameters are valid
			if (user.getName() != null) {
				userFromDB.get().setName(user.getName());
			}
			if (user.getEmail() != null) {
				userFromDB.get().setEmail(user.getEmail());
			}
			if (user.getDateOfBirth() != null) {
				userFromDB.get().setDateOfBirth(user.getDateOfBirth());
			}
			if (user.getPassword() != null) {
				userFromDB.get().setPassword(user.getPassword());
			}
			if (user.getAddress() != null) {
				userFromDB.get().setAddress(user.getAddress());
			}
			userRepository.save(userFromDB.get());
		}
		return null;
	}
}
