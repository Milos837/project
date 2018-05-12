package com.example.project.entities.dto;

import java.util.List;

public class ReportDto {
	
	private String categoryName;
	private List<ReportItemDto> reportList;
	private Double sumOfIncomes;
	private Integer totalNumberOfSoldOffers;
	
	public ReportDto() {
		super();
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public List<ReportItemDto> getReportList() {
		return reportList;
	}
	public void setReportList(List<ReportItemDto> reportList) {
		this.reportList = reportList;
	}
	public Double getSumOfIncomes() {
		return sumOfIncomes;
	}
	public void setSumOfIncomes(Double sumOfIncomes) {
		this.sumOfIncomes = sumOfIncomes;
	}
	public Integer getTotalNumberOfSoldOffers() {
		return totalNumberOfSoldOffers;
	}
	public void setTotalNumberOfSoldOffers(Integer totalNumberOfSoldOffers) {
		this.totalNumberOfSoldOffers = totalNumberOfSoldOffers;
	}

}
