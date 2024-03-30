package com.tea.paradise.service;

import com.tea.paradise.model.Package;
import com.tea.paradise.repository.PackageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class PackageService {
    PackageRepository packageRepository;

    public List<Package> saveAll(List<Package> packages) {
        return packageRepository.saveAll(packages);
    }
}
