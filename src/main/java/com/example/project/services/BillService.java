package com.example.project.services;

import java.util.List; 

import com.example.project.entities.BillEntity;

public interface BillService {
	
	List<BillEntity> findActiveByCategory(Integer catId);
	
	List<BillEntity> cancelBillsByExpiredOffer(Integer offerId);
	
}
