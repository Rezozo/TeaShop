package com.tea.paradise.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.tea.paradise.model.Image;
import com.tea.paradise.repository.ImagesRepository;
import jakarta.validation.ConstraintViolationException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ImageService {
    final ImagesRepository imagesRepository;

    @Value("${s3config.bucket}")
    String bucket;
    @Value("${s3config.access}")
    String accessKey;
    @Value("${s3config.key}")
    String secretKey;
    @Value("${s3config.endpoint}")
    String endpoint;
    @Value("${s3config.region}")
    String region;

    @SneakyThrows
    public List<Image> saveImages(List<MultipartFile> files) {
        AWSCredentials credentials = new BasicAWSCredentials(
                accessKey, secretKey
        );

        AmazonS3 s3client = AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
                        endpoint,
                        region
                ))
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();

        if(!s3client.doesBucketExistV2(bucket)) {
            throw new NoSuchElementException("S3 настроен неверно, такой корзины не существует");
        }

        return files.parallelStream()
                .map(file -> {
                    try {
                        ObjectMetadata metadata = new ObjectMetadata();
                        metadata.setContentLength(file.getBytes().length);
                        InputStream inputStream = file.getInputStream();
                        s3client.putObject(bucket, file.getOriginalFilename(), inputStream, metadata);
                        String url = s3client.getUrl(bucket, file.getOriginalFilename()).toExternalForm();
                        return imagesRepository.save(new Image().setImageUrl(url));
                    } catch (IOException e) {
                        throw new ConstraintViolationException("Невозможно сохранить изображение, попробуйте снова позже", null);
                    }
                })
                .toList();
    }
}
