package com.pixtends.models;

public class CardType {
    private int id;
    private String name;
    private String color;

    public CardType(int id, String domain, String color) {
        this.id = id;
        this.name = domain;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getColor() {
        return color;
    }
}
