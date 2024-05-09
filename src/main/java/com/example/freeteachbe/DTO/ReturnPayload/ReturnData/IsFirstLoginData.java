package com.example.freeteachbe.DTO.ReturnPayload.ReturnData;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IsFirstLoginData {
    @JsonProperty("is_first_login")
    private boolean is_first_login;

}
