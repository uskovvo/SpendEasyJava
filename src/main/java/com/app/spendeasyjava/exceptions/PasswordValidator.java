package com.app.spendeasyjava.exceptions;

import com.app.spendeasyjava.domain.requests.RegisterRequest;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {

    public static void validatePassword(String password, String confirmPassword) throws MethodArgumentNotValidException {
        validatePasswordLength(password);
        validatePasswordSpecialSymbols(password);
        validatePasswordMatch(password, confirmPassword);
    }

    private static void validatePasswordLength(String password) throws MethodArgumentNotValidException {
        if (password.length() < 6) {
            throwValidationException("password", "password.length", "Password should be longer than 6 characters");
        }
    }

    private static void validatePasswordSpecialSymbols(String password) throws MethodArgumentNotValidException {
        if (!isPasswordHasSpecialSymbols(password)) {
            throwValidationException("password", "password.hasDigitAndSpecialSymbols", "Password should have special symbols");
        }
    }

    private static void validatePasswordMatch(String password, String confirmPassword) throws MethodArgumentNotValidException {
        if (!password.equals(confirmPassword)) {
            throwValidationException("confirmPassword", "password.mismatch", "Passwords do not match");
        }
    }

    private static void throwValidationException(String field, String errorCode, String errorMessage) throws MethodArgumentNotValidException {
        RegisterRequest dummyRequest = new RegisterRequest();
        BindingResult bindingResult = new BeanPropertyBindingResult(dummyRequest, "registerRequest");
        bindingResult.rejectValue(field, errorCode, errorMessage);
        throw new MethodArgumentNotValidException((MethodParameter) null, bindingResult);
    }

    private static boolean isPasswordHasSpecialSymbols(String password) {
        String regex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }
}
