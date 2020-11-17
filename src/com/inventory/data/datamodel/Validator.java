package com.inventory.data.datamodel;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class Validator {


    /**
     * <h1>Validates TextFields in javaFX.</h1>
     * This method is the primary method of the Validator class. It can be used standalone or as a part of the other methods in the class.
     * 
     * @param event
     * @param regex
     * @return
     */
    private TextField textFieldValidator(KeyEvent event, String regex) {
        /** THIS METHOD PREVENTS THE USER FROM ENTERING DATA OF THE WRONG DATA TYPE
         *      THE METHOD FINDS INCORRECT DATA BASED ON THE regex PARAMETER AND
         *      REPLACES IT WITH AN EMPTY STRING.
         *      --THE METHOD IS EVENT DRIVEN BASED ON A KeyEvent
         *      THE CURRENT KeyEvent IS onKeyReleased.
         *
         *      THE FOLLOWING METHOD CALLS CAN BE USED TO VALIDATE THEIR RESPECTIVE TYPES;
         * textFieldValidator(element, event,"[^\\d.]"); for integer
         * textFieldValidator(element, event,"[^\\p{Digit}.]"); for double
         */
        TextField element = (TextField) event.getSource();
        int caretPosition = element.getCaretPosition();
        int textLength = element.getText().length();
        if (!event.getCode().isWhitespaceKey()
                && !event.getCode().isArrowKey()
                && !event.getCode().isFunctionKey()
                && !event.getCode().isModifierKey()
                && !event.getCode().isNavigationKey()
                && !event.isControlDown()
                && !event.isShiftDown()
            ) {
            element.setText(element.getText().replaceAll(regex, ""));
            if(caretPosition < textLength) {
                if(element.getText().matches(regex)) {
                    element.positionCaret(caretPosition - 1);
                } else {
                    element.positionCaret(caretPosition);
                }
            } else {
                element.positionCaret(textLength);
            }

        }
        return element;
    }

    protected boolean isDoubleValid(KeyEvent event) {
       TextField element = (TextField) textFieldValidator(event,"[^\\p{Digit}.]");
        return isDoubleValid(element);
    }

    protected boolean isDoubleValid(TextField element) {
        try {
            Double.parseDouble(element.getText());
        }
        catch (Exception e) {
            System.out.println("Not Valid");
            return false;
        }
        return true;
    }

    protected boolean isIntegerValid(KeyEvent event) {
        TextField element = (TextField) textFieldValidator(event,"[^\\d]");
        return isIntegerValid(element);
    }

    protected boolean isIntegerValid(TextField element) {
        try {
            Integer.parseInt(element.getText());
        }
        catch (Exception e) {
            System.out.println("Not Valid");
            return false;
        }
        return true;
    }

    protected boolean isCSVTextValid(KeyEvent event) {
        TextField element = (TextField) event.getSource();
        if (element.getText().contains(",") || element.getText().contains("\"")) {
            textFieldValidator(event, "[\",]");
            System.out.println("Not Valid");
            return false;
        }
        return true;
    }

    protected boolean isCSVTextValid(TextField element) {
        if(element.getText().contains(",") || element.getText().contains("\"")) {
            System.out.println("Not Valid");
            return false;
        }
        return true;
    }




}


