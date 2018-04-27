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


	// Vrati sve korisnike
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<UserEntity> getClients() {
		return (List<UserEntity>) userRepository.findAll();
	}

	// Vrati korisnika po ID-u
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public UserEntity getClientById(@PathVariable Integer id) {
		return userRepository.findById(id).get();
	}

	// Dodaj novog korisnika
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public UserEntity addUser(@RequestBody UserEntity user) {
		return userRepository.save(user);
	}

	// Azuriraj podatke korisnika po ID-u
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public UserEntity updateUser(@PathVariable Integer id, @RequestBody UserEntity user) {
		UserEntity userEntity = userRepository.findById(id).get();
		userEntity.setFirstName(user.getFirstName());
		userEntity.setLastName(user.getLastName());
		userEntity.setEmail(user.getEmail());
		userEntity.setUsername(user.getUsername());
		userEntity.setPassword(user.getPassword());
		userEntity.setUserRole(user.getUserRole());
		return userRepository.save(userEntity);
	}

	// Promeni ulogu korisnika
	@RequestMapping(value = "/{id}/role/{role}", method = RequestMethod.PUT)
	public UserEntity changeRole(@PathVariable Integer id, @PathVariable EUserRole role) {
		UserEntity user = userRepository.findById(id).get();
		user.setUserRole(role);
		return userRepository.save(user);
	}

	// Promeni password korisnika
	@RequestMapping(value = "/changePassword/{id}", method = RequestMethod.PUT)
	public UserEntity changePassword(@PathVariable Integer id, @RequestParam("password") String password) {
		UserEntity user = userRepository.findById(id).get();
		user.setPassword(password);
		return userRepository.save(user);
	}

	// Obrisi korisnika
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public UserEntity deleteUser(@PathVariable Integer id) {
		UserEntity temp = userRepository.findById(id).get();
		userRepository.deleteById(id);
		return temp;
	}

	// Vrati korisnika po username-u
	@RequestMapping(value = "/by-username/{username}", method = RequestMethod.GET)
	public List<UserEntity> getUserByUsername(@PathVariable String username) {
		return userRepository.findByUsername(username);
	}

}
