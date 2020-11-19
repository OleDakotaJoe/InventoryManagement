package com.inventory.modifypart;

import com.inventory.Controller;
import com.inventory.controls.TextFieldLimited;
import com.inventory.data.datamodel.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


/**
 * Controller for FXML file addmodifypart.fxml
 *
 * Contains all event handling for Add/Modify Part form.
 *
 * Extends the Validator class, to gain its functionality, to aid in textField Validation.
 */
public class AddModifyPartController extends Validator {

    @FXML public TextFieldLimited partId;

    @FXML public TextFieldLimited partName;

    @FXML public TextFieldLimited inventoryCount;

    @FXML public TextFieldLimited partPrice;

    @FXML public TextFieldLimited maximumInventory;

    @FXML public TextFieldLimited variableTextField;

    @FXML public Label varLabel;

    @FXML public Label addModLabel;

    @FXML public TextFieldLimited minimumInventory;

    @FXML public Button saveButton;

    @FXML public Button cancelButton;

    @FXML public RadioButton inHouse;

    @FXML public RadioButton outsourced;

    @FXML public GridPane main;


    @FXML
    public void initialize() {
     addModLabel.setText(PassableData.getPartTitle());
        if(PassableData.isModifyPart() && PassableData.getPartData() != null){
            populateForm();
        } else {
            partId.setText(Controller.createPartId());
        }
        changeVarLabel();
    }

    public void changeVarLabel() {
        if (inHouse.isSelected()) {
            varLabel.setText("Machine ID");
            variableTextField.setPromptText("Machine ID...");
            variableTextField.setMaxLength(10);
        } else if (outsourced.isSelected()) {
            varLabel.setText("Company Name");
            variableTextField.setPromptText("Company Name...");
            variableTextField.setMaxLength(26);
        }
        int textLength = partId.getText().length();
        variableTextField.setText(variableTextField.getText().replaceAll("[^\\d.]", ""));
        variableTextField.positionCaret(textLength);

    }

    private void populateForm() {
        Part selectedPart = (Part) PassableData.getPartData();
        partPrice.setText(String.valueOf(selectedPart.getPrice()));
        partName.setText(selectedPart.getName());
        inventoryCount.setText(String.valueOf(selectedPart.getStock()));
        partId.setText(String.valueOf(selectedPart.getId()));
        maximumInventory.setText(String.valueOf(selectedPart.getMax()));
        minimumInventory.setText(String.valueOf(selectedPart.getMin()));

        if (PassableData.isOutsourced()) {
            Outsourced part = (Outsourced) selectedPart;
            variableTextField.setText(part.getCompanyName());
            outsourced.setSelected(true);

        } else if (!PassableData.isOutsourced()) {
            InHouse part = (InHouse) selectedPart;
            variableTextField.setText(String.valueOf(part.getMachineId()));
            inHouse.setSelected(true);
        }


    }

