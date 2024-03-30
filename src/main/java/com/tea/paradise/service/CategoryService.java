package com.tea.paradise.service;

import com.tea.paradise.enums.ParentCategory;
import com.tea.paradise.model.Category;
import com.tea.paradise.model.Image;
import com.tea.paradise.repository.CategoryRepository;
import com.tea.paradise.repository.ImagesRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CategoryService {
    CategoryRepository categoryRepository;
    ImagesRepository imagesRepository;

    @SneakyThrows
    public Category create(MultipartFile file, String title, ParentCategory parentCategory) {
        Image image = imagesRepository.save(new Image().setImage(file.getBytes()).setFileName(file.getOriginalFilename()));
        return categoryRepository.save(new Category()
                .setImage(image)
                .setName(title)
                .setParentCategory(parentCategory));
    }

    public List<Category> getAllByParent(ParentCategory parentCategory) {
        return categoryRepository.findAllByParentCategory(parentCategory);
    }
}
