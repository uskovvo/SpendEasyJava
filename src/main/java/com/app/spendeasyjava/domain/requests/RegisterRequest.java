package com.app.spendeasyjava.domain.requests;

import com.app.spendeasyjava.domain.enums.Role;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private String name;
    @NotNull
    private String email;
    private String password;
    @NotNull
    private String confirmPassword;
    private Role role;
}
