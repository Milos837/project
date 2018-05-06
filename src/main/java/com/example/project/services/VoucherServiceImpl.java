package com.example.project.services;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.entities.BillEntity;
import com.example.project.entities.VoucherEntity;
import com.example.project.entities.enums.EUserRole;
import com.example.project.models.EmailObject;
import com.example.project.repositories.OfferRepository;
import com.example.project.repositories.VoucherRepository;

@Service
public class VoucherServiceImpl implements VoucherService{
	
	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private VoucherRepository voucherRepository;
	
	@Autowired
	private EmailService emailService;

	@Override
	public VoucherEntity createVoucherForBill(BillEntity bill) {
		if (bill.getUser().getUserRole().equals(EUserRole.ROLE_CUSTOMER)
				&& offerRepository.existsById(bill.getOffer().getId())) {
			VoucherEntity voucher = new VoucherEntity();
			voucher.setOffer(bill.getOffer());
			voucher.setUser(bill.getUser());
			voucher.setExpirationDate(LocalDate.now().plusDays(30));
			voucher.setIsUsed(false);
			//Posalji voucher kupcu preko emaila
			EmailObject mail = new EmailObject();
			mail.setTo(bill.getUser().getEmail());
			mail.setSubject("Voucher info");
			String text = "<tr>\r\n" + 
					"<th>Buyer</th>\r\n" + 
					"<th>Offer</th>\r\n" + 
					"<th>Price</th>\r\n" + 
					"<th>Expires date</th>\r\n" + 
					"</tr>\r\n" + 
					"<tr>\r\n" + 
					"<td>" + bill.getUser().getFirstName() + " " + bill.getUser().getLastName() + "</td>\r\n" + 
					"<td>" + bill.getOffer().getId().toString() + "</td>\r\n" + 
					"<td>" + String.valueOf(bill.getOffer().getActtionPrice()) + "</td>\r\n" + 
					"<td>" + voucher.getExpirationDate().toString() + "</td>\r\n" + 
					"</tr>";
			mail.setText(text);
			try {
			emailService.sendTemplateMessage(mail);
			} catch(Exception e) {
				e.getStackTrace();
			}
			return voucherRepository.save(voucher);
		}
		return null;
	}

}
