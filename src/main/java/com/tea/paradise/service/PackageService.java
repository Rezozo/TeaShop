package com.tea.paradise.service;

import com.tea.paradise.dto.packages.ShortOrderPackageDto;
import com.tea.paradise.model.Package;
import com.tea.paradise.repository.PackageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class PackageService {
    PackageRepository packageRepository;

    public List<Package> saveAll(List<Package> packages) {
        return packageRepository.saveAll(packages);
    }

    public List<String> isValid(List<ShortOrderPackageDto> shortOrderPackageDtos) {
        List<String> invalidProductsTitle = new ArrayList<>();
        for (ShortOrderPackageDto validateProduct : shortOrderPackageDtos) {
            Package pack = packageRepository.findById(validateProduct.getPackageId()).orElseThrow();
            if (pack.getQuantity() <= validateProduct.getCount() || !pack.getProduct().isActive()) {
                invalidProductsTitle.add(pack.getProduct().getTitle());
            }
        }
        return invalidProductsTitle;
    }

    public Package getById(Long id) {
        return packageRepository.findById(id).orElseThrow();
    }
}
