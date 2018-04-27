package com.example.project.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.project.entities.VoucherEntity;

public interface VoucherRepository extends CrudRepository<VoucherEntity, Integer> {
	
	//treba testirati
	@Query("SELECT v FROM voucher v JOIN user u ON v.user=u.id WHERE u.id = ?1")
	List<VoucherEntity> findByBuyerCustomQuery(Integer buyerId);
	
	//treba testirati
	@Query("SELECT v FROM voucher v JOIN offer o ON v.offer=o.id WHERE o.id = ?1")
	List<VoucherEntity> findByOfferCustomQuery(Integer offerId);
	
	List<VoucherEntity> findByExpirationDateLessThan(LocalDate date);

}
