package com.tea.paradise.service;

import com.tea.paradise.model.Product;
import com.tea.paradise.repository.PackageBucketRepository;
import com.tea.paradise.repository.ProductRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static com.tea.paradise.config.CacheConfig.PRODUCT_INFO;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ProductService {
    PackageBucketRepository packageBucketRepository;
    ProductRepository productRepository;

     public Product create(Product productEntity) {
         return productRepository.save(productEntity);
     }

     //@Cacheable(value = PRODUCT_INFO, key = "'products:' + #productId") TODO fix it late (review save)
     public Product getById(Long productId) {
         Product product = productRepository.findById(productId).orElseThrow();
         if (!product.isActive()) {
             throw new ConstraintViolationException("Упс, данный продукт недоступен", null);
         }
         return product;
     }

     @Transactional
     @CacheEvict(value = PRODUCT_INFO, key = "'products:' + #productId")
     public void deleteById(Long productId) {
         Product product = getById(productId);
         product.setActive(false);
         product.getFavoriteUsers().clear();
         packageBucketRepository.deleteAllByPack_Product_Id(productId);
         productRepository.save(product);
     }
}
