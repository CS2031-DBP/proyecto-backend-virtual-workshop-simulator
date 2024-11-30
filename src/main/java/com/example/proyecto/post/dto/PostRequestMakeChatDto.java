package com.example.proyecto.post.dto;

import lombok.Data;

@Data
public class PostRequestMakeChatDto {
    private String content;
    private Long chatID;
    private String aiModel;

}
