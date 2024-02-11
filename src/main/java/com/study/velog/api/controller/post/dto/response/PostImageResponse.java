package com.study.velog.api.controller.post.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostImageResponse {
    private String imageUrl;
    private int imageOrder;
}
