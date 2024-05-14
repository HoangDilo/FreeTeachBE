package com.example.freeteachbe.DTO.ReturnPayload.ReturnData;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DocumentData {
    private Long id;
    private String description;
    private String image_url;
    private String subject_name;
}
