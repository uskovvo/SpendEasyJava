package com.app.spendeasyjava.domain.enums;

public enum DefaultCategories {
    PRODUCTS("Products"),
    TRANSPORT("Transport"),
    HOUSING("Housing"),
    HEALTH("Health"),
    ENTERTAINMENT("Entertainment"),
    EDUCATION("Education"),
    PERSONAL("Personal"),
    FINANCE("Finance"),
    OTHER("Other");

    private final String category;

    DefaultCategories (String category){
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
