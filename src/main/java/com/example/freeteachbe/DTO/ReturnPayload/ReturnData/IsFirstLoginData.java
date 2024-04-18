package com.example.freeteachbe.DTO.ReturnPayload.ReturnData;

public class IsFirstLoginData {
    private boolean isFirstLogin;

    public IsFirstLoginData(boolean isFirstLogin) {
        this.isFirstLogin = isFirstLogin;
    }

    public boolean isFirstLogin() {
        return isFirstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        isFirstLogin = firstLogin;
    }
}
