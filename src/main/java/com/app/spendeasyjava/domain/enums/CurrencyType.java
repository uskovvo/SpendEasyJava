package com.app.spendeasyjava.domain.enums;

public enum CurrencyType {
    RUB ("RUB"),
    EUR ("EUR"),
    HUF ("HUF"),
    DOL ("USD");
    private final String currency;

    CurrencyType (String currency){
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }
}
