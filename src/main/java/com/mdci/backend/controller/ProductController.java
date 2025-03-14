package com.mdci.backend.controller;

import com.mdci.backend.dto.PagedResponse;
import com.mdci.backend.dto.ProductDTO;
import com.mdci.backend.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Validated
public class ProductController {

    private final ProductService productService;

    private final PaginationProperties paginationProperties;

    @GetMapping
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')") // Autorisé pour USER et ADMIN
    public ResponseEntity<PagedResponse<ProductDTO>> getProducts(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        // Appliquer les valeurs par défaut si aucun paramètre n'est fourni
        int pageNumber = (page == null) ? paginationProperties.getDefaultPage() : page;
        int pageSize = (size == null) ? paginationProperties.getDefaultSize() : size;

        // Limiter la taille maximale de la page
        if (pageSize > paginationProperties.getMaxSize()) {
            pageSize = paginationProperties.getMaxSize();
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ProductDTO> productPage = productService.getAllProducts(pageable);
        return ResponseEntity.ok(new PagedResponse<>(productPage));
    }

    @GetMapping(value = "{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')") // Autorisé uniquement pour ADMIN
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.createProduct(productDTO));
    }

    @PutMapping(value = "{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.updateProduct(id, productDTO));
    }

    @DeleteMapping(value = "{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
