package com.example.reservation.domain.db.dto;

import com.example.reservation.domain.db.model.User;
import com.example.reservation.domain.db.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String username;

    private UserRole roles;

    private String name;

    public static UserDto form(User user){
        return UserDto.builder()
            .username(user.getUsername())
            .roles(user.getRoles())
            .name(user.getNickname())
            .build();
    }
}
