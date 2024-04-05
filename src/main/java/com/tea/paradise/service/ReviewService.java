package com.tea.paradise.service;

import com.tea.paradise.enums.UserRole;
import com.tea.paradise.model.Review;
import com.tea.paradise.model.Users;
import com.tea.paradise.repository.ReviewRepository;
import jakarta.validation.ConstraintViolationException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Objects;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ReviewService {
    ReviewRepository reviewRepository;
    UserService userService;

    public Review getReviewByUserAndProduct(Long productId) {
        return reviewRepository.findByUser_IdAndProduct_Id(userService.getLoggedUserInfo().getId(), productId)
                .orElse(null);
    }

    public Review saveReview(Review review) {
        if (reviewRepository.existsByUser_IdAndProduct_Id(review.getUser().getId(), review.getProduct().getId())) {
            throw new ConstraintViolationException(
                    "Ваш отзыв о данном продукте уже существует", null
            );
        }
        return reviewRepository.save(review);
    }

    public Review updateReview(Review review) {
        if (!reviewRepository.existsById(review.getId())) {
            throw new ConstraintViolationException(
                    MessageFormat.format("Отзыва с id: {0} не существует", review.getId()), null
            );
        }
        return reviewRepository.save(review);
    }

    public void deleteById(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow();
        Users users = userService.getLoggedUserInfo();
        if (!Objects.equals(review.getUser().getId(), users.getId()) &&
                !(users.getRole().getTitle().equals(UserRole.ADMIN) ||
                        users.getRole().getTitle().equals(UserRole.MANAGER))) {
            throw new ConstraintViolationException(
                    "Невозможно удалить отзыв другого человека", null
            );
        }
        reviewRepository.deleteById(id);
    }
}
