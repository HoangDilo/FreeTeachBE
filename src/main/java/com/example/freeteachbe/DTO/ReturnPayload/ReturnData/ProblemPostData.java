package com.example.freeteachbe.DTO.ReturnPayload.ReturnData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemPostData {
    private Long id;
    private String description;
    private String image_url;
    private String student_avatar_url;
    private String subject_name;
    private LocalDateTime created_at;
    private String student_name;
}
