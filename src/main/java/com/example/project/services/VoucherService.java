package com.example.project.services;

import com.example.project.entities.BillEntity;
import com.example.project.entities.VoucherEntity;

public interface VoucherService {
	
	VoucherEntity createVoucherForBill(BillEntity bill);

}
