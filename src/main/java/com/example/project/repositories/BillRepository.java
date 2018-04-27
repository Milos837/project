package com.example.project.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.project.entities.BillEntity;

public interface BillRepository extends CrudRepository<BillEntity, Integer> {

}
