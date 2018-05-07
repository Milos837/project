package com.example.project.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.entities.OfferEntity;
import com.example.project.entities.VoucherEntity;
import com.example.project.entities.enums.EUserRole;
import com.example.project.repositories.OfferRepository;
import com.example.project.repositories.UserRepository;
import com.example.project.repositories.VoucherRepository;

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
	public List<VoucherEntity> getAllVouchers() {
		return (List<VoucherEntity>) voucherRepository.findAll();
	}

	// Dodaj voucher TESTIRAO
	@RequestMapping(value = "/{offerId}/buyer/{buyerId}", method = RequestMethod.POST)
	public VoucherEntity addVoucher(@PathVariable Integer offerId, @PathVariable Integer buyerId) {
		if (userRepository.findById(buyerId).get().getUserRole().equals(EUserRole.ROLE_CUSTOMER)
				&& offerRepository.existsById(offerId)) {
			VoucherEntity voucher = new VoucherEntity();
			voucher.setOffer(offerRepository.findById(offerId).get());
			voucher.setUser(userRepository.findById(buyerId).get());
			voucher.setExpirationDate(LocalDate.now().plusDays(30));
			voucher.setIsUsed(false);
			return voucherRepository.save(voucher);
		}
		return null;
	}

	// Izmeni voucher
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public VoucherEntity updateVoucher(@RequestParam Integer offerId, @RequestParam Integer buyerId,
			@RequestParam String expDate, @RequestParam Boolean isUsed, @PathVariable Integer id) {
		if (userRepository.findById(buyerId).get().getUserRole().equals(EUserRole.ROLE_CUSTOMER)
				&& offerRepository.existsById(offerId)) {
			VoucherEntity voucher = new VoucherEntity();
			OfferEntity offer = offerRepository.findById(id).get();
			voucher.setOffer(offer);
			voucher.setUser(userRepository.findById(buyerId).get());
			voucher.setExpirationDate(LocalDate.parse(expDate));
			voucher.setIsUsed(isUsed);
			return voucherRepository.save(voucher);
		}
		return null;
	}

	// Obrisi voucher
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public VoucherEntity deleteById(@PathVariable Integer id) {
		VoucherEntity temp = voucherRepository.findById(id).get();
		voucherRepository.deleteById(id);
		return temp;
	}

	// Vrati voucher po kupcu TESTIRAO
	@RequestMapping(value = "/findByBuyer/{buyerId}", method = RequestMethod.GET)
	public List<VoucherEntity> findByBuyer(@PathVariable Integer buyerId) {
		return voucherRepository.findByBuyerCustomQuery(buyerId);
	}

	// Vrati voucher po ponudi TESTIRAO
	@RequestMapping(value = "/findByOffer/{offerId}", method = RequestMethod.GET)
	public List<VoucherEntity> findByOffer(@PathVariable Integer offerId) {
		return voucherRepository.findByOfferCustomQuery(offerId);
	}

	// Vrati vouchere koji nisu istekli TESTIRANO
	@RequestMapping(value = "/findNonExpiredVoucher", method = RequestMethod.GET)
	public List<VoucherEntity> findNonExpired() {
		return voucherRepository.findByExpirationDateGreaterThan(LocalDate.now());
	}

}
