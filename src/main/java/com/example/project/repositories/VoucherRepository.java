package com.example.project.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.project.entities.VoucherEntity;

public interface VoucherRepository extends CrudRepository<VoucherEntity, Integer> {
	
	//treba testirati
	@Query("SELECT v FROM VoucherEntity v JOIN UserEntity u ON v.user=u.id WHERE u.id = ?1")
	List<VoucherEntity> findByBuyerCustomQuery(Integer buyerId);
	
	//treba testirati
	@Query("SELECT v FROM VoucherEntity v JOIN OfferEntity o ON v.offer=o.id WHERE o.id = ?1")
	List<VoucherEntity> findByOfferCustomQuery(Integer offerId);
	
	List<VoucherEntity> findByExpirationDateGreaterThan(LocalDate date);

}
