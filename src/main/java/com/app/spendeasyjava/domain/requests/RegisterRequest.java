package com.app.spendeasyjava.domain.requests;

import com.app.spendeasyjava.domain.enums.Role;
import com.app.spendeasyjava.exceptions.ValidUserParams;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ValidUserParams
public class RegisterRequest {

    private String name;
    private String email;
    private String password;
    private String confirmPassword;
    private Role role;
}
