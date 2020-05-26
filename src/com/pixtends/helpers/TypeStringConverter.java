package com.pixtends.helpers;

import com.pixtends.models.CardType;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

public class TypeStringConverter extends StringConverter<CardType> {
    private ComboBox<CardType> comboBox;

    public TypeStringConverter(ComboBox<CardType> comboBox) {
        this.comboBox = comboBox;
    }

    @Override
    public String toString(CardType cardType) {
        return cardType == null ? null : cardType.getName();
    }


    @Override
    public CardType fromString(String s) {
        return comboBox.getItems().stream().filter(ct ->
                ct.getName().equals(s)).findFirst().orElse(null);
    }
}
