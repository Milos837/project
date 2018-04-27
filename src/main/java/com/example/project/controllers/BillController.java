package com.example.project.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.entities.BillEntity;
import com.example.project.entities.OfferEntity;
import com.example.project.repositories.BillRepository;
import com.example.project.repositories.CategoryRepository;
import com.example.project.repositories.OfferRepository;
import com.example.project.repositories.UserRepository;

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
	private CategoryRepository categoryRepository;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<BillEntity> getAllBills() {
		return (List<BillEntity>) billRepository.findAll();
	}
	
	@RequestMapping(value = "/{offerId}/buyer/{buyerId}", method = RequestMethod.POST)
	public BillEntity addBill(@PathVariable Integer offerId, @PathVariable Integer buyerId) {
		BillEntity bill = new BillEntity();
		bill.setOffer(offerRepository.findById(offerId).get());
		bill.setUser(userRepository.findById(buyerId).get());
		return billRepository.save(bill);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public BillEntity deleteBill(@PathVariable Integer id) {
		BillEntity temp = billRepository.findById(id).get();
		billRepository.deleteById(id);
		return temp;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public BillEntity updateBillById(@PathVariable Integer id, @RequestBody BillEntity billEntity) {
		BillEntity bill = billRepository.findById(id).get();
		bill.setUser(billEntity.getUser());
		bill.setOffer(billEntity.getOffer());
		bill.setBillCreated(billEntity.getBillCreated());
		bill.setPaymentMade(billEntity.getPaymentMade());
		bill.setPaymentCanceled(billEntity.getPaymentCanceled());
		return billRepository.save(bill);
	}
	
	@RequestMapping(value = "/findByBuyer/{buyerId}", method = RequestMethod.GET)
	public List<BillEntity> findByBuyer(@PathVariable Integer id) {
		return userRepository.findById(id).get().getBill();
	}
	
	@RequestMapping(value = "/findByCategory/{categoryId}", method = RequestMethod.GET)
	public List<BillEntity> findByCategory(@PathVariable Integer id) {
		List<OfferEntity> list1 = categoryRepository.findById(id).get().getOffer();
		List<BillEntity> list2 = new ArrayList<>();
		for (OfferEntity offerEntity : list1) {
			if(offerEntity.getBill() != null) {
				//DOVRSI
			}
		}
		return null;
	}

}
