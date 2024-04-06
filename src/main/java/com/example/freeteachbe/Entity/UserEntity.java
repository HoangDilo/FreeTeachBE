package com.example.freeteachbe.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.IdGeneratorType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="user_account")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name = "user_" + id;
    @Column
    private String email;
    @Column
    private String avatarURL;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private int money = 0;
    @Column
    private boolean isFirstLogin = true;

    public UserEntity(String name, String email, String avatarURL, String username, String password) {
        this.name = name;
        this.email = email;
        this.avatarURL = avatarURL;
        this.username = username;
        this.password = password;
    }

    public UserEntity(Long id, String name, String email, String avatarURL, String username, String password, int money) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.avatarURL = avatarURL;
        this.username = username;
        this.password = password;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public UserEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isFirstLogin() {
        return isFirstLogin;
    }

    public void setFirstLogin(boolean firstLogin) {
        isFirstLogin = firstLogin;
    }
}
