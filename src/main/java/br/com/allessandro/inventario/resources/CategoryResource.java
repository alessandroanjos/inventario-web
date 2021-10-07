package br.com.allessandro.inventario.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.allessandro.inventario.dto.CategoryDTO;
import br.com.allessandro.inventario.services.CategoryService;

/**
 * 
 * 
 * 
 * @author allessandroanjos
 *
 */
@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

	@Autowired
	private CategoryService  categoryService;
	
	@GetMapping(value = "all")
	public ResponseEntity<List<CategoryDTO>> findAll(){
		
		List<CategoryDTO> list = categoryService.findAll();	
		return ResponseEntity.ok().body(list);
	}
}
