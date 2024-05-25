package com.example.freeteachbe.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user_account")
@Data
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String email;
    @Column(length = 500)
    private String avatarURL;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private int money = 0;
    @Column
    private boolean isFirstLogin = true;
    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    public UserEntity(String name, String email, String avatarURL, String username, String password, Role role) {
        this.name = name;
        this.email = email;
        this.avatarURL = avatarURL;
        this.username = username;
        this.password = password;
        this.role = role;
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

    public UserEntity() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
