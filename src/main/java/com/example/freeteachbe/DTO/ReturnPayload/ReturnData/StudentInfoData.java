package com.example.freeteachbe.DTO.ReturnPayload.ReturnData;

import lombok.Data;

import java.util.Set;

@Data
public class StudentInfoData extends UserInfoData {
    private int grade;
    private Set<String> subjects;
}
