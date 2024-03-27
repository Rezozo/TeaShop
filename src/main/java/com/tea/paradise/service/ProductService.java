package com.tea.paradise.service;

import com.tea.paradise.model.Product;
import com.tea.paradise.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProductService {
    ProductRepository productRepository;

     public Product create(Product productEntity) {
         return productRepository.save(productEntity);
     }
}
