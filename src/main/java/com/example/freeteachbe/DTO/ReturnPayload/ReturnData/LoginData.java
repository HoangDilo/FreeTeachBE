package com.example.freeteachbe.DTO.ReturnPayload.ReturnData;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginData {
    private String access_token;
    @JsonProperty("is_first_login")
    private boolean is_first_login;
}
