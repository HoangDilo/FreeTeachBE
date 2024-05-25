package com.example.freeteachbe.DTO.BodyPayload;

import lombok.Data;

@Data
public class DocumentPostDTO {
    private String description;
    private String img_url;
    private Long subject_id;
}
