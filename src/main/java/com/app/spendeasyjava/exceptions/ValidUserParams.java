package com.app.spendeasyjava.exceptions;

import com.app.spendeasyjava.domain.repositories.UserRepository;
import com.app.spendeasyjava.domain.requests.RegisterRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import lombok.RequiredArgsConstructor;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.annotation.ElementType.*;

@Documented
@Constraint(validatedBy = UserParamsValidator.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUserParams {
    String message() default "Invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

@RequiredArgsConstructor
class UserParamsValidator implements ConstraintValidator<ValidUserParams, RegisterRequest> {
    private final UserRepository userRepository;
    private final String PASSWORD = "password";
    private final String CONFIRM_PASSWORD = "confirmPassword";
    private final String REGEX = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).*$";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    @Override
    public void initialize(ValidUserParams constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(RegisterRequest value, ConstraintValidatorContext context) {
        if (!pattern.matcher(value.getEmail()).matches()) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("{valid.username.not_email}")
                    .addPropertyNode("email")
                    .addConstraintViolation();
            return false;
        }
        if (userRepository.findByEmail(value.getEmail()).isPresent()) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("{valid.username.already_exists}")
                    .addPropertyNode("email")
                    .addConstraintViolation();
            return false;
        }

        if (value.getPassword().length() < 6) {
            addConstraintViolation(context, PASSWORD, "{valid.password.length.message}");
            return false;
        }
        if (!isPasswordHasSpecialSymbols(value.getPassword())) {
            addConstraintViolation(context, PASSWORD, "{valid.password.symbols.message}");
            return false;
        }
        if (!value.getPassword().equals(value.getConfirmPassword())) {
            addConstraintViolation(context, CONFIRM_PASSWORD, "{valid.password.not_match.message}");
            return false;
        }

        return true;
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String property, String message) {
        context.disableDefaultConstraintViolation();
        context
                .buildConstraintViolationWithTemplate(message)
                .addPropertyNode(property)
                .addConstraintViolation();
    }

    private boolean isPasswordHasSpecialSymbols(String password) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }
}