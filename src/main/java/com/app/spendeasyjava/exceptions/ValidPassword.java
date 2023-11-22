package com.app.spendeasyjava.exceptions;

import com.app.spendeasyjava.domain.requests.RegisterRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.annotation.ElementType.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
    String message() default "Invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

class PasswordValidator implements ConstraintValidator<ValidPassword, RegisterRequest> {
    private final String PASSWORD = "password";
    private final String CONFIRM_PASSWORD = "confirmPassword";
    private final String REGEX = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).*$";

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(RegisterRequest value, ConstraintValidatorContext context) {

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