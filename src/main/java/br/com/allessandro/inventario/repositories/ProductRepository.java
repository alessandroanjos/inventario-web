package br.com.allessandro.inventario.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.allessandro.inventario.entities.Category;
import br.com.allessandro.inventario.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
