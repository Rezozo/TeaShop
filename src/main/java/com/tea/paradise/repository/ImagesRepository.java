package com.tea.paradise.repository;

import com.tea.paradise.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagesRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByIdIn(List<Long> ids);
}
