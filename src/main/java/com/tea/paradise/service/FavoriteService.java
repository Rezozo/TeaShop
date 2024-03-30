package com.tea.paradise.service;

import com.tea.paradise.model.FavoriteEntity;
import com.tea.paradise.repository.FavoriteRepository;
import com.tea.paradise.repository.ProductRepository;
import com.tea.paradise.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class FavoriteService {
    FavoriteRepository favoriteRepository;
    UserRepository userRepository;
    ProductRepository productRepository;

    public String updateFavorite(Long userId, Long productId) {
        Long favoriteId = existsByUserIdAndProductId(userId, productId);
        if (Objects.nonNull(favoriteId)) {
            favoriteRepository.deleteById(favoriteId);
            return "Удалено из избранного";
        } else {
            favoriteRepository.save(new FavoriteEntity()
                    .setUser(userRepository.findById(userId).orElseThrow())
                    .setProduct(productRepository.findById(productId).orElseThrow()));
            return "Добавлено в избранное";
        }
    }

    private Long existsByUserIdAndProductId(Long userId, Long productId) {
        return favoriteRepository.findByUser_IdAndProduct_Id(userId, productId)
                .map(FavoriteEntity::getId)
                .orElse(null);
    }
}
