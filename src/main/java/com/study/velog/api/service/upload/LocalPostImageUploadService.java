package com.study.velog.api.service.upload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class LocalPostImageUploadService implements PostImageUpload {

    @Value("${post.image.upload.path}")
    private String urlPath;

    @Override
    public String uploadPostImage(MultipartFile multipartFile)
    {
        String originName = multipartFile.getOriginalFilename();
        String imageFileName = extractExtension(originName);
        String savePath = UUID.randomUUID() + "_" + imageFileName + ".jpg";

        // http://localhost:8080/post/image/파일이름
        try
        {
            multipartFile.transferTo(new File(urlPath + savePath));
        } catch (Exception e)
        {
            throw new RuntimeException("이미지 업로드 실패");
        }

        return savePath;
    }

    private static String extractExtension(String fileName)
    {
        int idx = fileName.lastIndexOf(".");
        return fileName.substring(0, idx);
    }
}

