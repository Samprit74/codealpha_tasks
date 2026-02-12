//package com.hotel_booking.service.impl;
//
//import com.hotel_booking.service.S3Service;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import software.amazon.awssdk.services.s3.S3Client;
//import software.amazon.awssdk.services.s3.model.*;
//
//import java.io.IOException;
//import java.util.UUID;
//
//@Service
//public class S3ServiceImpl implements S3Service {
//
//    private final S3Client s3Client;
//
//    @Value("${aws.s3.bucket}")
//    private String bucketName;
//
//    public S3ServiceImpl(S3Client s3Client) {
//        this.s3Client = s3Client;
//    }
//
//    @Override
//    public String uploadFile(MultipartFile file) {
//
//        String fileName = generateFileName(file.getOriginalFilename());
//
//        try {
//            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
//                    .bucket(bucketName)
//                    .key(fileName)
//                    .contentType(file.getContentType())
//                    .acl(ObjectCannedACL.PUBLIC_READ)
//                    .build();
//
//            s3Client.putObject(
//                    putObjectRequest,
//                    software.amazon.awssdk.core.sync.RequestBody.fromBytes(file.getBytes())
//            );
//
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to upload file to S3", e);
//        }
//
//        return getFileUrl(fileName);
//    }
//
//    @Override
//    public void deleteFile(String fileUrl) {
//
//        String key = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
//
//        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
//                .bucket(bucketName)
//                .key(key)
//                .build();
//
//        s3Client.deleteObject(deleteObjectRequest);
//    }
//
//    private String generateFileName(String originalFileName) {
//        return UUID.randomUUID() + "_" + originalFileName;
//    }
//
//    private String getFileUrl(String fileName) {
//        return String.format("https://%s.s3.amazonaws.com/%s", bucketName, fileName);
//    }
//}
