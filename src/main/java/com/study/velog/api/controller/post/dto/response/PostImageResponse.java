package com.study.velog.api.controller.post.dto.response;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(of = {"imageUrl", "imageOrder"})
@AllArgsConstructor
@NoArgsConstructor
public class PostImageResponse {
    private String imageUrl;
    private int imageOrder;
}
