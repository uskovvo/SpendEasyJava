package com.app.spendeasyjava.controllers;

import com.app.spendeasyjava.domain.enums.Messages;
import com.app.spendeasyjava.domain.requests.ChangePasswordRequest;
import com.app.spendeasyjava.domain.responses.Response;
import com.app.spendeasyjava.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final MessageSource messageSource;

    @GetMapping
    public ResponseEntity<?> getAllUsers(Principal connectedUser) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new Response(HttpStatus.OK.name(), userService.getAllUsers(connectedUser)));
        } catch (IllegalAccessException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_ACCEPTABLE)
                    .body(ex.getMessage());
        }
    }

    @PatchMapping
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    )
    {
        userService.changePassword(request, connectedUser);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestParam UUID userId, Principal connectedUser) {
        try {
            userService.deleteUser(userId, connectedUser);
            return ResponseEntity.ok(messageSource.getMessage(Messages.DELETED.getMessage(), null, Locale.getDefault()));
        } catch (IllegalAccessException ex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ex.getMessage());
        }
    }
}
