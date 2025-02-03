package com.mdci.backend.service;

import com.mdci.backend.dto.ProductDTO;
import com.mdci.backend.model.Product;
import com.mdci.backend.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.mdci.backend.exceptions.ProductNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProductById_ShouldReturnProduct_WhenProductExists() {
        // Arrange
        Long productId = 1L;
        Product product = new Product(productId, "Product1", "Description1", 10.0f);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        ProductDTO result = productService.getProductById(productId);

        // Assert
        assertNotNull(result);
        assertEquals(productId, result.getId());
        assertEquals("Product1", result.getName());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void getProductById_ShouldThrowException_WhenProductDoesNotExist() {
        // Arrange
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(productId));
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void createProduct_ShouldReturnSavedProduct() {
        // Arrange
        ProductDTO inputProductDTO = createProductDTO(null, "Product1", "Description1", 10.0f);
        Product savedProduct = createProductEntity(1L, "Product1", "Description1", 10.0f);

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        // Act
        ProductDTO result = productService.createProduct(inputProductDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Product1", result.getName());
        assertEquals("Description1", result.getDescription());
        assertEquals(10.0f, result.getPrice());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void updateProduct_ShouldReturnUpdatedProduct_WhenProductExists() {
        // Arrange
        Long productId = 1L;
        Product existingProduct = createProductEntity(productId, "OldProduct", "OldDescription", 5.0f);
        ProductDTO updatedProductDTO = createProductDTO(null, "UpdatedProduct", "UpdatedDescription", 15.0f);
        Product updatedProduct = createProductEntity(productId, "UpdatedProduct", "UpdatedDescription", 15.0f);

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        // Act
        ProductDTO result = productService.updateProduct(productId, updatedProductDTO);

        // Assert
        assertNotNull(result);
        assertEquals(productId, result.getId());
        assertEquals("UpdatedProduct", result.getName());
        assertEquals("UpdatedDescription", result.getDescription());
        assertEquals(15.0, result.getPrice());
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void updateProduct_ShouldThrowException_WhenProductDoesNotExist() {
        // Arrange
        Long productId = 1L;
        ProductDTO updatedProductDTO = createProductDTO(null, "UpdatedProduct", "UpdatedDescription", 15.0f);

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(productId, updatedProductDTO));
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void deleteProduct_ShouldDeleteProduct_WhenProductExists() {
        // Arrange
        Long productId = 1L;
        Product existingProduct = createProductEntity(productId, "OldProduct", "OldDescription", 5.0f);

        when(productRepository.existsById(productId)).thenReturn(true);
        doNothing().when(productRepository).deleteById(productId);

        // Act
        productService.deleteProduct(productId);

        // Assert
        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    void deleteProduct_ShouldNotThrowException_WhenProductDoesNotExist() {
        // Arrange
        Long productId = 1L;

        when(productRepository.existsById(productId)).thenReturn(false);
        doNothing().when(productRepository).deleteById(productId);

        // Act & Assert
        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct(productId));
        verify(productRepository, times(1)).existsById(productId);
        verify(productRepository, never()).deleteById(any(Long.class));
    }

    private Product createProductEntity(Long id, String name, String description, float price) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        return product;
    }

    private ProductDTO createProductDTO(Long id, String name, String description, float price) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(id);
        productDTO.setName(name);
        productDTO.setDescription(description);
        productDTO.setPrice(price);
        return productDTO;
    }
}
