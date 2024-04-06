package com.example.freeteachbe.DTO.ReturnPayload.ReturnData;

public class LoginData {
    private long id;
    private boolean isFirstLogin;

    public LoginData(long id, boolean isFirstLogin) {
        this.id = id;
        this.isFirstLogin = isFirstLogin;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isFirstLogin() {
        return isFirstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        isFirstLogin = firstLogin;
    }
}
