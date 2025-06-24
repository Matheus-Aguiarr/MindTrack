package com.api.auth.domain.product.repository;

import com.api.auth.domain.product.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {
}
