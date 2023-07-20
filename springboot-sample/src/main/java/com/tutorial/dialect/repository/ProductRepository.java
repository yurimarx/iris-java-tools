package com.tutorial.dialect.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tutorial.dialect.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

}