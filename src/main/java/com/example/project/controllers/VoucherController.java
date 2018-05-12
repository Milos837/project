package com.example.project.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.controllers.util.RESTError;
import com.example.project.entities.VoucherEntity;
import com.example.project.entities.enums.EUserRole;
import com.example.project.repositories.OfferRepository;
import com.example.project.repositories.UserRepository;
import com.example.project.repositories.VoucherRepository;
import com.example.project.security.Views;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping(value = "/api/v1/project/vouchers")
public class VoucherController {

	@Autowired
	private VoucherRepository voucherRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OfferRepository offerRepository;

	// Vrati sve vouchere TESTIRAO
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<?> getAllVouchers() {
		return new ResponseEntity<List<VoucherEntity>>((List<VoucherEntity>) voucherRepository.findAll(),
				HttpStatus.OK);
	}

	@JsonView(Views.Public.class)
	@GetMapping(value = "/public/")
	public ResponseEntity<?> getAllVouchersPublic() {
		return new ResponseEntity<List<VoucherEntity>>((List<VoucherEntity>) voucherRepository.findAll(),
				HttpStatus.OK);
	}

	@JsonView(Views.Private.class)
	@GetMapping(value = "/private/")
	public ResponseEntity<?> getAllVouchersPrivate() {
		return new ResponseEntity<List<VoucherEntity>>((List<VoucherEntity>) voucherRepository.findAll(),
				HttpStatus.OK);
	}

	@JsonView(Views.Admin.class)
	@GetMapping(value = "/admin/")
	public ResponseEntity<?> getAllVouchersAdmin() {
		return new ResponseEntity<List<VoucherEntity>>((List<VoucherEntity>) voucherRepository.findAll(),
				HttpStatus.OK);
	}

	// Dodaj voucher TESTIRAO
	@RequestMapping(value = "/{offerId}/buyer/{buyerId}", method = RequestMethod.POST)
	public ResponseEntity<?> addVoucher(@PathVariable Integer offerId, @PathVariable Integer buyerId) {
		if (userRepository.existsById(buyerId)
				&& userRepository.findById(buyerId).get().getUserRole().equals(EUserRole.ROLE_CUSTOMER)
				&& offerRepository.existsById(offerId)) {
			VoucherEntity voucher = new VoucherEntity();
			voucher.setOffer(offerRepository.findById(offerId).get());
			voucher.setUser(userRepository.findById(buyerId).get());
			voucher.setExpirationDate(LocalDate.now().plusDays(30));
			voucher.setIsUsed(false);
			return new ResponseEntity<VoucherEntity>(voucherRepository.save(voucher), HttpStatus.OK);
		}
		return new ResponseEntity<RESTError>(new RESTError(7, "Voucher not found"), HttpStatus.NOT_FOUND);
	}

	// Izmeni voucher
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateVoucher(@PathVariable Integer id, @RequestBody VoucherEntity voucherEntity) {
		if (voucherRepository.existsById(id)) {
			VoucherEntity voucher = voucherRepository.findById(id).get();

			if (voucherEntity.getUser() != null && userRepository.existsById(voucherEntity.getUser().getId())
					&& voucherEntity.getUser().getUserRole().equals(EUserRole.ROLE_CUSTOMER)) {
				voucher.setUser(voucherEntity.getUser());
			}
			if (voucherEntity.getOffer() != null && offerRepository.existsById(voucherEntity.getOffer().getId())) {
				voucher.setOffer(voucherEntity.getOffer());
			}
			if (voucherEntity.getExpirationDate() != null) {
				voucher.setExpirationDate(voucherEntity.getExpirationDate());
			}
			if (voucherEntity.getIsUsed() != null) {
				voucher.setIsUsed(voucherEntity.getIsUsed());
			}
			return new ResponseEntity<VoucherEntity>(voucherRepository.save(voucher), HttpStatus.OK);
		}
		return new ResponseEntity<RESTError>(new RESTError(7, "Voucher not found"), HttpStatus.NOT_FOUND);
	}

	// Obrisi voucher TESTIRAO
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteById(@PathVariable Integer id) {
		if (voucherRepository.existsById(id)) {
			VoucherEntity temp = voucherRepository.findById(id).get();
			voucherRepository.deleteById(id);
			return new ResponseEntity<VoucherEntity>(temp, HttpStatus.OK);
		}
		return new ResponseEntity<RESTError>(new RESTError(7, "Voucher not found"), HttpStatus.NOT_FOUND);
	}

	// Vrati voucher po kupcu TESTIRAO
	@RequestMapping(value = "/findByBuyer/{buyerId}", method = RequestMethod.GET)
	public ResponseEntity<?> findByBuyer(@PathVariable Integer buyerId) {
		return new ResponseEntity<List<VoucherEntity>>(voucherRepository.findByBuyerCustomQuery(buyerId),
				HttpStatus.OK);
	}

	// Vrati voucher po ponudi TESTIRAO
	@RequestMapping(value = "/findByOffer/{offerId}", method = RequestMethod.GET)
	public ResponseEntity<?> findByOffer(@PathVariable Integer offerId) {
		return new ResponseEntity<List<VoucherEntity>>(voucherRepository.findByOfferCustomQuery(offerId),
				HttpStatus.OK);
	}

	// Vrati vouchere koji nisu istekli TESTIRANO
	@RequestMapping(value = "/findNonExpiredVoucher", method = RequestMethod.GET)
	public ResponseEntity<?> findNonExpired() {
		return new ResponseEntity<List<VoucherEntity>>(
				voucherRepository.findByExpirationDateGreaterThan(LocalDate.now()), HttpStatus.OK);
	}

}
