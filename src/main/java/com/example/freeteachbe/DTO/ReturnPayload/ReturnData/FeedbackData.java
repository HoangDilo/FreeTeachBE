package com.example.freeteachbe.DTO.ReturnPayload.ReturnData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackData {
    private Long id;
    private String student_name;
    private String student_avatar_url;
    private String description;
    private double point;
}
