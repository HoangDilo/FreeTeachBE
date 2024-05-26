package com.example.freeteachbe.DTO.ReturnPayload.ReturnData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemPostData {
    private String description;
    private String image_url;
    private String subject_name;
}
