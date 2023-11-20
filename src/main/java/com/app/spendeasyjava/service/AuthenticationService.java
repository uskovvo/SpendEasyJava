package com.app.spendeasyjava.service;

import ch.qos.logback.core.spi.ErrorCodes;
import ch.qos.logback.core.status.ErrorStatus;
import com.app.spendeasyjava.config.JwtService;
import com.app.spendeasyjava.domain.enums.Role;
import com.app.spendeasyjava.domain.requests.AuthenticationRequest;
import com.app.spendeasyjava.domain.responses.AuthenticationResponse;
import com.app.spendeasyjava.domain.requests.RegisterRequest;
import com.app.spendeasyjava.domain.entities.Token;
import com.app.spendeasyjava.domain.entities.User;
import com.app.spendeasyjava.domain.enums.TokenType;
import com.app.spendeasyjava.domain.repositories.TokenRepository;
import com.app.spendeasyjava.domain.repositories.UserRepository;
import com.app.spendeasyjava.domain.responses.Response;
import com.app.spendeasyjava.exceptions.PasswordValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.app.spendeasyjava.domain.enums.Role.USER;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CategoriesServiceImpl categoriesService;

    public Response register(RegisterRequest request) throws MethodArgumentNotValidException {
        PasswordValidator.validatePassword(request.getPassword(), request.getConfirmPassword());
//        if (request.getPassword().length() < 6) {
//            BindingResult bindingResult = new BeanPropertyBindingResult(request, "registerRequest");
//            bindingResult.rejectValue("password", "password.length", "Password should be longer than 6 characters");
//            throw new MethodArgumentNotValidException((MethodParameter) null, bindingResult);
//        }
//        if (!isRequestHasSpecialSymbols(request.getPassword())) {
//            BindingResult bindingResult = new BeanPropertyBindingResult(request, "registerRequest");
//            bindingResult.rejectValue("password", "password.hasDigitAndSpecialSymbols", "Password should have special symbols");
//            throw new MethodArgumentNotValidException((MethodParameter) null, bindingResult);
//        }
//        if (!request.getPassword().equals(request.getConfirmPassword())) {
//            BindingResult bindingResult = new BeanPropertyBindingResult(request, "registerRequest");
//            bindingResult.rejectValue("confirmPassword", "password.mismatch", "Passwords do not match");
//            throw new MethodArgumentNotValidException((MethodParameter) null, bindingResult);
//        }
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(USER)
                .build();
        var savedUser = userRepository.save(user);
        categoriesService.createDefaultCategories(savedUser);
        userRepository.save(savedUser);

        return new Response("Success", user.getId());
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail).orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
