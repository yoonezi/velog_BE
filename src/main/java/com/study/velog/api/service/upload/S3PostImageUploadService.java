package com.study.velog.api.service.upload;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Primary
@RequiredArgsConstructor
@Service
public class S3PostImageUploadService implements PostImageUpload {

    public static final String IMAGE_UPLOAD_PATH_PREFIX = "images/";
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public String uploadPostImage(MultipartFile file)
    {
        try {
            String key = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            PutObjectResult putObjectResult =  amazonS3Client.putObject(bucket, IMAGE_UPLOAD_PATH_PREFIX + key, file.getInputStream(), metadata);
            log.info(putObjectResult.getMetadata().toString());
            return key;
        } catch(IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
