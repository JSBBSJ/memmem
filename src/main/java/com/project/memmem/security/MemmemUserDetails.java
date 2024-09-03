package com.project.memmem.security;
import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.project.memmem.domain.entity.UserEntity;

import lombok.Getter;
@Getter
public class MemmemUserDetails extends User {
    private static final long serialVersionUID = 1L;
    private final String email;
    private final String name;
    private final String nickName;
    //private final UserEntity userEntity;
    private final long userId;
    private final String number; // 수정된 부분
    //private final Map<String, Object> attributes;
    
    public MemmemUserDetails(UserEntity entity) {
        super(entity.getEmail(), entity.getPassword(),
              Set.of(new SimpleGrantedAuthority("ROLE_" + entity.getRole().name())));
        this.email = entity.getEmail();
        this.name = entity.getName();
        this.nickName = entity.getNickName();
        this.userId = entity.getUserId();
        this.number = entity.getNumber(); // 수정된 부분
    }
    
}