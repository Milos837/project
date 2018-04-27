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

@RestController
@RequestMapping(value = "/project/categories")
public class CategoryController {

	@Autowired
	private CategoryRepository categoryRepository;

	// Vrati sve kategorije
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<CategoryEntity> getCategories() {
		return (List<CategoryEntity>) categoryRepository.findAll();
	}

	// Dodaj novu kategoriju
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public CategoryEntity addCategory(@RequestBody CategoryEntity category) {
		return categoryRepository.save(category);
	}

	// Azuriraj kategoriju po ID-u
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public CategoryEntity updateCategory(@PathVariable Integer id, @RequestBody CategoryEntity cat) {
		CategoryEntity category = categoryRepository.findById(id).get();
		category.setCategoryName(cat.getCategoryName());
		category.setCategoryDescription(cat.getCategoryDescription());
		return categoryRepository.save(category);
	}

	// Obrisi kategoriju po ID-u
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public CategoryEntity deleteCategory(@PathVariable Integer id) {
		CategoryEntity temp = categoryRepository.findById(id).get();
		categoryRepository.deleteById(id);
		return temp;
	}

	// Vrati kategoriju po ID-u
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public CategoryEntity getCategory(@PathVariable Integer id) {
		return categoryRepository.findById(id).get();
	}

}
