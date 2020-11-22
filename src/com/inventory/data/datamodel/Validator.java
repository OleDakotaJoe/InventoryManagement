package com.inventory.data.datamodel;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * Class which extends the functionality of other classes to include text validation for TextFields.
 */
public class Validator {


    /**
     * Validates text fields in javaFx
     * This method is the primary method of the Validator class. It can be used standalone or as a part of the other methods in the class.
     * It finds incorrect data based ont the <code>regex</code> parameter and replaces it with an empty string.
     * This method should be called on a <code>KeyEvent</code>.
     * The method also ignores non-text input, such as arrowKeys, functionKeys, Modifier Keys, NavigationKeys, as well
     * as ignoring when shift or control is down, to prevent buggy behavior)
     * <p>In future releases of this product need to work to ensure that the price field includes only 2 significant digits.</p>
     * <code>textFieldValidator(event,"[^\\d.]");// for integer</code>
     * <code>textFieldValidator(event,"[^\\p{Digit}.]");// for double</code>
     * <code>textFieldValidator(event, "[\",]"); //for text (to not interfere with comma-delimited format)</code>
     * @param event
     * The <code>KeyEvent</code> which is calling the function.
     * @param regex
     * The regular expression to be removed from the expression.
     * @return
     * returns the <code>TextField</code> element which was being validated, for further validation.
     */
    private TextField textFieldValidator(KeyEvent event, String regex) {
        TextField element = (TextField) event.getSource();
        int caretPosition = element.getCaretPosition();
        int textLength = element.getText().length();
        if ( !event.getCode().isArrowKey()
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

    /**
     * Checks for  validity of an element expected to contain a double.
     * Corrects any input that includes a non-number or Decimal "."
     * then runs the overloaded <code>isDouble</code> which attempts to parse a double, and if it cannot parsed, throws an error.
     * @param event
     * The <code>KeyEvent</code> which calls the method.
     * @return
     * Returns the result of the Overloaded method <code>isDoubleValid()</code>, which will be true only if input is a valid <code>double</code>.
     */
    protected boolean isDoubleValid(KeyEvent event) {
       TextField element = (TextField) textFieldValidator(event,"[^\\p{Digit}.]");
        return isDoubleValid(element);
    }

    /**
     * An overloaded method which can be called as a standalone, when input correcting is not necessary, and
     * the goal is only to check if input is valid or invalid.
     * NOTE: this method checks if input is valid according to type, not format.
     * @param element
     * The <code>TextField</code> element containing the input to be checked
     * @return
     * Returns true if input is valid, and false if input is valid.
     */
    protected boolean isDoubleValid(TextField element) {
        try {
            Double.parseDouble(element.getText());
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Checks for  validity of an element expected to contain a integer.
     * Corrects any input that includes a non-number.
     * then runs the overloaded <code>isDouble</code> which attempts to parse a int, and if it cannot parsed, throws an error.
     * @param event
     * The <code>KeyEvent</code> which calls the method.
     * @return
     * Returns the result of the Overloaded method <code>isDoubleValid()</code>, which will be true only if input is a valid <code>int</code>.
     */
    protected boolean isIntegerValid(KeyEvent event) {
        TextField element = (TextField) textFieldValidator(event,"[^\\d]");
        return isIntegerValid(element);
    }
    /**
     * An overloaded method which can be called as a standalone, when input correcting is not necessary, and
     * the goal is only to check if input is valid or invalid.
     * NOTE: this method checks if input is valid according to type, not format.
     * @param element
     * The <code>TextField</code> element containing the input to be checked
     * @return
     * Returns true if input is valid, and false if input is valid.
     */
    protected boolean isIntegerValid(TextField element) {
        try {
            Integer.parseInt(element.getText());
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    protected boolean isCSVTextValid(KeyEvent event) {
        TextField element = (TextField) event.getSource();
        if (element.getText().contains(",") || element.getText().contains("\"")) {
            textFieldValidator(event, "[\",]");
            return false;
        }
        return true;
    }
    /**
     * An overloaded method which can be called as a standalone, when input correcting is not necessary, and
     * the goal is only to check if input is valid or invalid.
     * Only checking whether or not an element is containing a comma (,) or a quotation mark (").
     * @param element
     * The <code>TextField</code> element containing the input to be checked
     * @return
     * Returns true if input is valid, and false if input is valid.
     */
    protected boolean isCSVTextValid(TextField element) {
        if(element.getText().contains(",") || element.getText().contains("\"")) {
            return false;
        }
        return true;
    }
}


