package com.buyershield.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buyershield.backend.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}