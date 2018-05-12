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
import com.example.project.entities.BillEntity;
import com.example.project.repositories.BillRepository;
import com.example.project.repositories.CategoryRepository;
import com.example.project.repositories.OfferRepository;
import com.example.project.repositories.UserRepository;
import com.example.project.security.Views;
import com.example.project.services.OfferService;
import com.example.project.services.VoucherService;
import com.fasterxml.jackson.annotation.JsonView;

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

	@Autowired
	private CategoryRepository categoryRepository;

	// Nadji sve racune TESTIRAO
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<?> getAllBills() {
		return new ResponseEntity<List<BillEntity>>((List<BillEntity>) billRepository.findAll(), HttpStatus.OK);
	}

	@JsonView(Views.Public.class)
	@GetMapping(value = "/public/")
	public ResponseEntity<?> getAllBillsPublic() {
		return new ResponseEntity<List<BillEntity>>((List<BillEntity>) billRepository.findAll(), HttpStatus.OK);
	}

	@JsonView(Views.Private.class)
	@GetMapping(value = "/private/")
	public ResponseEntity<?> getAllBillsPrivate() {
		return new ResponseEntity<List<BillEntity>>((List<BillEntity>) billRepository.findAll(), HttpStatus.OK);
	}

	@JsonView(Views.Admin.class)
	@GetMapping(value = "/admin/")
	public ResponseEntity<?> getAllBillsAdmin() {
		return new ResponseEntity<List<BillEntity>>((List<BillEntity>) billRepository.findAll(), HttpStatus.OK);
	}

	// Kreiraj novi racun TESTIRAO
	@RequestMapping(value = "/{offerId}/buyer/{buyerId}", method = RequestMethod.POST)
	public ResponseEntity<?> addBill(@PathVariable Integer offerId, @PathVariable Integer buyerId) {
		BillEntity bill = new BillEntity();
		bill.setOffer(offerRepository.save(offerService.updateSoldAvailable(offerId, 1)));
		bill.setUser(userRepository.findById(buyerId).get());
		bill.setBillCreated(LocalDate.now());
		bill.setPaymentMade(false);
		bill.setPaymentCanceled(false);
		return new ResponseEntity<BillEntity>(billRepository.save(bill), HttpStatus.OK);
	}

	// Obrisi racun TESTIRAO
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteBill(@PathVariable Integer id) {
		BillEntity temp = billRepository.findById(id).get();
		billRepository.deleteById(id);
		return new ResponseEntity<BillEntity>(temp, HttpStatus.OK);
	}

	// Izmeni racun TESTIRAO
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateBillById(@PathVariable Integer id, @RequestBody BillEntity billEntity) {
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
			return new ResponseEntity<BillEntity>(billRepository.save(bill), HttpStatus.OK);
		}
		return new ResponseEntity<RESTError>(new RESTError(1, "Bill not found"), HttpStatus.NOT_FOUND);
	}

	// Nadji racune po kupcu TESTIRAO
	@RequestMapping(value = "/findByBuyer/{buyerId}", method = RequestMethod.GET)
	public ResponseEntity<?> findByBuyer(@PathVariable Integer buyerId) {
		if (userRepository.existsById(buyerId)) {
			return new ResponseEntity<List<BillEntity>>(userRepository.findById(buyerId).get().getBill(),
					HttpStatus.OK);
		}
		return new ResponseEntity<RESTError>(new RESTError(2, "User not found"), HttpStatus.NOT_FOUND);
	}

	// Nadji racune po kategoriji TESTIRAO
	@RequestMapping(value = "/findByCategory/{categoryId}", method = RequestMethod.GET)
	public ResponseEntity<?> findByCategory(@PathVariable Integer categoryId) {
		if (categoryRepository.existsById(categoryId)) {
			return new ResponseEntity<List<BillEntity>>(billRepository.findByCategoryCustomQuery(categoryId), HttpStatus.OK);
		}
		return new ResponseEntity<RESTError>(new RESTError(3, "Category not found"), HttpStatus.NOT_FOUND);
	}

	// Nadji racune izmedju dva datuma TESTIRAO
	@RequestMapping(value = "/findByDate/{startDate}/and/{endDate}", method = RequestMethod.GET)
	public ResponseEntity<?> findByDate(@PathVariable String startDate, @PathVariable String endDate) {
		LocalDate startDateParsed = LocalDate.parse(startDate);
		LocalDate endDateParsed = LocalDate.parse(endDate);
		List<BillEntity> list = billRepository.findByBillCreatedBetween(startDateParsed, endDateParsed);
		return new ResponseEntity<List<BillEntity>>(list, HttpStatus.OK);
	}

}
