package br.com.allessandro.inventario.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.allessandro.inventario.dto.CategoryDTO;
import br.com.allessandro.inventario.entities.Category;
import br.com.allessandro.inventario.repositories.CategoryRepository;
import br.com.allessandro.inventario.services.exceptions.DatabaseException;
import br.com.allessandro.inventario.services.exceptions.ResourceNotFoundException;

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

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> optional = categoryRepository.findById(id);
		Category category = optional.orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
 		
		return new CategoryDTO(category);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO categoryDTO) {
		Category category = new Category();
		category.setName(categoryDTO.getName());
		category = categoryRepository.save(category);
		return new CategoryDTO(category);
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO categoryDTO) {
		try {
			Category category = categoryRepository.getOne(id);
			category.setName(categoryDTO.getName());
			categoryRepository.save(category);		
			return new CategoryDTO(category);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("ID not found" + id	);
		}
	}
	
	
	public void delete(Long id) {
		try {
			categoryRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("ID not found" + id	);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
}
