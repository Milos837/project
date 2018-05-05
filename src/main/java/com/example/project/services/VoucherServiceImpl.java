package com.example.project.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.entities.BillEntity;
import com.example.project.entities.VoucherEntity;
import com.example.project.entities.enums.EUserRole;
import com.example.project.repositories.OfferRepository;
import com.example.project.repositories.VoucherRepository;

@Service
public class VoucherServiceImpl implements VoucherService{
	
	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private VoucherRepository voucherRepository;

	@Override
	public VoucherEntity createVoucherForBill(BillEntity bill) {
		if (bill.getUser().getUserRole().equals(EUserRole.ROLE_CUSTOMER)
				&& offerRepository.existsById(bill.getOffer().getId())) {
			VoucherEntity voucher = new VoucherEntity();
			voucher.setOffer(bill.getOffer());
			voucher.setUser(bill.getUser());
			voucher.setExpirationDate(LocalDate.now().plusDays(30));
			voucher.setIsUsed(false);
			return voucherRepository.save(voucher);
		}
		return null;
	}

}
