package com.api.auth.controller;

import com.api.auth.domain.product.ProductModel;
import com.api.auth.domain.product.repository.ProductRepository;
import com.api.auth.dto.ProductRequestDTO;
import com.api.auth.dto.ProductResponseDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping()
    @Transactional
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody @Valid ProductRequestDTO dto, UriComponentsBuilder uriComponentsBuilder) {
        ProductModel productModel = new ProductModel(dto);
        productRepository.save(productModel);
        ProductResponseDTO response = new ProductResponseDTO(productModel);

        var uri = uriComponentsBuilder.path("/product/{id}").buildAndExpand(productModel.getId()).toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping()
    public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(@PageableDefault(size = 3, sort = {"price"}) Pageable pageable) {
        var page = productRepository.findAll(pageable).map(ProductResponseDTO::new);
        return ResponseEntity.ok(page);
    }

    
}
