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
import com.iktakademija.Serialization.controllers.dto.UserRegisterDTO;
import com.iktakademija.Serialization.controllers.util.RESTError;
import com.iktakademija.Serialization.entities.UserEntity;
import com.iktakademija.Serialization.repositories.UserEntityRepository;
import com.iktakademija.Serialization.security.Views;

@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {
	
	@Autowired
	UserEntityRepository userEntityRepository;
	
	@JsonView(Views.Public.class)
	@RequestMapping(method = RequestMethod.GET, path = {"/public", ""})
	public ResponseEntity<?> getAllUsersPublic() {
		
		List<UserEntity> retVal = userEntityRepository.findAll();
		
		if (retVal.size() == 0) {
			//return new ResponseEntity<String>("NotFound", HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else
		{
			return new ResponseEntity<List<UserEntity>>(retVal, HttpStatus.OK);
		}
	}
	
	@JsonView(Views.Private.class)
	@RequestMapping(method = RequestMethod.GET, path = "/private")
	public List<UserEntity> getAllUsersPrivate() {
		return userEntityRepository.findAll();
	}
	
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET, path = "/admin")
	public List<UserEntity> getAllUsersAdmin() {
		return userEntityRepository.findAll();
	}
	
	/** 
	 * Ovo je dobro raditi i na projektu
	 * Dodati kodove errora
	 * @param id - znaci to i to
	 * @return - vraca to i to
	 */
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public ResponseEntity<?> getUserByIdAdmin(@PathVariable Integer id) {
		try {
			List<UserEntity> users = userEntityRepository.findAll();

			for (UserEntity userEntity : users) {
				if (userEntity.getId().equals(id))

					return new ResponseEntity<UserEntity>(userEntity, HttpStatus.OK);
			}
			// return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			// return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);

			return new ResponseEntity<RESTError>(new RESTError("User not found", 1), HttpStatus.NOT_FOUND);

		} catch (Exception e) { // u slucaju izuzetka vratiti 500
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred:" + e.getMessage(), 2), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@JsonView(Views.Public.class)
	@RequestMapping(method = RequestMethod.POST, path = "")
	public ResponseEntity<?> saveUser(@RequestBody UserRegisterDTO userRegisterDTO) {
		try {
			UserEntity userEntity = new UserEntity();
			userEntity.setEmail(userRegisterDTO.getEmail());
			userEntity.setName(userRegisterDTO.getName());

			userEntityRepository.save(userEntity);
			return new ResponseEntity<UserEntity>(userEntity, HttpStatus.CREATED);
		
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred:" + e.getMessage(), 2),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	
	
	@JsonView(Views.Private.class)
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}/private")
	public ResponseEntity<?> updateUserPrivate(
			@PathVariable(name = "id") Integer id, 
			@RequestBody UserRegisterDTO userRegisterDTO) {
		
		try {
			Optional<UserEntity> op = userEntityRepository.findById(id);

			if (op.isPresent() == false) new ResponseEntity<>(HttpStatus.NO_CONTENT);
			UserEntity userEntity = op.get();
			
			if (userRegisterDTO.getName() == null) 			userEntity.setName(userRegisterDTO.getName());
			if (userRegisterDTO.getEmail() == null) 		userEntity.setEmail(userRegisterDTO.getEmail());
			if (userRegisterDTO.getDateOfBirth() == null) 	userEntity.setDateOfBirth(userRegisterDTO.getDateOfBirth());
			if (userRegisterDTO.getPasword() == null) 		userEntity.setPasword(userRegisterDTO.getPasword());
			
			userEntityRepository.save(userEntity);
			return new ResponseEntity<UserEntity>(userEntity, HttpStatus.CREATED);
			
		} catch (Exception e) { 
			return new ResponseEntity<RESTError>(new RESTError("Exception occurred:" + e.getMessage(), 2),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}	
	
}
