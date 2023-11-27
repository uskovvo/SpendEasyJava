package com.app.spendeasyjava.service;

import com.app.spendeasyjava.domain.DTO.UserDTO;
import com.app.spendeasyjava.domain.entities.User;
import com.app.spendeasyjava.domain.repositories.UserRepository;
import com.app.spendeasyjava.domain.requests.ChangePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.app.spendeasyjava.domain.enums.Role.ADMIN;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MessageSource messageSource;

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        var user = getAuthUser(connectedUser);

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    public UserDTO getUserProfile(Principal connectedUser) {
        return UserDTO.toDto(getAuthUser(connectedUser));
    }

    public void deleteUser(UUID userId, Principal connectedUser) throws IllegalAccessException {
        User user = getAuthUser(connectedUser);
        if (user.getRole() != ADMIN) {
            throw new IllegalAccessException(messageSource
                    .getMessage("user.not.admin", null, Locale.getDefault()));
        }
        userRepository.deleteById(userId);
    }

    public User getUser(Principal connectedUser) {
        return getAuthUser(connectedUser);
    }

    @NotNull
    private User getAuthUser(Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        if (user == null) {
            throw new IllegalStateException(messageSource
                    .getMessage("user.not.authorized", null, Locale.getDefault()));
        }
        return user;
    }

    public List<UserDTO> getAllUsers(Principal connectedUser) throws IllegalAccessException {
        User user = getAuthUser(connectedUser);
        if (user.getRole() != ADMIN) {
            throw new IllegalAccessException(messageSource
                    .getMessage("user.not.admin", null, Locale.getDefault()));
        }
        return userRepository.findAll().stream().map(UserDTO::toDto).collect(Collectors.toList());
    }
}
