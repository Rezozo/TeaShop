package com.tea.paradise.service;

import com.tea.paradise.enums.ParentCategory;
import com.tea.paradise.model.Category;
import com.tea.paradise.model.Image;
import com.tea.paradise.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.tea.paradise.config.CacheConfig.CATEGORY_INFO;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CategoryService {
    CategoryRepository categoryRepository;
    ImageService imageService;

    @SneakyThrows
    @CacheEvict(value = CATEGORY_INFO, key = "'categories:' + #parentCategory.name()")
    public Category create(MultipartFile file, String title, ParentCategory parentCategory) {
        Image image = imageService.saveImages(List.of(file)).stream()
                .findFirst().orElseThrow();
        return categoryRepository.save(new Category()
                .setImage(image)
                .setName(title)
                .setParentCategory(parentCategory));
    }

    @Cacheable(value = CATEGORY_INFO, key = "'categories:' + #parentCategory.name()")
    public List<Category> getAllByParent(ParentCategory parentCategory) {
        return categoryRepository.findAllByParentCategory(parentCategory);
    }
}
