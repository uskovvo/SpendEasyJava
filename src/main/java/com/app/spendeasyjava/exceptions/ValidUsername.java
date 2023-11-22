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
import java.util.regex.Pattern;

import static java.lang.annotation.ElementType.*;

@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUsername {
    String message() default "{valid.username.already_exists}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

@RequiredArgsConstructor
class UsernameValidator implements ConstraintValidator<ValidUsername, RegisterRequest> {

    private final UserRepository userRepository;
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    @Override
    public void initialize(ValidUsername constraintAnnotation) {

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
        return true;
    }
}
