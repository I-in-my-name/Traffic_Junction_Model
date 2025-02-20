package com.trafficjunction.UI_Utilities;

import java.util.function.UnaryOperator;

import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class DataSanitisation {
    public static void applyNumericRestriction(TextField textField) {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            return newText.matches("\\d{0,6}") ? change : null;
        };
        textField.setTextFormatter(new TextFormatter<>(filter));
    }
}
