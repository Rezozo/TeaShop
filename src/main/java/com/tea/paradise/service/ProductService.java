package com.tea.paradise.service;

import com.tea.paradise.model.Product;
import com.tea.paradise.repository.ProductRepository;
import jakarta.validation.ConstraintViolationException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProductService {
    ProductRepository productRepository;

     public Product createOrUpdate(Product productEntity) {
         if (!productEntity.isActive()) {
             productEntity.getFavoriteUsers().clear();
         }
         return productRepository.save(productEntity);
     }

     public Product getById(Long productId) {
         Product product = productRepository.findById(productId).orElseThrow();
         if (!product.isActive()) {
             throw new ConstraintViolationException("Упс, данный продукт недоступен", null);
         }
         return product;
     }
}
