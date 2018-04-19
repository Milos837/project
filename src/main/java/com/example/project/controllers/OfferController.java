package com.example.project.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.entities.OfferEntity;
import com.example.project.entities.enums.EOfferStatus;

@RestController
public class OfferController {

	List<OfferEntity> allOffers;

	// DORADI
	public List<OfferEntity> getDb() {
		if (allOffers == null) {
			allOffers = new ArrayList<>();

			OfferEntity o1 = new OfferEntity();
			o1.setOfferName("ponuda1");

			allOffers.add(o1);
		}
		return allOffers;
	}

	// 3.3	TESTIRANO
	@RequestMapping(value = "/project/offers", method = RequestMethod.GET)
	public List<OfferEntity> getOffers() {
		return getDb();
	}

	// 3.4	TESTIRANO
	@RequestMapping(value = "/project/offers", method = RequestMethod.POST)
	public OfferEntity addOffer(@RequestBody OfferEntity offer) {
		getDb().add(offer);
		return offer;
	}

	// 3.5	TESTIRANO
	@RequestMapping(value = "/project/offers/{id}", method = RequestMethod.PUT)
	public OfferEntity updateOffer(@PathVariable Integer id, @RequestBody OfferEntity offer) {
		for (OfferEntity offerEntity : getDb()) {
			if (offerEntity.getId().equals(id)) {
				offerEntity.setActtionPrice(offer.getActtionPrice());
				offerEntity.setAvailableOffers(offer.getAvailableOffers());
				offerEntity.setBoughtOffers(offer.getBoughtOffers());
				offerEntity.setImagePath(offer.getImagePath());
				offerEntity.setOfferCreated(offer.getOfferCreated());
				offerEntity.setOfferExpires(offer.getOfferExpires());
				offerEntity.setOfferName(offer.getOfferName());
				offerEntity.setRegularPrice(offer.getRegularPrice());
				return offerEntity;
			}
		}
		return null;
	}

	// 3.6
	@RequestMapping(value = "/project/offers/{id}", method = RequestMethod.DELETE)
	public OfferEntity deleteOffer(@PathVariable Integer id) {
		for (OfferEntity offerEntity : getDb()) {
			if (offerEntity.getId().equals(id)) {
				getDb().remove(offerEntity);
				return offerEntity;
			}
		}
		return null;
	}

	// 3.7	TESTIRANO
	@RequestMapping(value = "/project/offers/{id}", method = RequestMethod.GET)
	public OfferEntity getOffer(@PathVariable Integer id) {
		for (OfferEntity offerEntity : getDb()) {
			if (offerEntity.getId().equals(id)) {
				return offerEntity;
			}
		}
		return null;
	}

	// 3.8
	@RequestMapping(value = "/project/offers/{id}/status/{status}", method = RequestMethod.PUT)
	public OfferEntity updateOfferStatus(@PathVariable Integer id, @PathVariable EOfferStatus status) {
		for (OfferEntity offerEntity : getDb()) {
			if (offerEntity.getId().equals(id)) {
				offerEntity.setOfferstatus(status);
				return offerEntity;
			}
		}
		return null;
	}

	// 3.9
	@RequestMapping(value = "/project/offers/findByPrice/{lowerPrice}/and/{upperPrice}", method = RequestMethod.GET)
	public List<OfferEntity> findByPrice(@PathVariable Double lowerPrice, @PathVariable Double upperPrice) {
		List<OfferEntity> lista = new ArrayList<>();
		for (OfferEntity offerEntity : getDb()) {
			if (offerEntity.getActtionPrice() < upperPrice && offerEntity.getActtionPrice() > lowerPrice) {
				lista.add(offerEntity);
			}
		}
		return lista;
	}
}
