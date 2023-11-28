package com.app.spendeasyjava.service.interfaces;

import com.app.spendeasyjava.domain.DTO.UserDTO;
import com.app.spendeasyjava.domain.entities.User;
import com.app.spendeasyjava.domain.requests.ChangePasswordRequest;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

public interface UserService {
    public void changePassword(ChangePasswordRequest request, Principal connectedUser);
    public UserDTO getUserProfile(Principal connectedUser);
    public void deleteUser(UUID userId, Principal connectedUser) throws IllegalAccessException;
    public User getUser(Principal connectedUser);
    public List<UserDTO> getAllUsers(Principal connectedUser) throws IllegalAccessException;
}
