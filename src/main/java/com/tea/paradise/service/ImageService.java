package com.tea.paradise.service;

import com.tea.paradise.model.Image;
import com.tea.paradise.repository.ImagesRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ImageService {
    ImagesRepository imagesRepository;

    @SneakyThrows
    public List<Long> saveImages(List<MultipartFile> files) {
        return files.stream()
                .map(file -> {
                    Image image = new Image();
                    try {
                        image.setImage(file.getBytes());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return imagesRepository.save(image).getId();
                })
                .toList();
    }
}
