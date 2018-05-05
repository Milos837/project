package com.example.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.entities.OfferEntity;
import com.example.project.repositories.OfferRepository;

@Service
public class OfferServiceImpl implements OfferService{
	
	@Autowired
	private OfferRepository offerRepository;

	@Override
	public OfferEntity updateSoldAvailable(Integer id, Integer sold) {
		OfferEntity offer = offerRepository.findById(id).get();
		offer.setBoughtOffers(offer.getBoughtOffers() + sold);
		offer.setAvailableOffers(offer.getAvailableOffers() - sold);
		return offerRepository.save(offer);
	}

}
