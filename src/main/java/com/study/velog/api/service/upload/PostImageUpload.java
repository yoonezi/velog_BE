package com.study.velog.api.service.upload;

import org.springframework.web.multipart.MultipartFile;

public interface PostImageUpload {
    String uploadPostImage(MultipartFile multipartFile);
}
