package com.example.freeteachbe.DTO.ReturnPayload.ReturnData;

import lombok.Data;

@Data
public class UserInfoData {
    private String name;
    private String email;
    private String avatar_url;
    private int money;
}
