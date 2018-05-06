package com.example.project.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.project.entities.BillEntity;
import com.example.project.entities.OfferEntity;

public interface BillRepository extends CrudRepository<BillEntity, Integer> {
	
	//	TESTIRAO
	@Query("SELECT b "
			+ "FROM OfferEntity o JOIN BillEntity b ON b.offer=o.id JOIN CategoryEntity c ON c.id=o.category "
			+ "WHERE c.id = ?1")
	List<BillEntity> findByCategoryCustomQuery(Integer categoryId);
	
	List<BillEntity> findByBillCreatedBetween(LocalDate startDate, LocalDate endDate);
	
	List<BillEntity> findByOffer(OfferEntity offer);

}
