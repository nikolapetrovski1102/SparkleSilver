package com.example.springsilver;



import com.example.springsilver.models.Category;
import com.example.springsilver.models.Product;
import com.example.springsilver.models.dto.ProductDto;
import com.example.springsilver.models.exceptions.CategoryNotFoundException;
import com.example.springsilver.models.exceptions.ProductNotFoundException;
import com.example.springsilver.repository.CategoryRepository;
import com.example.springsilver.repository.ProductRepository;
import com.example.springsilver.service.ProductService;
import com.example.springsilver.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    public void testFindAll() {

        List<Product> products = List.of(new Product(), new Product());
        when(productRepository.findAll()).thenReturn(products);


        List<Product> result = productService.findAll();


        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productRepository).findAll();
    }

    @Test
    public void testFindByName() {

        String name = "Test Product";
        Product product = new Product();
        when(productRepository.findByName(name)).thenReturn(Optional.of(product));


        Optional<Product> result = productService.findByName(name);


        assertTrue(result.isPresent());
        assertEquals(product, result.get());
        verify(productRepository).findByName(name);
    }

    @Test
    public void testFindById() {

        Long id = 1L;
        Product product = new Product();
        when(productRepository.findById(id)).thenReturn(Optional.of(product));


        Optional<Product> result = productService.findById(id);


        assertTrue(result.isPresent());
        assertEquals(product, result.get());
        verify(productRepository).findById(id);
    }

    @Test
    public void testSave() {

        Long categoryId = 1L;
        String name = "Test Product";
        String description = "Description";
        float price = 10.0f;
        Long quantity = 5L;
        String imagePathUrl = "image.jpg";
        Category category = new Category();
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));


        Optional<Product> result = productService.save(name, description, price, categoryId, quantity, imagePathUrl);


        assertTrue(result.isPresent());
        Product savedProduct = result.get();
        assertEquals(name, savedProduct.getName());
        assertEquals(description, savedProduct.getDescription());
        assertEquals(price, savedProduct.getPrice());
        assertEquals(quantity, savedProduct.getQuantity());
        assertEquals(category, savedProduct.getCategory());
        verify(productRepository).deleteByName(name);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    public void testEdit_Success() {
        // Arrange
        Long id = 1L;
        String name = "Updated Product";
        String description = "Updated Description";
        float price = 20.0f;
        Long categoryId = 2L;
        Long quantity = 10L;
        Product product = new Product();
        product.setDescription("Initial Description"); // Set initial description
        Category category = new Category();
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Optional<Product> result = productService.edit(id, name, description, price, categoryId, quantity);

        // Assert
        assertTrue(result.isPresent());
        Product editedProduct = result.get();
        assertEquals(name, editedProduct.getName());
        assertEquals(description, editedProduct.getDescription()); // Check updated description
        assertEquals(price, editedProduct.getPrice());
        assertEquals(quantity, editedProduct.getQuantity());
        assertEquals(category, editedProduct.getCategory());
        verify(productRepository).findById(id);
        verify(categoryRepository).findById(categoryId);
        verify(productRepository).save(any(Product.class));
    }
    @Test
    public void testEdit_NotFound() {

        Long id = 1L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());


        assertThrows(ProductNotFoundException.class, () -> productService.edit(id, "Updated Product", "Updated Description", 20.0f, 2L, 10L));
        verify(productRepository).findById(id);
        verifyNoMoreInteractions(productRepository, categoryRepository);
    }

    @Test
    public void testDeleteById() {

        Long id = 1L;
        doNothing().when(productRepository).deleteById(id);


        productService.deleteById(id);


        verify(productRepository).deleteById(id);
    }

}
