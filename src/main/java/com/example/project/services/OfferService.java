package com.example.project.services;

import com.example.project.entities.OfferEntity;

public interface OfferService {
	
	OfferEntity updateSoldAvailable(Integer id, Integer sold);

}
