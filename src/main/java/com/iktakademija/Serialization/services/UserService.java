package com.iktakademija.Serialization.services;

import com.iktakademija.Serialization.entities.UserEntity;

public interface UserService {
	public UserEntity updateUser(Integer id, UserEntity user);
}
