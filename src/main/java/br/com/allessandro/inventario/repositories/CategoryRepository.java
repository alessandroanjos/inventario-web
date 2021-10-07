package br.com.allessandro.inventario.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.allessandro.inventario.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
