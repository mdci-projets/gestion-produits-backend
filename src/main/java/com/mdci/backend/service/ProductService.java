package com.mdci.backend.service;

import com.mdci.backend.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<ProductDTO> getAllProducts(Pageable pageable); // Pagination

    ProductDTO getProductById(Long id);

    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO updateProduct(Long id, ProductDTO productDTO);

    void deleteProduct(Long id);
}