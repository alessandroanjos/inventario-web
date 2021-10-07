package br.com.allessandro.inventario.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.allessandro.inventario.dto.CategoryDTO;
import br.com.allessandro.inventario.entities.Category;
import br.com.allessandro.inventario.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll(){
		
		List<Category> list = categoryRepository.findAll();		
		List<CategoryDTO> listDTO = list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
		
		return listDTO;
	}
}
