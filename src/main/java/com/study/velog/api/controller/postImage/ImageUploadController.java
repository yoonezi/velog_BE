package com.study.velog.api.controller.postImage;

import com.study.velog.api.service.upload.PostImageUploadResponse;
import com.study.velog.api.service.upload.PostImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/upload")
public class ImageUploadController {

    private final PostImageUploadService postImageUploadService;

    @PostMapping("/image/post")
    public PostImageUploadResponse uploadPostImage(@RequestPart MultipartFile multipartFile)
    {
        return postImageUploadService.uploadPostImage(multipartFile);
    }
}
