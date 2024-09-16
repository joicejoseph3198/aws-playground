package com.example.aws_playground.service.impl;

import com.example.aws_playground.service.S3Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
public class S3ServiceImpl implements S3Service {
    @Value("${aws.s3.bucket}")
    private String bucketName;
    private final S3Client s3Client;
    private static final String DIRECTORY_NAME = "aws-playground-images";

    public S3ServiceImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public String imageUpload(MultipartFile file) throws IOException {

        try {
            String key = DIRECTORY_NAME + "/" + file.getOriginalFilename();

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .contentType("image/jpeg")
                    .key(key)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
            return getPublicUrl(key);

        }catch (IOException exception){
            throw exception;
        }

    }

    private String getPublicUrl(String key) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s",
                bucketName, s3Client.serviceClientConfiguration().region().toString(), key);
    }
}
