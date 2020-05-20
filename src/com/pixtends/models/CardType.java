package com.pixtends.models;

public class CardType {
    private String domain;
    private String color;

    enum Type {
        GEOGRAPHY,
        ENTERTAINMENT,
        HISTORY,
        ARTS_LITERATURE,
        SCIENCES_NATURE,
        SPORTS_HOBBIES
    }

    public CardType(String domain, String color) {
        this.domain = domain;
        this.color = color;
    }

    public String getDomain() {
        return domain;
    }
}
