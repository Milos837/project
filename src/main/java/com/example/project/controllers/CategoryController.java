package com.example.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.controllers.util.RESTError;
import com.example.project.entities.CategoryEntity;
import com.example.project.repositories.CategoryRepository;
import com.example.project.repositories.OfferRepository;
import com.example.project.security.Views;
import com.example.project.services.BillService;
import com.fasterxml.jackson.annotation.JsonView;

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
	@JsonView(Views.Public.class)
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<?> getCategories() {
		return new ResponseEntity<List<CategoryEntity>>((List<CategoryEntity>) categoryRepository.findAll(), HttpStatus.OK);
	}

	// Dodaj novu kategoriju TESTIRAO
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<?> addCategory(@RequestBody CategoryEntity category) {
		return new ResponseEntity<CategoryEntity>(categoryRepository.save(category), HttpStatus.OK);
	}

	// Azuriraj kategoriju po ID-u TESTIRAO
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateCategory(@PathVariable Integer id, @RequestBody CategoryEntity cat) {
		if (categoryRepository.existsById(id)) {
			CategoryEntity category = categoryRepository.findById(id).get();
			category.setCategoryName(cat.getCategoryName());
			category.setCategoryDescription(cat.getCategoryDescription());
			return new ResponseEntity<CategoryEntity>(categoryRepository.save(category), HttpStatus.OK);
		}
		return new ResponseEntity<RESTError>(new RESTError(3, "Category not found"), HttpStatus.NOT_FOUND);
	}

	// Obrisi kategoriju po ID-u TESTIRAO
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteCategory(@PathVariable Integer id) {
		if (categoryRepository.existsById(id) 
				&& !offerRepository.existsByCategory(categoryRepository.findById(id).get())
				&& billService.findActiveByCategory(id).size() == 0) {
			CategoryEntity temp = categoryRepository.findById(id).get();
			categoryRepository.deleteById(id);
			return new ResponseEntity<CategoryEntity>(temp, HttpStatus.OK);
		}
		return new ResponseEntity<RESTError>(
				new RESTError(4, "Category has active bills or offers, or doesn't exists."), HttpStatus.BAD_REQUEST);
	}

	// Vrati kategoriju po ID-u	TESTIRAO
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getCategory(@PathVariable Integer id) {
		if (categoryRepository.existsById(id)) {
			return new ResponseEntity<CategoryEntity>(categoryRepository.findById(id).get(), HttpStatus.OK);
		}
		return new ResponseEntity<RESTError>(new RESTError(3, "Category not found"), HttpStatus.NOT_FOUND);
	}

}
