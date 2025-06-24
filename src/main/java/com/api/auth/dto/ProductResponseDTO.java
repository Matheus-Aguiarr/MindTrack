package com.api.auth.dto;

import com.api.auth.domain.product.ProductModel;

public record ProductResponseDTO(Long id, String name, Double price) {

    public ProductResponseDTO(ProductModel model) {
        this(model.getId(), model.getName(), model.getPrice());
    }
}
