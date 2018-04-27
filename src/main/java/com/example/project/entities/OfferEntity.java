package com.example.project.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import com.example.project.entities.enums.EOfferStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "offer")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class OfferEntity {

	@Id
	@GeneratedValue
	protected Integer id;

	@OneToMany(mappedBy = "voucher", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonIgnore
	protected List<VoucherEntity> voucher;

	@OneToMany(mappedBy = "offer", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JsonIgnore
	protected List<BillEntity> bill;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "user")
	protected UserEntity user;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "category")
	protected CategoryEntity category;

	@Column
	protected String offerName;

	@Column
	protected String offerCreated;

	@Column
	protected String offerExpires;

	@Column
	protected Double regularPrice;

	@Column
	protected Double acttionPrice;

	@Column
	protected String imagePath;

	@Column
	protected Integer availableOffers;

	@Column
	protected Integer boughtOffers;

	@Column
	protected EOfferStatus offerstatus;

	@Version
	protected Integer version;

	public OfferEntity() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<BillEntity> getBill() {
		return bill;
	}

	public void setBill(List<BillEntity> bill) {
		this.bill = bill;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public CategoryEntity getCategory() {
		return category;
	}

	public void setCategory(CategoryEntity category) {
		this.category = category;
	}

	public String getOfferName() {
		return offerName;
	}

	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}

	public String getOfferCreated() {
		return offerCreated;
	}

	public void setOfferCreated(String offerCreated) {
		this.offerCreated = offerCreated;
	}

	public String getOfferExpires() {
		return offerExpires;
	}

	public void setOfferExpires(String offerExpires) {
		this.offerExpires = offerExpires;
	}

	public List<VoucherEntity> getVoucher() {
		return voucher;
	}

	public void setVoucher(List<VoucherEntity> voucher) {
		this.voucher = voucher;
	}

	public Double getRegularPrice() {
		return regularPrice;
	}

	public void setRegularPrice(Double regularPrice) {
		this.regularPrice = regularPrice;
	}

	public Double getActtionPrice() {
		return acttionPrice;
	}

	public void setActtionPrice(Double acttionPrice) {
		this.acttionPrice = acttionPrice;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Integer getAvailableOffers() {
		return availableOffers;
	}

	public void setAvailableOffers(Integer availableOffers) {
		this.availableOffers = availableOffers;
	}

	public Integer getBoughtOffers() {
		return boughtOffers;
	}

	public void setBoughtOffers(Integer boughtOffers) {
		this.boughtOffers = boughtOffers;
	}

	public EOfferStatus getOfferstatus() {
		return offerstatus;
	}

	public void setOfferstatus(EOfferStatus offerstatus) {
		this.offerstatus = offerstatus;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "OfferEntity [id=" + id + ", offerName=" + offerName + ", offerCreated=" + offerCreated
				+ ", offerExpires=" + offerExpires + ", regularPrice=" + regularPrice + ", acttionPrice=" + acttionPrice
				+ ", imagePath=" + imagePath + ", availableOffers=" + availableOffers + ", boughtOffers=" + boughtOffers
				+ ", offerstatus=" + offerstatus + "]";
	}

}
