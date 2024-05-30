package com.tea.paradise.service;

import com.tea.paradise.model.Product;
import com.tea.paradise.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProductService {
    ProductRepository productRepository;

     public Product createOrUpdate(Product productEntity) {
         if (!productEntity.isActive() && Objects.nonNull(productEntity.getFavoriteUsers())) {
             productEntity.getFavoriteUsers().clear();
         }
         return productRepository.save(productEntity);
     }

     public Product getById(Long productId) {
         return productRepository.findById(productId).orElseThrow();
     }
}
