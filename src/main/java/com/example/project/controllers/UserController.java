package com.example.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.entities.UserEntity;
import com.example.project.entities.enums.EUserRole;
import com.example.project.repositories.UserRepository;

@RestController
@RequestMapping(value = "/api/v1/project/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	// Vrati sve korisnike TESTIRAO
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<UserEntity> getClients() {
		return (List<UserEntity>) userRepository.findAll();
	}

	// Vrati korisnika po ID-u TESTIRAO
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public UserEntity getClientById(@PathVariable Integer id) {
		if (userRepository.existsById(id)) {
			return userRepository.findById(id).get();
		}
		return null;
	}

	// Dodaj novog korisnika TESTIRAO
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public UserEntity addUser(@RequestBody UserEntity user) {
		return userRepository.save(user);
	}

	// Azuriraj podatke korisnika po ID-u TESTIRAO
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public UserEntity updateUser(@PathVariable Integer id, @RequestBody UserEntity user) {
		if (userRepository.existsById(id)) {
			UserEntity userEntity = userRepository.findById(id).get();
			userEntity.setFirstName(user.getFirstName());
			userEntity.setLastName(user.getLastName());
			userEntity.setEmail(user.getEmail());
			userEntity.setUsername(user.getUsername());
			return userRepository.save(userEntity);
		}
		return null;
	}

	// Promeni ulogu korisnika TESTIRAO
	@RequestMapping(value = "/{id}/role/{role}", method = RequestMethod.PUT)
	public UserEntity changeRole(@PathVariable Integer id, @PathVariable EUserRole role) {
		if (userRepository.existsById(id)) {
			UserEntity user = userRepository.findById(id).get();
			user.setUserRole(role);
			return userRepository.save(user);
		}
		return null;
	}

	// Promeni password korisnika TESTIRAO
	@RequestMapping(value = "/changePassword/{id}", method = RequestMethod.PUT)
	public UserEntity changePassword(@PathVariable Integer id, @RequestParam String oldPassword,
			@RequestParam String newPassword) {
		if (userRepository.existsById(id) && userRepository.findById(id).get().getPassword().equals(oldPassword)) {
			UserEntity user = userRepository.findById(id).get();
			user.setPassword(newPassword);
			return userRepository.save(user);
		}
		return null;
	}

	// Obrisi korisnika TESTIRAO
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public UserEntity deleteUser(@PathVariable Integer id) {
		if (userRepository.existsById(id)) {
			UserEntity temp = userRepository.findById(id).get();
			userRepository.deleteById(id);
			return temp;
		}
		return null;
	}

	// Vrati korisnika po username-u TESTIRAO
	@RequestMapping(value = "/by-username/{username}", method = RequestMethod.GET)
	public List<UserEntity> getUserByUsername(@PathVariable String username) {
		return userRepository.findByUsername(username);
	}

}
