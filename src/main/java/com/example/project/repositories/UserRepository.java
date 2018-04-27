package com.example.project.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.project.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {
	
	List<UserEntity> findByUsername(String username);

}
