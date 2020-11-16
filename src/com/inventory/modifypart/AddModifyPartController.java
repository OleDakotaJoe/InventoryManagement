package com.inventory.modifypart;

import com.inventory.Controller;
import com.inventory.controls.TextFieldLimited;
import com.inventory.data.datamodel.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

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
     changeVarLabel();
     addModLabel.setText(PassableData.getTitle());
     if(PassableData.getIsModify() && PassableData.getPartData()!= null){
         populateForm();
     } else {
         partId.setText(Controller.createId());
     }
     partName.setMaxLength(26);
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

    // TODO: 11/14/2020 Implement functionality to add variableTextField property
    public void populateForm() {
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

    public boolean partIsValid(){
        boolean isComplete =  !partPrice.getText().equals("")
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
        if (isComplete) isMinLessThanMax = Integer.parseInt(maximumInventory.getText()) > Integer.parseInt(minimumInventory.getText());

        if (!isComplete) {
            Alert errorMessage = new Alert(Alert.AlertType.INFORMATION);
            errorMessage.setTitle("Missing Information");
            errorMessage.setHeaderText("You didn't enter required information!");
            errorMessage.setContentText("All fields are required! Your part has not been saved. Press \"OK\" and try again.");
            errorMessage.show();
            return false;
        }
        if (!isMinLessThanMax) {
            Alert errorMessage = new Alert(Alert.AlertType.INFORMATION);
            errorMessage.setTitle("Invalid Entry");
            errorMessage.setHeaderText("Min must be less than Max!");
            errorMessage.setContentText("Re-enter your data! Your part has not been saved.Press \"OK\" and try again.");
            errorMessage.show();
            return  false;
        }

        if(!isValidPrice) {
            Alert errorMessage = new Alert(Alert.AlertType.INFORMATION);
            errorMessage.setTitle("Price is not valid");
            errorMessage.setHeaderText("The value you entered for price is not valid");
            errorMessage.setContentText("Please ensure that the value you have entered does" +
                    " not include more than one decimal point (.), does not contain any letters,  and does not " +
                    "have more than two digits after the decimal.  ");
            errorMessage.show();
            return false;
        }

        if (!isValidName) {
            Alert errorMessage = new Alert(Alert.AlertType.INFORMATION);
            errorMessage.setTitle("Invalid Name");
            errorMessage.setHeaderText("The value you entered for name is not valid.");
            errorMessage.setContentText("Please ensure that the text you enter does not" +
                    " include any quotation marks," +
                    "(\"), or commas (,).");
            errorMessage.show();
            return false;
        }
        if (!isValidId) {
            Alert errorMessage = new Alert(Alert.AlertType.INFORMATION);
            errorMessage.setTitle("Incorrect ID");
            errorMessage.setHeaderText("The value you entered for ID is not valid");
            errorMessage.setContentText("Please ensure that the value you have entered" +
                    " is a whole number, with no letters, symbols or decimal points.  ");
            errorMessage.show();
            return false;
        }

        if (!isValidMax) {
            Alert errorMessage = new Alert(Alert.AlertType.INFORMATION);
            errorMessage.setTitle("Incorrect Max Inventory");
            errorMessage.setHeaderText("The value you entered for Max is not valid");
            errorMessage.setContentText("Please ensure that the value you have entered" +
                    " is a whole number, with no letters, symbols or decimal points.  ");
            errorMessage.show();
            return false;
        }

        if(!isValidMin) {
            Alert errorMessage = new Alert(Alert.AlertType.INFORMATION);
            errorMessage.setTitle("Incorrect Min Inventory");
            errorMessage.setHeaderText("The value you entered for Min is not valid");
            errorMessage.setContentText("Please ensure that the value you have entered" +
                    " is a whole number, with no letters, symbols or decimal points.  ");
            errorMessage.show();
            return false;
        }
        return true;

    }

    public void addPart() {

        if(partIsValid()) {
            double price = Double.parseDouble(partPrice.getText());
            String name = partName.getText();
            int stock = Integer.parseInt(inventoryCount.getText());
            int id = Integer.parseInt(partId.getText());
            int minimum = Integer.parseInt(minimumInventory.getText());
            int maximum = Integer.parseInt(maximumInventory.getText());
            String variableText = variableTextField.getText();

                if (inHouse.isSelected()) {
                    InHouse newPart = new InHouse(id,name,price,stock,minimum,maximum,Integer.parseInt(variableText));
                    if (PassableData.getIsModify()) {
                        Controller.getInventory().updatePart(PassableData.getPartIndex(), newPart);
                    } else {
                        Controller.getInventory().addPart(newPart);
                    }

                } else if (outsourced.isSelected()) {
                    Outsourced newPart = new Outsourced(id,name,price,stock,minimum,maximum,variableText);
                    if (PassableData.getIsModify()) {
                        Controller.getInventory().updatePart(PassableData.getPartIndex(), newPart);
                    } else {
                        Controller.getInventory().addPart(newPart);
                    }
                }
                try {
                    InventoryData.getInstance().storePartInventory();
                    InventoryData.getInstance().storeIdIndex();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                exit();

            }
    }

    public void validator(KeyEvent event) {
        TextFieldLimited source =(TextFieldLimited) event.getSource();
        if (source.equals(partId)) {
           isIntegerValid(source,event);
        } else if (source.equals(maximumInventory)) {
            isIntegerValid(source,event);
        } else if (source.equals(partName)) {
            isCSVTextValid(source,event);
        } else if (source.equals(inventoryCount)) {
            isIntegerValid(source,event);
        } else if (source.equals(minimumInventory)) {
            isIntegerValid(source,event);
        } else if (source.equals(variableTextField)) {
            if (inHouse.isSelected()) {
                isIntegerValid(source,event);;
            } else {
                isCSVTextValid(source,event);
            }
        } else if (source.equals(partPrice)) {
            isDoubleValid(source, event);
        } else return;
    }



    @FXML
    public void exit(){
        try {
            InventoryData.getInstance().storeIdIndex();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        PassableData.setIsModify(false);
    }
}
