package com.example.project.entities;

public class CategoryEntity {

	protected Integer id;
	protected String categoryName;
	protected String categoryDescription;
	protected static Integer counterCategory = 1;

	public CategoryEntity() {
		super();
		this.setId(counterCategory);
		counterCategory++;
	}

	public Integer getId() {
		return id;
	}

	private void setId(Integer id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	@Override
	public String toString() {
		return "CategoryEntity [id=" + id + ", categoryName=" + categoryName + ", categoryDescription="
				+ categoryDescription + "]";
	}

}
