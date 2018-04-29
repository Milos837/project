package com.example.project.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.entities.OfferEntity;
import com.example.project.entities.enums.EOfferStatus;
import com.example.project.entities.enums.EUserRole;
import com.example.project.repositories.CategoryRepository;
import com.example.project.repositories.OfferRepository;
import com.example.project.repositories.UserRepository;

@RestController
@RequestMapping(value = "/api/v1/project/offers")
public class OfferController {

	@Autowired
	private OfferRepository offerRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	// Vrati sve ponude TESTIRAO
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<OfferEntity> getOffers() {
		return (List<OfferEntity>) offerRepository.findAll();
	}

	// Dodaj novu ponudu i povezi kategoriju i korisnika
	// Korisnik mora biti ROLE_SELLER
	@RequestMapping(value = "/{categoryId}/seller/{sellerId}", method = RequestMethod.POST)
	public OfferEntity addCategoryAndUserForOffer(@PathVariable Integer categoryId, @PathVariable Integer sellerId) {
		if (userRepository.findById(sellerId).get().getUserRole().equals(EUserRole.ROLE_SELLER)) {
			OfferEntity offer = new OfferEntity();
			offer.setCategory(categoryRepository.findById(categoryId).get());
			offer.setUser(userRepository.findById(sellerId).get());
			offer.setOfferCreated(LocalDate.now().toString());
			offer.setOfferExpires(LocalDate.now().plusDays(10).toString());
			return offerRepository.save(offer);
		}
		return null;
	}

	// Azuriraj ponudu TESTIRAO
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public OfferEntity updateOffer(@PathVariable Integer id, @RequestBody OfferEntity offer) {
		if (offerRepository.existsById(id)) {
			OfferEntity offerEntity = offerRepository.findById(id).get();
			offerEntity.setActtionPrice(offer.getActtionPrice());
			offerEntity.setAvailableOffers(offer.getAvailableOffers());
			offerEntity.setBoughtOffers(offer.getBoughtOffers());
			offerEntity.setImagePath(offer.getImagePath());
			offerEntity.setOfferCreated(offer.getOfferCreated());
			offerEntity.setOfferExpires(offer.getOfferExpires());
			offerEntity.setOfferName(offer.getOfferName());
			offerEntity.setRegularPrice(offer.getRegularPrice());
			return offerRepository.save(offerEntity);
		}
		return null;
	}

	// Obrisi ponudu po ID-u TESTIRAO
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public OfferEntity deleteOffer(@PathVariable Integer id) {
		if (offerRepository.existsById(id)) {
			OfferEntity offer = offerRepository.findById(id).get();
			offerRepository.deleteById(id);
			return offer;
		}
		return null;
	}

	// Vrati ponudu po ID-u TESTIRAO
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public OfferEntity getOffer(@PathVariable Integer id) {
		if (offerRepository.existsById(id)) {
			return offerRepository.findById(id).get();
		}
		return null;
	}

	// Promeni status ponude TESTIRAO
	@RequestMapping(value = "/{id}/status/{status}", method = RequestMethod.PUT)
	public OfferEntity updateOfferStatus(@PathVariable Integer id, @PathVariable EOfferStatus status) {
		if (offerRepository.existsById(id)) {
			OfferEntity offer = offerRepository.findById(id).get();
			offer.setOfferstatus(status);
			return offerRepository.save(offer);
		}
		return null;
	}

	// Nadji ponude izmedju zadate cene
	@RequestMapping(value = "/findByPrice/{lowerPrice}/and/{upperPrice}", method = RequestMethod.GET)
	public List<OfferEntity> findByPrice(@PathVariable Double lowerPrice, @PathVariable Double upperPrice) {
		return offerRepository.findByActtionPriceBetween(lowerPrice, upperPrice);
	}

	// Promeni kategoriju ponude
	@RequestMapping(value = "/{id}/category/{categoryId}", method = RequestMethod.PUT)
	public OfferEntity changeCategory(@PathVariable Integer offerId, @PathVariable Integer catId) {
		OfferEntity offer = offerRepository.findById(offerId).get();
		offer.setCategory(categoryRepository.findById(catId).get());
		return offerRepository.save(offer);
	}

}
