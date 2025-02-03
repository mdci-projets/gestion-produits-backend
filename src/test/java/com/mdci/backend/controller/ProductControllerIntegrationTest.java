package com.mdci.backend.controller;

import com.mdci.backend.config.JwtUtils;
import com.mdci.backend.model.Product;
import com.mdci.backend.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private JwtUtils jwtUtils;

    private JwtTestUtil jwtTestUtil;

    private String adminToken;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll(); // Nettoyage de la base pour chaque test

        // Générer un token JWT pour un utilisateur ADMIN
        jwtTestUtil = new JwtTestUtil(jwtUtils); // Utilitaire pour générer les tokens
        adminToken = jwtTestUtil.generateAdminToken(); // Générer un token pour ADMIN
    }

    @Test
    void getAllProducts_ShouldReturnProducts() throws Exception {
        //
        Product product1 = new Product();
        product1.setName("Product1");
        product1.setDescription("Description1");
        product1.setPrice(10.0f);

        Product product2 = new Product();
        product2.setName("Product2");
        product2.setDescription("Description2");
        product2.setPrice(20.0f);
        productRepository.saveAll(List.of(product1, product2));

        // When & Then
        mockMvc.perform(get("/api/products")
                        .header("Authorization", "Bearer " + adminToken) // Inclure le JWT
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Product1"))
                .andExpect(jsonPath("$.content[1].name").value("Product2"));
    }

    @Test
    void getProductById_ShouldReturnProduct_WhenProductExists() throws Exception {
        // Arrange
        Product product = new Product();
        product.setName("Product1");
        product.setDescription("Description1");
        product.setPrice(10.0f);
        product = productRepository.save(product);

        // When & Then
        mockMvc.perform(get("/api/products/{id}", product.getId())
                        .header("Authorization", "Bearer " + adminToken) // Inclure le JWT
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.name").value("Product1"));
    }

    @Test
    void createProduct_ShouldReturnCreatedProduct() throws Exception {
        // Arrange
        String newProductJson = """
                    {
                        "name": "Product1",
                        "description": "Description1",
                        "price": 10.0
                    }
                """;

        // When & Then
        mockMvc.perform(post("/api/products")
                        .header("Authorization", "Bearer " + adminToken) // Inclure le JWT
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newProductJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Product1")))
                .andExpect(jsonPath("$.description", is("Description1")))
                .andExpect(jsonPath("$.price", is(10.0)));
    }

    @Test
    void createProduct_ShouldReturnBadRequest_WhenInvalidData() throws Exception {
        // Arrange
        String invalidProductJson = """
                    {
                        "name": "",
                        "description": "Description1",
                        "price": -5.0
                    }
                """;

        // When & Then
        mockMvc.perform(post("/api/products")
                        .header("Authorization", "Bearer " + adminToken) // Inclure le JWT
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidProductJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name", is("Name is required")))
                .andExpect(jsonPath("$.price", is("Price must be greater than 0")));
    }

    @Test
    void createProduct_ShouldReturnUnauthorized_WhenNotAuthenticated() throws Exception {
        // Arrange
        String newProductJson = """
                    {
                        "name": "Product1",
                        "description": "Description1",
                        "price": 10.0
                    }
                """;

        // Act & Assert
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newProductJson))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void createProduct_ShouldReturnForbidden_WhenAuthenticatedAsUser() throws Exception {
        // Arrange
        String userToken = jwtTestUtil.generateUserToken();

        String newProductJson = """
                    {
                        "name": "Product1",
                        "description": "Description1",
                        "price": 10.0
                    }
                """;

        // Act & Assert
        mockMvc.perform(post("/api/products")
                        .header("Authorization", "Bearer " + userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newProductJson))
                .andExpect(status().isForbidden());
    }

    @Test
    void updateProduct_ShouldReturnUpdatedProduct() throws Exception {
        // Arrange
        Product savedProduct = productRepository.save(new Product(null, "Product1", "Description1", 10.0f));
        Long productId = savedProduct.getId();

        String updatedProductJson = """
                    {
                        "name": "UpdatedProduct",
                        "description": "UpdatedDescription",
                        "price": 15.0
                    }
                """;

        // When & Then
        mockMvc.perform(put("/api/products/{id}", productId)
                        .header("Authorization", "Bearer " + adminToken) // Inclure le JWT
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedProductJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("UpdatedProduct")))
                .andExpect(jsonPath("$.description", is("UpdatedDescription")))
                .andExpect(jsonPath("$.price", is(15.0)));
    }

    @Test
    void updateProduct_ShouldReturnNotFound_WhenProductDoesNotExist() throws Exception {
        // Arrange
        String updatedProductJson = """
                    {
                        "name": "UpdatedProduct",
                        "description": "UpdatedDescription",
                        "price": 15.0
                    }
                """;

        // When & Then
        mockMvc.perform(put("/api/products/{id}", 999)
                        .header("Authorization", "Bearer " + adminToken) // Inclure le JWT
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedProductJson))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is("Product not found")))
                .andExpect(jsonPath("$.message", is("Product with ID 999 not found")));
    }

    @Test
    void deleteProduct_ShouldReturnNoContent() throws Exception {
        // Arrange
        Product savedProduct = productRepository.save(new Product(null, "Product1", "Description1", 10.0f));
        Long productId = savedProduct.getId();

        // When & Then
        mockMvc.perform(delete("/api/products/{id}", productId)
                        .header("Authorization", "Bearer " + adminToken) // Inclure le JWT
                )
                .andExpect(status().isNoContent());

        // Vérifiez que le produit est supprimé
        assertFalse(productRepository.findById(productId).isPresent());
    }

    @Test
    void deleteProduct_ShouldReturnNotFound_WhenProductDoesNotExist() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/products/{id}", 999)
                        .header("Authorization", "Bearer " + adminToken) // Inclure le JWT
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is("Product not found")))
                .andExpect(jsonPath("$.message", is("Product with ID 999 not found")));
    }

}
