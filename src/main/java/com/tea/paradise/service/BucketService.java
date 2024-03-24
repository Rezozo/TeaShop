package com.tea.paradise.service;

import com.tea.paradise.model.Bucket;
import com.tea.paradise.model.Users;
import com.tea.paradise.repository.BucketRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BucketService {
    BucketRepository bucketRepository;

    public void createByUser(Users users) {
        bucketRepository.save(new Bucket()
                .setUser(users)
                .setModifiedDate(ZonedDateTime.now()));
    }
}
