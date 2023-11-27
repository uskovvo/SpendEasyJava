package com.app.spendeasyjava.domain.DTO;

import com.app.spendeasyjava.domain.entities.User;
import com.app.spendeasyjava.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private UUID userId;
    private String name;
    private Role role;

    public static UserDTO toDto(User user) {
        return UserDTO
                .builder()
                .userId(user.getId())
                .name(user.getName())
                .role(user.getRole())
                .build();
    }
}
