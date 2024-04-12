package com.tea.paradise.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.management.timer.Timer;
import java.util.Objects;

@Configuration
@RequiredArgsConstructor
public class CacheConfig {
    public static final String ORDERS_INFO = "orders_info";
    public static final String BUCKET_INFO = "bucket_info";
    public static final String CATEGORY_INFO = "category_info";
    public static final String PRODUCT_INFO = "product_info";

    private final CacheManager cacheManager;

    @Scheduled(fixedRate = Timer.ONE_HOUR * 3)
    public void clearAllCachesWithManager() {
        cacheManager.getCacheNames()
                .forEach(name -> Objects.requireNonNull(cacheManager.getCache(name)).clear());
    }
}
