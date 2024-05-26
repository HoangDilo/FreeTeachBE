package com.example.freeteachbe.DTO.BodyPayload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProblemPostDTO {
    private String description;
    private String image_url;
    private Long subject_id;
}
