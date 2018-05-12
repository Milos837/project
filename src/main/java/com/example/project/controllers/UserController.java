package com.example.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.controllers.util.RESTError;
import com.example.project.entities.UserEntity;
import com.example.project.entities.enums.EUserRole;
import com.example.project.repositories.UserRepository;
import com.example.project.security.Views;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping(value = "/api/v1/project/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	// Vrati sve korisnike TESTIRAO
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<?> getClients() {
		return new ResponseEntity<List<UserEntity>>((List<UserEntity>) userRepository.findAll(), HttpStatus.OK);
	}
	
	@JsonView(Views.Public.class)
	@GetMapping(value = "/public/")
	public ResponseEntity<?> getClientsPublic() {
		return new ResponseEntity<List<UserEntity>>((List<UserEntity>) userRepository.findAll(), HttpStatus.OK);
	}
	
	@JsonView(Views.Private.class)
	@GetMapping(value = "/private/")
	public ResponseEntity<?> getClientsPrivate() {
		return new ResponseEntity<List<UserEntity>>((List<UserEntity>) userRepository.findAll(), HttpStatus.OK);
	}
	
	@JsonView(Views.Admin.class)
	@GetMapping(value = "/admin/")
	public ResponseEntity<?> getClientsAdmin() {
		return new ResponseEntity<List<UserEntity>>((List<UserEntity>) userRepository.findAll(), HttpStatus.OK);
	}

	// Vrati korisnika po ID-u TESTIRAO
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getClientById(@PathVariable Integer id) {
		if (userRepository.existsById(id)) {
			return new ResponseEntity<UserEntity>(userRepository.findById(id).get(), HttpStatus.OK);
		}
		return new ResponseEntity<RESTError>(new RESTError(2, "User not found"), HttpStatus.NOT_FOUND);
	}

	// Dodaj novog korisnika TESTIRAO
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<?> addUser(@RequestBody UserEntity user) {
		return new ResponseEntity<UserEntity>(userRepository.save(user), HttpStatus.OK);
	}

	// Azuriraj podatke korisnika po ID-u TESTIRAO
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody UserEntity user) {
		if (userRepository.existsById(id)) {
			UserEntity userEntity = userRepository.findById(id).get();
			if (user.getFirstName() != null) {
				userEntity.setFirstName(user.getFirstName());
			}
			if (user.getLastName() != null) {
				userEntity.setLastName(user.getLastName());
			}
			if (user.getEmail() != null) {
				userEntity.setEmail(user.getEmail());
			}
			if (user.getUsername() != null) {
				userEntity.setUsername(user.getUsername());
			}
			return new ResponseEntity<UserEntity>(userRepository.save(userEntity), HttpStatus.OK);
		}
		return new ResponseEntity<RESTError>(new RESTError(2, "User not found"), HttpStatus.NOT_FOUND);
	}

	// Promeni ulogu korisnika TESTIRAO
	@RequestMapping(value = "/{id}/role/{role}", method = RequestMethod.PUT)
	public ResponseEntity<?> changeRole(@PathVariable Integer id, @PathVariable EUserRole role) {
		if (userRepository.existsById(id)) {
			UserEntity user = userRepository.findById(id).get();
			user.setUserRole(role);
			return new ResponseEntity<UserEntity>(userRepository.save(user), HttpStatus.OK);
		}
		return new ResponseEntity<RESTError>(new RESTError(2, "User not found"), HttpStatus.NOT_FOUND);
	}

	// Promeni password korisnika TESTIRAO
	@RequestMapping(value = "/changePassword/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> changePassword(@PathVariable Integer id, @RequestParam String oldPassword,
			@RequestParam String newPassword) {
		if (userRepository.existsById(id) && userRepository.findById(id).get().getPassword().equals(oldPassword)) {
			UserEntity user = userRepository.findById(id).get();
			user.setPassword(newPassword);
			return new ResponseEntity<UserEntity>(userRepository.save(user), HttpStatus.OK);
		}
		return new ResponseEntity<RESTError>(new RESTError(6, "User not found, or old password wrong"), HttpStatus.BAD_REQUEST);
	}

	// Obrisi korisnika TESTIRAO
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
		if (userRepository.existsById(id)) {
			UserEntity temp = userRepository.findById(id).get();
			userRepository.deleteById(id);
			return new ResponseEntity<UserEntity>(temp, HttpStatus.OK);
		}
		return new ResponseEntity<RESTError>(new RESTError(2, "User not found"), HttpStatus.NOT_FOUND);
	}

	// Vrati korisnika po username-u TESTIRAO
	@RequestMapping(value = "/by-username/{username}", method = RequestMethod.GET)
	public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
		return new ResponseEntity<List<UserEntity>>(userRepository.findByUsername(username), HttpStatus.OK);
	}

}
