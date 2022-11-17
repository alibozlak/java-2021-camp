package bozlak.java2021.repository.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import bozlak.java2021.entities.concretes.Product;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    
}
