package com.app.spendeasyjava.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Messages {

    ENTITY_NOT_FOUND("entity.not.found.exception"),
    DELETED("entity.deleted"),
    NOT_EMAIL_FORMAT("valid.username.not_email"),
    USER_ALREADY_EXISTS("valid.username.already_exists"),
    SHORT_PASS("valid.password.length.message"),
    PASS_WITHOUT_SYMB("valid.password.symbols.message"),
    NOT_MATCHES_PASS("valid.password.not_match.message"),
    WRONG_AUTH("valid.authorization.wrong"),
    INVALID_EMAIL("valid.username.invalid.characters.email");

    private final String message;
}
