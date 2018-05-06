package com.example.project.services;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.entities.BillEntity;
import com.example.project.repositories.BillRepository;
import com.example.project.repositories.OfferRepository;

@Service
public class BillServiceImpl implements BillService{
	
	@Autowired
	private BillRepository billRepository;
	
	@Autowired
	private OfferRepository offerRepository;

	//	TESTIRAO
	@Override
	public List<BillEntity> findActiveByCategory(Integer catId) {
		List<BillEntity> bills = billRepository.findByCategoryCustomQuery(catId);
		Iterator<BillEntity> active = bills.iterator();
		while(active.hasNext()) {
			BillEntity bill = active.next();
			if(Boolean.TRUE.equals(bill.getPaymentCanceled()) || Boolean.TRUE.equals(bill.getPaymentMade())) {
				active.remove();
			}
		}
		return bills;
	}

	@Override
	public List<BillEntity> cancelBillsByExpiredOffer(Integer offerId) {
		List<BillEntity> billsByOffer = billRepository.findByOffer(offerRepository.findById(offerId).get());
		for (BillEntity billEntity : billsByOffer) {
			billEntity.setPaymentCanceled(true);
			billRepository.save(billEntity);
		}
		return billsByOffer;
	}

}
