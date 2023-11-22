package com.app.spendeasyjava.domain.requests;

import com.app.spendeasyjava.domain.enums.Role;
import com.app.spendeasyjava.exceptions.ValidPassword;
import com.app.spendeasyjava.exceptions.ValidUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ValidUsername
@ValidPassword
@Validated
public class RegisterRequest {

    private String name;
    @NotNull
    private String email;
    private String password;
    @NotNull
    private String confirmPassword;
    private Role role;
}
