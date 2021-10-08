package br.com.allessandro.inventario.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.allessandro.inventario.dto.CategoryDTO;
import br.com.allessandro.inventario.dto.ProductDTO;
import br.com.allessandro.inventario.entities.Category;
import br.com.allessandro.inventario.entities.Product;
import br.com.allessandro.inventario.repositories.CategoryRepository;
import br.com.allessandro.inventario.repositories.ProductRepository;
import br.com.allessandro.inventario.services.exceptions.DatabaseException;
import br.com.allessandro.inventario.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Transactional(readOnly = true)
	public List<ProductDTO> findAll(){
		
		List<Product> list = productRepository.findAll();		
		List<ProductDTO> listDTO = list.stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());
		
		return listDTO;
	}

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> optional = productRepository.findById(id);
		Product product = optional.orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
 		
		return new ProductDTO(product, product.getCategories());
	}

	@Transactional
	public ProductDTO insert(ProductDTO productDTO) {
		Product product = new Product();
		copyDtoToEntity(productDTO, product);
		product = productRepository.save(product);
		return new ProductDTO(product);
	}

	

	@Transactional
	public ProductDTO update(Long id, ProductDTO productDTO) {
		try {
			Product product = productRepository.getOne(id);
			copyDtoToEntity(productDTO, product);
			productRepository.save(product);		
			return new ProductDTO(product);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("ID not found" + id	);
		}
	}
	
	
	public void delete(Long id) {
		try {
			productRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("ID not found" + id	);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}

	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
		Page<Product> list = productRepository.findAll(pageRequest);	
			
		Page<ProductDTO> pageProductDTO = list.map(x -> new ProductDTO(x));
		
		return pageProductDTO;
	}
	
	private void copyDtoToEntity(ProductDTO productDTO, Product product) {
		product.setName(productDTO.getName());
		product.setDate(productDTO.getDate());
		product.setDescription(productDTO.getDescription());
		product.setImgUrl(productDTO.getImgUrl());
		product.setPrice(productDTO.getPrice());
		
		product.getCategories().clear();
		for (CategoryDTO dto : productDTO.getCategories()) {
			
			Category category = categoryRepository.getOne(dto.getId());
			
			product.getCategories().add(category);
		}
	}
}
