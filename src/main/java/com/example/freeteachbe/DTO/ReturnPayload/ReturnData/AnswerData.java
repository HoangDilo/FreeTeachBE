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
public class AnswerData {
    private Long id;
    private String teacher_name;
    private String teacher_avatar_url;
    private String answer;
    private String answer_image_url;
    private LocalDateTime created_at;
}
