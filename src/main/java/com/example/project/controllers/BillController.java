package com.example.project.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.entities.BillEntity;
import com.example.project.repositories.BillRepository;
import com.example.project.repositories.OfferRepository;
import com.example.project.repositories.UserRepository;
import com.example.project.services.OfferService;
import com.example.project.services.VoucherService;

@RestController
@RequestMapping(value = "/api/v1/project/bills")
public class BillController {

	@Autowired
	private BillRepository billRepository;

	@Autowired
	private OfferRepository offerRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OfferService offerService;

	@Autowired
	private VoucherService voucherService;

	// Nadji sve racune TESTIRAO
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<BillEntity> getAllBills() {
		return (List<BillEntity>) billRepository.findAll();
	}

	// Kreiraj novi racun TESTIRAO
	@RequestMapping(value = "/{offerId}/buyer/{buyerId}", method = RequestMethod.POST)
	public BillEntity addBill(@PathVariable Integer offerId, @PathVariable Integer buyerId) {
		BillEntity bill = new BillEntity();
		bill.setOffer(offerRepository.save(offerService.updateSoldAvailable(offerId, 1)));
		bill.setUser(userRepository.findById(buyerId).get());
		bill.setBillCreated(LocalDate.now());
		bill.setPaymentMade(false);
		bill.setPaymentCanceled(false);
		return billRepository.save(bill);
	}

	// Obrisi racun TESTIRAO
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public BillEntity deleteBill(@PathVariable Integer id) {
		BillEntity temp = billRepository.findById(id).get();
		billRepository.deleteById(id);
		return temp;
	}

	// Izmeni racun TESTIRAO
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public BillEntity updateBillById(@PathVariable Integer id, @RequestBody BillEntity billEntity) {
		if (billRepository.existsById(id)) {
			BillEntity bill = billRepository.findById(id).get();
			if (billEntity.getPaymentMade() != null && Boolean.TRUE.equals(billEntity.getPaymentMade())) {
				voucherService.createVoucherForBill(bill);
				bill.setPaymentMade(billEntity.getPaymentMade());
			} 
			if (billEntity.getPaymentCanceled() != null && Boolean.TRUE.equals(billEntity.getPaymentCanceled())) {
				bill.setPaymentCanceled(true);
				offerService.updateSoldAvailable(bill.getOffer().getId(), -1);
			} 
			return billRepository.save(bill);
		}
		return null;
	}

	// Nadji racune po kupcu TESTIRAO
	@RequestMapping(value = "/findByBuyer/{buyerId}", method = RequestMethod.GET)
	public List<BillEntity> findByBuyer(@PathVariable Integer buyerId) {
		if (userRepository.existsById(buyerId)) {
			return userRepository.findById(buyerId).get().getBill();
		}
		return null;
	}

	// Nadji racune po kategoriji TESTIRAO
	@RequestMapping(value = "/findByCategory/{categoryId}", method = RequestMethod.GET)
	public List<BillEntity> findByCategory(@PathVariable Integer categoryId) {
		return billRepository.findByCategoryCustomQuery(categoryId);
	}

	// Nadji racune izmedju dva datuma TESTIRAO
	@RequestMapping(value = "/findByDate/{startDate}/and/{endDate}", method = RequestMethod.GET)
	public List<BillEntity> findByDate(@PathVariable String startDate, @PathVariable String endDate) {
		LocalDate startDateParsed = LocalDate.parse(startDate);
		LocalDate endDateParsed = LocalDate.parse(endDate);
		return billRepository.findByBillCreatedBetween(startDateParsed, endDateParsed);
	}

}