    /**
     * <h1>This method tests whether or not the entries into the Add/Modify part form's fields are valid complete and valid input. </h1>
     * <p> The test checks first that there is valid text entries into each of the text fields
     *      * located on the add/modify part form.</p>
     * <p>The isDoubleValid(), isCSVTextValid(), and isIntegerValid() methods are members of the Validator class
     *      * which the Controller class extends. Each of these methods currently take a parameter which is required
     *      * to designate which field the method is validating. These overloaded methods only check for validity, but also
     *      * have an overloaded method which will correct the errors as well. this is used in textFieldValidator An alert message for each item that may be invalid is provided, even though they
     *      * currently are not all able to be used. This way if a change is made to the Validator class, which no longer
     *      * corrects the invalid text, or if a bug is introduced, the user will receive a Alert that informs them of their
     *      * errors and directions on how to provide valid input.</p>
     * @return The method returns true if all text is complete and valid according to data type, and false otherwise.
     */
    public boolean isValidPart() {
        boolean result = false;
        boolean isComplete = !partPrice.getText().equals("")
                && !partName.getText().equals("")
                && !inventoryCount.getText().equals("")
                && !partId.getText().equals("")
                && !maximumInventory.getText().equals("")
                && !minimumInventory.getText().equals("")
                && !variableTextField.getText().equals("");
        boolean isValidPrice = isDoubleValid(partPrice);
        boolean isValidName = isCSVTextValid(partName);
        boolean isValidId = isIntegerValid(partId);
        boolean isValidMax = isIntegerValid(maximumInventory);
        boolean isValidMin = isIntegerValid(minimumInventory);
        boolean isMinLessThanMax = false;
        if (isComplete)
            isMinLessThanMax = Integer.parseInt(maximumInventory.getText()) > Integer.parseInt(minimumInventory.getText());

        if (!isComplete) {
            Alert errorMessage = new Alert(Alert.AlertType.INFORMATION);
            errorMessage.setTitle("Missing Information");
            errorMessage.setHeaderText("You didn't enter required information!");
            errorMessage.setContentText("All fields are required! Your part has not been saved. Press \"OK\" and try again.");
            errorMessage.show();
        } else if (!isMinLessThanMax) {
            Alert errorMessage = new Alert(Alert.AlertType.INFORMATION);
            errorMessage.setTitle("Invalid Entry");
            errorMessage.setHeaderText("Min must be less than Max!");
            errorMessage.setContentText("Re-enter your data! Your part has not been saved.Press \"OK\" and try again.");
            errorMessage.show();
        } else if (!isValidPrice) {
            Alert errorMessage = new Alert(Alert.AlertType.INFORMATION);
            errorMessage.setTitle("Price is not valid");
            errorMessage.setHeaderText("The value you entered for price is not valid");
            errorMessage.setContentText("Please ensure that the value you have entered does" +
                    " not include more than one decimal point (.), does not contain any letters,  and does not " +
                    "have more than two digits after the decimal.  ");
            errorMessage.show();
        } else if (!isValidName) {
            Alert errorMessage = new Alert(Alert.AlertType.INFORMATION);
            errorMessage.setTitle("Invalid Name");
            errorMessage.setHeaderText("The value you entered for name is not valid.");
            errorMessage.setContentText("Please ensure that the text you enter does not" +
                    " include any quotation marks," +
                    "(\"), or commas (,).");
            errorMessage.show();
        } else if (!isValidId) {
            Alert errorMessage = new Alert(Alert.AlertType.INFORMATION);
            errorMessage.setTitle("Incorrect ID");
            errorMessage.setHeaderText("The value you entered for ID is not valid");
            errorMessage.setContentText("Please ensure that the value you have entered" +
                    " is a whole number, with no letters, symbols or decimal points.  ");
            errorMessage.show();
        } else if (!isValidMax) {
            Alert errorMessage = new Alert(Alert.AlertType.INFORMATION);
            errorMessage.setTitle("Incorrect Max Inventory");
            errorMessage.setHeaderText("The value you entered for Max is not valid");
            errorMessage.setContentText("Please ensure that the value you have entered" +
                    " is a whole number, with no letters, symbols or decimal points.  ");
            errorMessage.show();
        } else if (!isValidMin) {
            Alert errorMessage = new Alert(Alert.AlertType.INFORMATION);
            errorMessage.setTitle("Incorrect Min Inventory");
            errorMessage.setHeaderText("The value you entered for Min is not valid");
            errorMessage.setContentText("Please ensure that the value you have entered" +
                    " is a whole number, with no letters, symbols or decimal points.  ");
            errorMessage.show();
        } else {
            result = true;
        }

        return result;
    }

    public void addPart() {

        if(isValidPart()) {
            double price = Double.parseDouble(partPrice.getText());
            String name = partName.getText();
            int stock = Integer.parseInt(inventoryCount.getText());
            int id = Integer.parseInt(partId.getText());
            int minimum = Integer.parseInt(minimumInventory.getText());
            int maximum = Integer.parseInt(maximumInventory.getText());
            String variableText = variableTextField.getText();

                if (inHouse.isSelected()) {
                    InHouse newPart = new InHouse(id,name,price,stock,minimum,maximum,Integer.parseInt(variableText));
                    if (PassableData.isModifyPart()) {
                        Controller.getInventory().updatePart(PassableData.getPartIndex(), newPart);
                    } else {
                        Controller.getInventory().addPart(newPart);
                    }

                } else if (outsourced.isSelected()) {
                    Outsourced newPart = new Outsourced(id,name,price,stock,minimum,maximum,variableText);
                    if (PassableData.isModifyPart()) {
                        Controller.getInventory().updatePart(PassableData.getPartIndex(), newPart);
                    } else {
                        Controller.getInventory().addPart(newPart);
                    }
                }
/*                try {
                    InventoryData.getInstance().storePartInventory();
                    InventoryData.getInstance().storePartIdIndex();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                exit();

            }
    }

    /**<h1>Validates and checks for proper format of Data types</h1>
     *  <p>This method is used for validation of text fields. It checks for event type, then validates
     *      * based on the source based on what data should be in the field.</p>
     *  <p>The variable "source" </p>
     * @param event is the must be called by an event listener that passes a KeyEvent, and the source must be a text field.
     */
    public void textFieldValidator(KeyEvent event) {
        TextFieldLimited source =(TextFieldLimited) event.getSource();
        if (source.equals(partId)) {
           isIntegerValid(event);
        } else if (source.equals(maximumInventory)) {
            isIntegerValid(event);
        } else if (source.equals(partName)) {
            isCSVTextValid(event);
        } else if (source.equals(inventoryCount)) {
            isIntegerValid(event);
        } else if (source.equals(minimumInventory)) {
            isIntegerValid(event);
        } else if (source.equals(variableTextField)) {
            if (inHouse.isSelected()) {
                isIntegerValid(event);;
            } else {
                isCSVTextValid(event);
            }
        } else if (source.equals(partPrice)) {
            isDoubleValid(event);
        } else return;
    }



    @FXML
    public void exit(){
/*        try {
            InventoryData.getInstance().storePartIdIndex();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        PassableData.setIsModifyPart(false);
    }
}
