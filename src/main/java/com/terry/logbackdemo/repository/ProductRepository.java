package com.terry.logbackdemo.repository;

import com.terry.logbackdemo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByOrderByIdxAsc();
}
