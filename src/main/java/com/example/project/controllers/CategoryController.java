package com.example.project.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.entities.CategoryEntity;

@RestController
public class CategoryController {

	List<CategoryEntity> allCategories;

	// 2.2	TESTIRANO
	public List<CategoryEntity> getDb() {
		if (allCategories == null) {
			allCategories = new ArrayList<>();

			CategoryEntity c1 = new CategoryEntity();
			c1.setCategoryName("Automobili");
			c1.setCategoryDescription("Oglasi sa polovnim automobilima");
			allCategories.add(c1);

		}
		return allCategories;
	}

	// 2.3	TESTIRANO
	@RequestMapping(value = "/project/categories", method = RequestMethod.GET)
	public List<CategoryEntity> getCategories() {
		return getDb();
	}

	// 2.4	TESTIRANO
	@RequestMapping(value = "/project/categories", method = RequestMethod.POST)
	public CategoryEntity addCategory(@RequestBody CategoryEntity category) {
		getDb().add(category);
		return category;
	}

	// 2.5	TESTIRANO
	@RequestMapping(value = "/project/categories/{id}", method = RequestMethod.PUT)
	public CategoryEntity updateCategory(@PathVariable Integer id, @RequestBody CategoryEntity cat) {
		for (CategoryEntity categoryEntity : getDb()) {
			if (categoryEntity.getId().equals(id)) {
				categoryEntity.setCategoryName(cat.getCategoryName());
				categoryEntity.setCategoryDescription(cat.getCategoryDescription());
				return categoryEntity;
			}
		}
		return null;
	}

	// 2.6	TESTIRANO
	@RequestMapping(value = "/project/categories/{id}", method = RequestMethod.DELETE)
	public CategoryEntity deleteCategory(@PathVariable Integer id) {
		for (CategoryEntity categoryEntity : getDb()) {
			if (categoryEntity.getId().equals(id)) {
				getDb().remove(categoryEntity);
				return categoryEntity;
			}
		}
		return null;
	}

	// 2.7	TESTIRANO
	@RequestMapping(value = "/project/categories/{id}", method = RequestMethod.GET)
	public CategoryEntity getCategory(@PathVariable Integer id) {
		for (CategoryEntity categoryEntity : getDb()) {
			if (categoryEntity.getId().equals(id)) {
				return categoryEntity;
			}
		}
		return null;
	}

}
