package com.example.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.entities.CategoryEntity;
import com.example.project.repositories.CategoryRepository;
import com.example.project.repositories.OfferRepository;
import com.example.project.services.BillService;

@RestController
@RequestMapping(value = "/api/v1/project/categories")
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private BillService billService;
	
	@Autowired
	private OfferRepository offerRepository;

	// Vrati sve kategorije TESTIRAO
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<CategoryEntity> getCategories() {
		return (List<CategoryEntity>) categoryRepository.findAll();
	}

	// Dodaj novu kategoriju TESTIRAO
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public CategoryEntity addCategory(@RequestBody CategoryEntity category) {
		return categoryRepository.save(category);
	}

	// Azuriraj kategoriju po ID-u TESTIRAO
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public CategoryEntity updateCategory(@PathVariable Integer id, @RequestBody CategoryEntity cat) {
		if (categoryRepository.existsById(id)) {
			CategoryEntity category = categoryRepository.findById(id).get();
			category.setCategoryName(cat.getCategoryName());
			category.setCategoryDescription(cat.getCategoryDescription());
			return categoryRepository.save(category);
		}
		return null;
	}

	// Obrisi kategoriju po ID-u TESTIRAO
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public CategoryEntity deleteCategory(@PathVariable Integer id) {
		if (categoryRepository.existsById(id) 
				&& !offerRepository.existsByCategory(categoryRepository.findById(id).get())
				&& billService.findActiveByCategory(id).size() == 0) {
			CategoryEntity temp = categoryRepository.findById(id).get();
			categoryRepository.deleteById(id);
			return temp;
		}
		return null;
	}

	// Vrati kategoriju po ID-u	TESTIRAO
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public CategoryEntity getCategory(@PathVariable Integer id) {
		if (categoryRepository.existsById(id)) {
			return categoryRepository.findById(id).get();
		}
		return null;
	}

}
