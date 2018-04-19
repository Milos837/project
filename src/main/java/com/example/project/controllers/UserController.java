package com.example.project.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.entities.UserEntity;
import com.example.project.entities.enums.EUserRole;

@RestController
public class UserController {

	List<UserEntity> allClients;

	public List<UserEntity> getDb() {
		if (allClients == null) {
			allClients = new ArrayList<>();

			UserEntity u1 = new UserEntity();
			u1.setFirstName("Pera");
			u1.setLastName("Peric");
			u1.setEmail("pera@mail.com");
			u1.setUsername("pera");
			u1.setPassword("pera");
			u1.setUserRole(EUserRole.ROLE_CUSTOMER);
			allClients.add(u1);
		}
		return allClients;
	}

	// 1.3 TESTIRAO
	@RequestMapping(value = "/project/users", method = RequestMethod.GET)
	public List<UserEntity> getClients() {
		return getDb();
	}

	// 1.4 TESTIRAO
	@RequestMapping("/project/users/{id}")
	public UserEntity getClientById(@PathVariable Integer id) {
		for (UserEntity userEntity : getDb()) {
			if (userEntity.getId().equals(id)) {
				return userEntity;
			}
		}
		return null;
	}

	// 1.5 TESTIRAO
	@RequestMapping(value = "/project/users", method = RequestMethod.POST)
	public UserEntity addUser(@RequestBody UserEntity user) {
		user.setUserRole(EUserRole.ROLE_CUSTOMER);
		getDb().add(user);
		return user;
	}

	// 1.6	TESTIRAO
	@RequestMapping(value = "/project/users/{id}", method = RequestMethod.PUT)
	public UserEntity updateUser(@PathVariable Integer id, @RequestBody UserEntity user) {
		for (UserEntity userEntity : getDb()) {
			if (userEntity.getId().equals(id)) {
				userEntity.setFirstName(user.getFirstName());
				userEntity.setLastName(user.getLastName());
				userEntity.setEmail(user.getEmail());
				userEntity.setUsername(user.getUsername());
				return userEntity;
			}
		}
		return null;
	}

	// 1.7	TESTIRANO
	@RequestMapping(value = "/project/users/{id}/role/{role}", method = RequestMethod.PUT)
	public UserEntity changeRole(@PathVariable Integer id, @PathVariable EUserRole role) {
		for (UserEntity userEntity : getDb()) {
			if (userEntity.getId().equals(id)) {
				userEntity.setUserRole(role);
				return userEntity;
			}
		}
		return null;
	}

	// 1.8	TESTIRANO
	@RequestMapping(value = "/project/users/changePassword/{id}", method = RequestMethod.PUT)
	public UserEntity changePassword(@PathVariable Integer id, @RequestParam("password") String password) {
		for (UserEntity userEntity : getDb()) {
			if (userEntity.getId().equals(id)) {
				userEntity.setPassword(password);
				return userEntity;
			}
		}
		return null;
	}

	// 1.9	TESTIRANO
	@RequestMapping(value = "/project/users/{id}", method = RequestMethod.DELETE)
	public UserEntity deleteUser(@PathVariable Integer id) {
		for (UserEntity userEntity : getDb()) {
			if (userEntity.getId().equals(id)) {
				getDb().remove(userEntity);
				return userEntity;
			}
		}
		return null;
	}

	// 1.10	TESTIRANO
	@RequestMapping(value = "/project/users/by-username/{username}", method = RequestMethod.GET)
	public UserEntity getUserByUsername(@PathVariable String username) {
		for (UserEntity userEntity : getDb()) {
			if (userEntity.getUsername().equals(username)) {
				return userEntity;
			}
		}
		return null;
	}

}
