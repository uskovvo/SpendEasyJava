package com.app.spendeasyjava.service;

import com.app.spendeasyjava.domain.requests.ChangePasswordRequest;
import com.app.spendeasyjava.domain.entities.User;
import com.app.spendeasyjava.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        var user = getUser(connectedUser);

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    public User getUser(Principal connectedUser) {
        return getAuthUser(connectedUser);
    }

    @NotNull
    private User getAuthUser(Principal connectedUser) {
        var user = (User)((UsernamePasswordAuthenticationToken)connectedUser).getPrincipal();

        if (user == null) {
            throw new IllegalStateException("Вы не авторизованы");
        }
        return user;
    }
}
