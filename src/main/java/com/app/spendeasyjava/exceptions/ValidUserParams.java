package com.app.spendeasyjava.exceptions;

import com.app.spendeasyjava.domain.enums.Messages;
import com.app.spendeasyjava.domain.repositories.UserRepository;
import com.app.spendeasyjava.domain.requests.RegisterRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Locale;
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
    private final MessageSource messageSource;
    private final String PASSWORD = "password";
    private final String CONFIRM_PASSWORD = "confirmPassword";
    private final String REGEX = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).*$";
    private final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private final String ALLOWED_EMAIL_REGEX = "^[a-zA-Z0-9._%+-@]+$";
    private final Pattern validEmailPattern = Pattern.compile(EMAIL_REGEX);
    private final Pattern allowedEmailPattern = Pattern.compile(ALLOWED_EMAIL_REGEX);

    @Override
    public void initialize(ValidUserParams constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(RegisterRequest value, ConstraintValidatorContext context) {
        if (!allowedEmailPattern.matcher(value.getEmail()).matches()) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(messageSource.getMessage(Messages.INVALID_EMAIL.getMessage(), null, Locale.getDefault()))
                    .addPropertyNode("email")
                    .addConstraintViolation();
            return false;
        }
        if (!validEmailPattern.matcher(value.getEmail()).matches()) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(messageSource.getMessage(Messages.NOT_EMAIL_FORMAT.getMessage(), null, Locale.getDefault()))
                    .addPropertyNode("email")
                    .addConstraintViolation();
            return false;
        }
        if (userRepository.findByEmail(value.getEmail()).isPresent()) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(messageSource.getMessage(Messages.USER_ALREADY_EXISTS.getMessage(), null, Locale.getDefault()))
                    .addPropertyNode("email")
                    .addConstraintViolation();
            return false;
        }

        if (value.getPassword().length() < 6) {
            addConstraintViolation(context, PASSWORD, messageSource.getMessage(Messages.SHORT_PASS.getMessage(), null, Locale.getDefault()));
            return false;
        }
        if (!isPasswordHasSpecialSymbols(value.getPassword())) {
            addConstraintViolation(context, PASSWORD, messageSource.getMessage(Messages.PASS_WITHOUT_SYMB.getMessage(), null, Locale.getDefault()));
            return false;
        }
        if (!value.getPassword().equals(value.getConfirmPassword())) {
            addConstraintViolation(context, CONFIRM_PASSWORD, messageSource.getMessage(Messages.NOT_MATCHES_PASS.getMessage(), null, Locale.getDefault()));
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