package com.study.velog.api.service.upload;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class PostImageUploadService {

    private final PostImageUpload postImageUpload;

    public PostImageUploadResponse uploadPostImage(MultipartFile multipartFile)
    {
        String url = postImageUpload.uploadPostImage(multipartFile);
        return new PostImageUploadResponse(multipartFile.getOriginalFilename(), url);
    }
}