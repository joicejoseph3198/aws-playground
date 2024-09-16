package com.example.aws_playground.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface S3Service {
    String imageUpload(MultipartFile imageToBeUploaded) throws IOException;

}
