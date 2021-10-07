package br.com.allessandro.inventario.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.allessandro.inventario.entities.Category;
import br.com.allessandro.inventario.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	
	public List<Category> findAll(){
		return categoryRepository.findAll();
	}
}
