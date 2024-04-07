package com.tea.paradise.service;

import com.tea.paradise.dto.packages.ShortPackageDto;
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

    public List<String> isValid(List<ShortPackageDto> shortPackageDtos) {
        List<String> invalidProductsTitle = new ArrayList<>();
        for (ShortPackageDto validateProduct : shortPackageDtos) {
            Package pack = packageRepository.findById(validateProduct.getPackageId()).orElseThrow();
            if (pack.getQuantity() >= validateProduct.getCount()) {
                invalidProductsTitle.add(pack.getProduct().getTitle());
            }
        }
        return invalidProductsTitle;
    }
}
