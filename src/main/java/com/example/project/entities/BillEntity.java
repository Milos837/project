package com.example.project.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "bill")
public class BillEntity {
	
	@Id
	@GeneratedValue
	protected Integer id;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
	protected UserEntity user;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "offer")
	protected OfferEntity offer;
	
	@Column
	protected Boolean paymentMade;
	
	@Column
	protected Boolean paymentCanceled;
	
	@Column
	protected LocalDate billCreated;
	
	@Version
	protected Integer version;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Boolean getPaymentMade() {
		return paymentMade;
	}
	public void setPaymentMade(Boolean paymentMade) {
		this.paymentMade = paymentMade;
	}
	public Boolean getPaymentCanceled() {
		return paymentCanceled;
	}
	public void setPaymentCanceled(Boolean paymentCanceled) {
		this.paymentCanceled = paymentCanceled;
	}
	public LocalDate getBillCreated() {
		return billCreated;
	}
	public void setBillCreated(LocalDate billCreated) {
		this.billCreated = billCreated;
	}
	
	public void setUser(UserEntity user) {
		this.user = user;
	}
	
	public UserEntity getUser() {
		return this.user;
	}
	
	public void setOffer(OfferEntity offer) {
		this.offer = offer;
	}
	
	public OfferEntity getOffer() {
		return this.offer;
	}
	
	public BillEntity() {
		super();
	}
	@Override
	public String toString() {
		return "BillEntity [id=" + id + ", paymentMade=" + paymentMade + ", paymentCanceled=" + paymentCanceled
				+ ", billCreated=" + billCreated + "]";
	}

}
