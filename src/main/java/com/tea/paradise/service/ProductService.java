package com.tea.paradise.service;

import com.tea.paradise.model.Product;
import com.tea.paradise.repository.PackageBucketRepository;
import com.tea.paradise.repository.ProductRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProductService {
    PackageBucketRepository packageBucketRepository;
    ProductRepository productRepository;

     public Product createOrUpdate(Product productEntity) {
         return productRepository.save(productEntity);
     }

     public Product getById(Long productId) {
         Product product = productRepository.findById(productId).orElseThrow();
         if (!product.isActive()) {
             throw new ConstraintViolationException("Упс, данный продукт недоступен", null);
         }
         return product;
     }

     @Transactional
     public void deleteById(Long productId) {
         Product product = getById(productId);
         product.setActive(false);
         product.getFavoriteUsers().clear();
         packageBucketRepository.deleteAllByPack_Product_Id(productId);
         productRepository.save(product);
     }
}
