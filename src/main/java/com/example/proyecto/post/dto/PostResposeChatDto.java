package com.example.proyecto.post.dto;

import lombok.Data;

@Data
public class PostResposeChatDto {
    private String content;
    private Long chatID;
    private String aiModel;

}
