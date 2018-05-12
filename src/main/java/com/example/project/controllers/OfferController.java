package com.example.project.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.project.controllers.util.RESTError;
import com.example.project.entities.OfferEntity;
import com.example.project.entities.enums.EOfferStatus;
import com.example.project.entities.enums.EUserRole;
import com.example.project.repositories.CategoryRepository;
import com.example.project.repositories.OfferRepository;
import com.example.project.repositories.UserRepository;
import com.example.project.security.Views;
import com.example.project.services.BillService;
import com.example.project.services.FileHandler;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping(value = "/api/v1/project/offers")
public class OfferController {

	@Autowired
	private OfferRepository offerRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private FileHandler fileHandler;

	@Autowired
	private BillService billService;

	// Vrati sve ponude TESTIRAO
	@JsonView(Views.Public.class)
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<?> getOffers() {
		return new ResponseEntity<List<OfferEntity>>((List<OfferEntity>) offerRepository.findAll(), HttpStatus.OK);
	}

	// Dodaj novu ponudu i povezi kategoriju i korisnika
	// Korisnik mora biti ROLE_SELLER TESTIRAO
	@RequestMapping(value = "/{categoryId}/seller/{sellerId}", method = RequestMethod.POST)
	public ResponseEntity<?> addCategoryAndUserForOffer(@PathVariable Integer categoryId, @PathVariable Integer sellerId) {
		if (userRepository.findById(sellerId).get().getUserRole().equals(EUserRole.ROLE_SELLER)) {
			OfferEntity offer = new OfferEntity();
			offer.setCategory(categoryRepository.findById(categoryId).get());
			offer.setUser(userRepository.findById(sellerId).get());
			offer.setOfferCreated(LocalDate.now().toString());
			offer.setOfferExpires(LocalDate.now().plusDays(10).toString());
			offer.setRegularPrice(.0);
			offer.setActtionPrice(.0);
			offer.setAvailableOffers(1);
			offer.setBoughtOffers(0);
			return new ResponseEntity<OfferEntity>(offerRepository.save(offer), HttpStatus.OK);
		}
		return new ResponseEntity<RESTError>(new RESTError(2, "User not found"), HttpStatus.NOT_FOUND);
	}

	// Azuriraj ponudu TESTIRAO
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateOffer(@PathVariable Integer id, @RequestBody OfferEntity offer) {
		if (offerRepository.existsById(id)) {
			OfferEntity offerEntity = offerRepository.findById(id).get();
			if (offer.getActtionPrice() != null) {
				offerEntity.setActtionPrice(offer.getActtionPrice());
			}
			if (offer.getAvailableOffers() != null) {
				offerEntity.setAvailableOffers(offer.getAvailableOffers());
			}
			if (offer.getBoughtOffers() != null) {
				offerEntity.setBoughtOffers(offer.getBoughtOffers());
			}
			if (offer.getImagePath() != null) {
				offerEntity.setImagePath(offer.getImagePath());
			}
			if (offer.getOfferCreated() != null) {
				offerEntity.setOfferCreated(offer.getOfferCreated());
			}
			if (offer.getOfferExpires() != null) {
				offerEntity.setOfferExpires(offer.getOfferExpires());
			}
			if (offer.getOfferName() != null) {
				offerEntity.setOfferName(offer.getOfferName());
			}
			if (offer.getRegularPrice() != null) {
				offerEntity.setRegularPrice(offer.getRegularPrice());
			}
			return new ResponseEntity<OfferEntity>(offerRepository.save(offerEntity), HttpStatus.OK);
		}
		return new ResponseEntity<RESTError>(new RESTError(5, "Offer not found"), HttpStatus.NOT_FOUND);
	}

	// Obrisi ponudu po ID-u TESTIRAO
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteOffer(@PathVariable Integer id) {
		if (offerRepository.existsById(id)) {
			OfferEntity offer = offerRepository.findById(id).get();
			offerRepository.deleteById(id);
			return new ResponseEntity<OfferEntity>(offer, HttpStatus.OK);
		}
		return new ResponseEntity<RESTError>(new RESTError(5, "Offer not found"), HttpStatus.NOT_FOUND);
	}

	// Vrati ponudu po ID-u TESTIRAO
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getOffer(@PathVariable Integer id) {
		if (offerRepository.existsById(id)) {
			return new ResponseEntity<OfferEntity>(offerRepository.findById(id).get(), HttpStatus.OK);
		}
		return new ResponseEntity<RESTError>(new RESTError(5, "Offer not found"), HttpStatus.NOT_FOUND);
	}

	// Promeni status ponude TESTIRAO
	@RequestMapping(value = "/{id}/status/{status}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateOfferStatus(@PathVariable Integer id, @PathVariable EOfferStatus status) {
		if (offerRepository.existsById(id)) {
			OfferEntity offer = offerRepository.findById(id).get();
			if (status.equals(EOfferStatus.EXPIRED)) {
				billService.cancelBillsByExpiredOffer(id);
			}
			offer.setOfferstatus(status);
			return new ResponseEntity<OfferEntity>(offerRepository.save(offer), HttpStatus.OK);
		}
		return new ResponseEntity<RESTError>(new RESTError(5, "Offer not found"), HttpStatus.NOT_FOUND);
	}

	// Nadji ponude izmedju zadate cene TESTIRAO
	@RequestMapping(value = "/findByPrice/{lowerPrice}/and/{upperPrice}", method = RequestMethod.GET)
	public ResponseEntity<?> findByPrice(@PathVariable Double lowerPrice, @PathVariable Double upperPrice) {
		List<OfferEntity> offers = offerRepository.findByActtionPriceBetween(lowerPrice, upperPrice);
		return new ResponseEntity<List<OfferEntity>>(offers, HttpStatus.OK);
	}

	// Promeni kategoriju ponude TESTIRAO
	@RequestMapping(value = "/{offerId}/category/{categoryId}", method = RequestMethod.PUT)
	public ResponseEntity<?> changeCategory(@PathVariable Integer offerId, @PathVariable Integer categoryId) {
		if (offerRepository.existsById(offerId) && categoryRepository.existsById(categoryId)) {
			OfferEntity offer = offerRepository.findById(offerId).get();
			offer.setCategory(categoryRepository.findById(categoryId).get());
			return new ResponseEntity<OfferEntity>(offerRepository.save(offer), HttpStatus.OK);
		}
		return new ResponseEntity<RESTError>(new RESTError(5, "Offer not found"), HttpStatus.NOT_FOUND);
	}

	// Uploaduje sliku za ponudu TESTIRAO
	@PutMapping(value = "/uploadImage/{id}")
	public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file, @PathVariable Integer id) {
		if (offerRepository.existsById(id)) {
			OfferEntity offer = offerRepository.findById(id).get();
			offer.setImagePath(fileHandler.singleFileUpload(file).toString());
			return new ResponseEntity<OfferEntity>(offerRepository.save(offer), HttpStatus.OK);
		}
		return new ResponseEntity<RESTError>(new RESTError(5, "Offer not found"), HttpStatus.NOT_FOUND);
	}

}
