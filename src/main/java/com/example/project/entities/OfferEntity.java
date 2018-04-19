package com.example.project.entities;

import com.example.project.entities.enums.EOfferStatus;

public class OfferEntity {

	protected Integer id;
	protected String offerName;
	protected String offerCreated;
	protected String offerExpires;
	protected Double regularPrice;
	protected Double acttionPrice;
	protected String imagePath;
	protected Integer availableOffers;
	protected Integer boughtOffers;
	protected EOfferStatus offerstatus;
	protected static Integer counterOffer = 1;

	public OfferEntity() {
		super();
		this.id=counterOffer;
		counterOffer++;
	}

	public Integer getId() {
		return id;
	}

	//private void setId(Integer id) {
		//this.id = id;
	//}

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

	@Override
	public String toString() {
		return "OfferEntity [id=" + id + ", offerName=" + offerName + ", offerCreated=" + offerCreated
				+ ", offerExpires=" + offerExpires + ", regularPrice=" + regularPrice + ", acttionPrice=" + acttionPrice
				+ ", imagePath=" + imagePath + ", availableOffers=" + availableOffers + ", boughtOffers=" + boughtOffers
				+ ", offerstatus=" + offerstatus + "]";
	}

}
