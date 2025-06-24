package com.api.auth.domain.product;

import com.api.auth.dto.ProductRequestDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;

    public ProductModel(Long id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public ProductModel() {}

    public ProductModel(ProductRequestDTO dto) {
        this.name = dto.name();
        this.price = dto.price();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }
}
