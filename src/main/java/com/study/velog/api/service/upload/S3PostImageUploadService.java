package com.study.velog.api.service.upload;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class S3PostImageUploadService implements PostImageUpload {


    @Override
    public String uploadPostImage(MultipartFile multipartFile) {
        return null;
    }
}
