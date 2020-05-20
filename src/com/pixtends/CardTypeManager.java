package com.pixtends;

import com.pixtends.models.CardType;

import java.util.ArrayList;

public class CardTypeManager {
    public static ArrayList<CardType> getAll() {
        ArrayList<CardType> list = new ArrayList<>();
        list.add(new CardType("Geography", "blue"));
        list.add(new CardType("Entertainment", "pink"));
        list.add(new CardType("History", "yellow"));
        list.add(new CardType("Arts & Literature", "maroon"));
        list.add(new CardType("Sciences & Nature", "green"));
        list.add(new CardType("Sports & Hobbies", "orange"));
        return list;
    }
}
